import requests
from requests.exceptions import HTTPError, Timeout, RequestException
import json
import pandas as pd
import numpy as np
from dotenv import load_dotenv
import os
import mysql.connector
from sqlalchemy import create_engine
from data import TAXES_LIST, TICKER_LIST


# Getting envoironment variables and defining constants
load_dotenv('token.env')
BRAPI_TOKEN = os.getenv('BRAPI_TOKEN')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD= os.getenv('DB_PASSWORD')
DB_SERVER = os.getenv('DB_SERVER')
DB_PORT = os.getenv('DB_PORT')
DB_NAME = os.getenv('DB_NAME')
BRAPI_BASE_URL = 'https://brapi.dev/api'


def get_response_brapi(endpoint, params):
  
  try:
    response = requests.get(f'{BRAPI_BASE_URL}{endpoint}', params=params)
    return response.json()
  
  except HTTPError as http_err:
      print(f"Erro HTTP ocorreu: {http_err}")
  except Timeout as timeout_err:
      print(f"A requisição demorou muito tempo: {timeout_err}")
  except RequestException as req_err:
      print(f"Ocorreu um erro na requisição: {req_err}")
  except Exception as err:
      print(f"Ocorreu um erro inesperado: {err}")
      
  return

def get_quote_list_brapi() -> pd.DataFrame:

  endpoint = '/quote/list'
  params = {
      'token': BRAPI_TOKEN,
  }

  response = get_response_brapi(endpoint, params)
  data = response['stocks']

  df = pd.DataFrame(data)

  return df

def extract_company_data(ticker:str) -> dict:
  
  """
  get all data related to the company from brapi api
  
  Args:
    ticker(str): code of the finance asset
    
  Returns:
    response(dict): data returned from api in json format
  """  

  endpoint = f'/quote/{ticker}'
  params = {
      'token': BRAPI_TOKEN,
      'range': '1d',
      'interval': '1d',
      'fundamental': 'true',
      'modules': ['summaryProfile']
  }

  response = get_response_brapi(endpoint, params)

  return response

def extract_taxes_data(serie_code:str, start_date:str, end_date:str) -> dict:

  format = 'json'

  url = f'https://api.bcb.gov.br/dados/serie/bcdata.sgs.{serie_code}/dados?formato={format}&dataInicial={start_date}&dataFinal={end_date}'

  try:
    response = requests.get(url, timeout=10)
    response.raise_for_status()
    
    if not response.text.strip():
        print("Resposta vazia recebida")
        return {}
    
    data = response.json()
  except HTTPError as http_err:
      print(f"Erro HTTP ocorreu: {http_err}")
  except Timeout as timeout_err:
      print(f"Timeout Error: {timeout_err}")
  except RequestException as req_err:
      print(f"Request Error: {req_err}")
  except Exception as err:
      print(f"Unexpected Error: {err}")

  data = response.json()

  return data   

def transform_company_data(response:str) -> dict:

  data = response['results']
  
  for result in data:
    summary_profile = result.pop('summaryProfile', {})
    result.update(summary_profile)

    first_historical_record = result['historicalDataPrice'][0]
    result.update(first_historical_record)
    result.pop('historicalDataPrice', {})
    
    result.pop('validRanges', {})
    
    result.pop('validIntervals', {})
    
    result.pop('companyOfficers', {})

  return data

def transform_historical_prices(response:str) -> dict:
  data = response['results']
  
  for result in data:
    ticker = result["symbol"]
    historicalPrice = result["historicalDataPrice"]
    
    for i in historicalPrice:
      i["ticker"] = ticker

  return historicalPrice

def transform_taxes_data(data:dict, tax_name:str, start_date, end_date):
  
  df = pd.DataFrame(data)
  start_datetime = pd.to_datetime(start_date, dayfirst=True)
  end_datetime = pd.to_datetime(end_date, dayfirst=True)
  
  df.rename(columns={'valor':tax_name}, inplace=True)
  
  df['data'] = pd.to_datetime(df['data'], dayfirst=True)
  all_dates = pd.date_range(start=start_datetime, end=end_datetime)
  df = df.set_index('data').reindex(all_dates).ffill().reset_index()
  df = df.rename(columns={'index': 'data'})

  return df 

def load_data(df:pd.DataFrame, table:str, schema:str) -> None:

  connection = f'mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_SERVER}:{DB_PORT}/{DB_NAME}'
  engine = create_engine(connection)

  df.to_sql(table, con=engine, schema=schema, if_exists='replace', index=False)
  
  return
  
def get_companies(companies:dict) -> None:
  
  data = []
  table = 'empresas'
  schema = 'pi6'
  
  for ticker, company in companies.items():
    response = extract_company_data(ticker)
    transformed_data = transform_company_data(response)
    data.extend(transformed_data)
    
  df = pd.DataFrame(data)  
  load_data(df, table, schema)
    
  return

def get_historical_prices(companies:dict) -> None:
  
  data = []
  table = 'precos'
  schema = 'pi6'
  
  for ticker, company in companies.items():
    response = extract_company_data(ticker)
    transformed_data = transform_historical_prices(response)
    data.extend(transformed_data)
    
  df = pd.DataFrame(data)    
  load_data(df, table, schema)
    
  return

def get_taxes_data(taxes:dict) -> None:
  
  table = 'taxas'
  schema = 'pi6'
  
  start_date = '01/01/2023'
  end_date = '31/12/2023'
  
  df = pd.DataFrame(columns=['data'])
  
  for tax_name, serie_code in taxes.items():
    data = extract_taxes_data(serie_code, start_date, end_date)
    df1 = transform_taxes_data(data, tax_name, start_date, end_date)
    df = pd.merge(df, df1, on='data', how='outer')
    df = df.sort_values(by='data').reset_index(drop=True)
    
  load_data(df, table, schema)
  
  return
  
def main():
  get_companies(TICKER_LIST)
  get_historical_prices(TICKER_LIST)
  get_taxes_data(TAXES_LIST)


if __name__ == "__main__":
    main()