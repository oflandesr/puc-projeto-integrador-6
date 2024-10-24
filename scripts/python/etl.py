import requests
from requests.exceptions import HTTPError, Timeout, RequestException
import json
import pandas as pd
import numpy as np
from dotenv import load_dotenv
import os
import mysql.connector
from sqlalchemy import create_engine, Table, MetaData, text
from sqlalchemy.dialects.mysql import insert
from lists import INDEX_LIST, TICKER_LIST


# Getting envoironment variables and defining constants
current_dir = os.path.dirname(os.path.abspath(__file__))
env_path = os.path.join(current_dir, '..', 'keys.env')
load_dotenv(dotenv_path=env_path)

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

def extract_company_data(tickers: dict) -> pd.DataFrame:
    """
    Extrai os dados de todos os tickers fornecidos em um dicionário e retorna um DataFrame.
    O dicionário deve conter o ticker como chave e informações adicionais como valor (se necessário).
    """
    data = []
    params = {
        'token': BRAPI_TOKEN,
        'range': '1d',
        'interval': '1d',
        'fundamental': 'true',
        'modules': ['summaryProfile']
    }
      
    for ticker, _ in tickers.items(): 
      endpoint = f'/quote/{ticker}'
      response = get_response_brapi(endpoint, params=params)
      if response and 'results' in response:
        result = response['results'][0]
        summary_profile = result.pop('summaryProfile', {})
        result.update(summary_profile)
        data.append(result) 
    
    # Criar um DataFrame com todos os tickers
    df = pd.DataFrame(data)
    
    if df.empty:
        print("Erro ao extrair dados das empresas da API")
    else:
        print("Dados das empresas coletados com sucesso")
        
    
    return df

def extract_price_data(tickers: dict) -> pd.DataFrame:
    
    data = []
    params = {
        'token': BRAPI_TOKEN,
        'range': '3mo',
        'interval': '1d',
        'fundamental': 'true',
        'modules': ['summaryProfile']
    }
      
    for ticker, _ in tickers.items():  # Iterando sobre o dicionário de tickers
      endpoint = f'/quote/{ticker}'
      response = get_response_brapi(endpoint, params=params)
      
      if response and 'results' in response:
        result = response['results'][0]
        ticker = result["symbol"]
        historical_price_data = result.get("historicalDataPrice", [])

        for entry in historical_price_data:
            entry['symbol'] = ticker  # Adicionar o ticker a cada entrada
            data.append(entry)  # Adiciona cada histórico de preços na lista de dados
        
    df = pd.DataFrame(data)
    
    if df.empty:
        print("Erro ao extrair cotacao das acoes")
    else:
        print("Dados de cotacao das acoes coletados com sucesso")
    
    return df

def extract_indexes_data(serie_code:str, start_date:str, end_date:str) -> dict:
  
  data = {} # Criado para tratar erro de interpretacao do json

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

  return data   

def transform_company_data(df: pd.DataFrame) -> pd.DataFrame:
    """
    Transforma o DataFrame extraído para conter apenas as colunas relevantes de acordo com o formato do banco de dados.
    """
    column_mapping = {
        'symbol': 'TICKER',
        'currency': 'CURRENCY',
        'shortName': 'SHORT_NAME',
        'longName': 'LONG_NAME',
        'address2': 'ADDRESS_2',
        'city': 'CITY',
        'state': 'STATE',
        'zip': 'ZIP',
        'country': 'COUNTRY',
        'phone': 'PHONE',
        'website': 'WEBSITE',
        'logo_url': 'LOGO_URL',
        'industry': 'INDUSTRY',
        'sector': 'SECTOR',
        'businessSummary': 'BUSINESS_SUMMARY',
        'fullTimeEmployees': 'NUMBER_OF_EMPLOYEES',
        'marketCap': 'MARKET_CAP',
        'twoHundredDayAverage': '200_DAY_AVERAGE',
        'twoHundredDayAverageChange': '200_DAY_AVERAGE_CHANGE',
        'twoHundredDayAverageChangePercent': '200_DAY_AVERAGE_CHANGE_PERCENT',
        'regularMarketChange': 'REGULAR_MARKET_CHANGE',
        'regularMarketChangePercent': 'REGULAR_MARKET_CHANGE_PERCENT',
        'regularMarketTime': 'REGULAR_MARKET_TIME',
        'regularMarketPrice': 'REGULAR_MARKET_PRICE',
        'regularMarketDayHigh': 'REGULAR_MARKET_DAY_HIGH',
        'regularMarketDayLow': 'REGULAR_MARKET_DAY_LOW',
        'regularMarketDayRange': 'REGULAR_MARKET_DAY_RANGE',
        'regularMarketVolume': 'REGULAR_MARKET_VOLUME',
        'regularMarketPreviousClose': 'REGULAR_MARKET_PREVIOUS_CLOSE',
        'regularMarketOpen': 'REGULAR_MARKET_OPEN',
        'averageDailyVolume3Month': 'AVERAGE_DAILY_VOLUME_3_MONTHS',
        'averageDailyVolume10Day': 'AVERAGE_DAILY_VOLUME_10_DAYS',
        'fiftyTwoWeekLowChange': '52_WEEKS_LOW_CHANGE',
        'fiftyTwoWeekHighChange': '52_WEEKS_HIGH_CHANGE',
        'fiftyTwoWeekHighChangePercent': '52_WEEKS_HIGH_CHANGE_PERCENT',
        'fiftyTwoWeekRange': '52_WEEKS_RANGE',
        'fiftyTwoWeekLow': '52_WEEKS_LOW',
        'fiftyTwoWeekHigh': '52_WEEKS_HIGH',
        'priceEarnings': 'PRICE_EARNINGS',
        'earningsPerShare': 'EARNINGS_PER_SHARE'
    }
    
    # Verificar quais colunas do mapeamento estão presentes no DataFrame
    existing_columns = [col for col in column_mapping.keys() if col in df.columns]
    
    # Renomear as colunas que estão presentes no DataFrame
    df = df.rename(columns={col: column_mapping[col] for col in existing_columns})
    
    # Selecionar apenas as colunas que foram renomeadas e estão presentes
    df = df[[column_mapping[col] for col in existing_columns]]
    
    return df

def transform_price_data(df: pd.DataFrame) -> pd.DataFrame:
    """
    Transforma o DataFrame extraído para conter apenas as colunas relevantes de acordo com o formato da tabela PRICE.
    Adiciona uma chave primária concatenando TIMESTAMP e TICKER.
    """
    column_mapping = {
        'date': 'TIMESTAMP',
        'symbol': 'TICKER',
        'open': 'OPEN',
        'high': 'HIGH',
        'low': 'LOW',
        'close': 'CLOSE',
        'adjustedClose': 'ADJUSTED_CLOSE',
        'volume': 'VOLUME'
    }
    
    # Verificar quais colunas do mapeamento estão presentes no DataFrame
    existing_columns = [col for col in column_mapping.keys() if col in df.columns]
    
    # Renomear as colunas que estão presentes no DataFrame
    df = df.rename(columns={col: column_mapping[col] for col in existing_columns})
    
    # Selecionar apenas as colunas que foram renomeadas e estão presentes
    df = df[[column_mapping[col] for col in existing_columns]]
    
    # Adicionar a chave primária concatenando TIMESTAMP e TICKER
    df['ID'] = df['TIMESTAMP'].astype(str) + '_' + df['TICKER']
    
    return df

def transform_indexes_data(data: dict, index_name: str, start_date: str, end_date: str) -> pd.DataFrame:
    """
    Transforma os dados de índice extraídos para um DataFrame com as colunas 'DATE' e o índice.
    Repete os valores mensais para cada dia até o próximo valor disponível.
    """
    df = pd.DataFrame(data)
    df['data'] = pd.to_datetime(df['data'], dayfirst=True)  # Convertendo para datetime
    df['valor'] = pd.to_numeric(df['valor'], errors='coerce')  # Garantir que os valores sejam numéricos

    # Gerar um intervalo de datas completo entre start_date e end_date
    all_dates = pd.date_range(start=start_date, end=end_date, freq='D')
    df = df.set_index('data').reindex(all_dates).ffill().reset_index()
    df = df.rename(columns={'index': 'DATE', 'valor': index_name})

    return df

def load_data(df: pd.DataFrame, table: str, key_columns: list) -> None:
    """
    Carrega os dados no banco de dados com upsert (inserção ou atualização).
    key_columns: lista das colunas que formam a chave para a operação de upsert.
    """
    # Conectando ao banco de dados
    connection = f'mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_SERVER}:{DB_PORT}/{DB_NAME}'
    engine = create_engine(connection)
    
    # Usando a conexão do SQLAlchemy
    with engine.connect() as conn:
        trans = conn.begin()  # Inicia uma transação
        try:
            for index, row in df.iterrows():
                # Criando uma lista de colunas e valores para o INSERT
                columns = ', '.join(df.columns)
                values = ', '.join([f"'{str(x)}'" if pd.notna(x) else 'NULL' for x in row])

                # Criando o comando de atualização (ON DUPLICATE KEY UPDATE)
                update_clause = ', '.join([f"{col}=VALUES({col})" for col in df.columns if col not in key_columns])

                # Query de inserção com upsert
                insert_query = f"""
                INSERT INTO {table} ({columns})
                VALUES ({values})
                ON DUPLICATE KEY UPDATE {update_clause};
                """
                
                # Usando text() para passar a string SQL
                conn.execute(text(insert_query))
            
            trans.commit()  # Confirma a transação se tudo der certo
        except Exception as e:
            trans.rollback()  # Reverte a transação em caso de erro
            print(f"Erro durante a inserção de dados na tabela {table}: {e}")
            
    print(f'Dados da tabela {table} carregados com sucesso')
    
    return

def get_companies() -> None:
    """
    Obtém os dados de todos os tickers de uma vez, transforma os dados e carrega no banco de dados.
    """
    # Extract all tickers data
    df_extracted = extract_company_data(TICKER_LIST)
    
    # Transform ticker dataframe
    df_transformed = transform_company_data(df_extracted)
    
    # Load ticker dataframe into database
    table = 'TICKERS'
    key_columns = ['ticker']
    load_data(df_transformed, table, key_columns)
    
    return

def get_prices() -> None:
  
  # Extract all tickers data
  df_extracted = extract_price_data(TICKER_LIST)
  
  # Transform ticker dataframe
  df_transformed = transform_price_data(df_extracted)
  
  table = 'PRICES' 
  key_columns = ['id']  
  load_data(df_transformed, table, key_columns)
    
  return

def get_indexes() -> None:
    """
    Combina os dados de todos os índices em um único DataFrame, com a data como chave primária.
    Cada índice é uma nova coluna com os valores preenchidos.
    """
    start_date = '01/01/2023'
    end_date = '31/12/2023'
    
    df_final = pd.DataFrame()  # DataFrame para combinar todos os índices

    for index_name, serie_code in INDEX_LIST.items():
        # Extrair e transformar os dados do índice
        data = extract_indexes_data(serie_code, start_date, end_date)
        df_index = transform_indexes_data(data, index_name, start_date, end_date)

        if df_final.empty:
            df_final = df_index  # Inicializa com o primeiro índice
        else:
            # Merge com base na coluna 'DATE', adicionando o novo índice como nova coluna
            df_final = pd.merge(df_final, df_index, on='DATE', how='outer')
    
    df_final = df_final.sort_values(by='DATE').reset_index(drop=True)
    
    # Definir 'DATE' como a chave primária e carregar os dados no banco de dados
    table = 'INDEXES'
    key_columns = ['DATE']
    load_data(df_final, table, key_columns)

    return
  
def main():
    
  get_companies()
  get_prices()
  get_indexes()
  
  print("Todos os dados foram extraídos, tratados e carregados com sucesso")

if __name__ == "__main__":
    main()