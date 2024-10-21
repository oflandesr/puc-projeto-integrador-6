import mysql.connector
from dotenv import load_dotenv
import os

# Carregar o arquivo .env
current_dir = os.path.dirname(os.path.abspath(__file__))
env_path = os.path.join(current_dir, '..', 'keys.env')
load_dotenv(dotenv_path=env_path)

# Obter as variáveis de ambiente
DB_HOST = os.getenv('DB_HOST')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')
DB_NAME = os.getenv('DB_NAME')

# Criar tabela TICKER
CREATE_TABLE_TICKERS = """
CREATE TABLE IF NOT EXISTS TICKERS (
    TICKER VARCHAR(10) PRIMARY KEY,
    CURRENCY VARCHAR(10),
    SHORT_NAME VARCHAR(50),
    LONG_NAME VARCHAR(100),
    ADDRESS_2 VARCHAR(100),
    CITY VARCHAR(50),
    STATE VARCHAR(50),
    ZIP VARCHAR(20),
    COUNTRY VARCHAR(50),
    PHONE VARCHAR(20),
    WEBSITE VARCHAR(100),
    LOGO_URL VARCHAR(255),
    INDUSTRY VARCHAR(100),
    SECTOR VARCHAR(100),
    BUSINESS_SUMMARY TEXT,
    NUMBER_OF_EMPLOYEES INT,
    MARKET_CAP FLOAT,
    200_DAY_AVERAGE FLOAT,
    200_DAY_AVERAGE_CHANGE FLOAT,
    200_DAY_AVERAGE_CHANGE_PERCENT FLOAT,
    REGULAR_MARKET_CHANGE FLOAT,
    REGULAR_MARKET_CHANGE_PERCENT FLOAT,
    REGULAR_MARKET_TIME VARCHAR(50),
    REGULAR_MARKET_PRICE FLOAT,
    REGULAR_MARKET_DAY_HIGH FLOAT,
    REGULAR_MARKET_DAY_LOW FLOAT,
    REGULAR_MARKET_DAY_RANGE VARCHAR(50),
    REGULAR_MARKET_VOLUME INT,
    REGULAR_MARKET_PREVIOUS_CLOSE FLOAT,
    REGULAR_MARKET_OPEN FLOAT,
    AVERAGE_DAILY_VOLUME_3_MONTHS INT,
    AVERAGE_DAILY_VOLUME_10_DAYS INT,
    52_WEEKS_LOW_CHANGE FLOAT,
    52_WEEKS_HIGH_CHANGE FLOAT,
    52_WEEKS_HIGH_CHANGE_PERCENT FLOAT,
    52_WEEKS_RANGE VARCHAR(50),
    52_WEEKS_LOW FLOAT,
    52_WEEKS_HIGH FLOAT,
    PRICE_EARNINGS FLOAT,
    EARNINGS_PER_SHARE FLOAT
);
"""

# Criar tabela TICKER
CREATE_TABLE_TICKER = """
CREATE TABLE IF NOT EXISTS TICKER (
    TICKER VARCHAR(10) PRIMARY KEY,
    CURRENCY VARCHAR(10),
    SHORT_NAME VARCHAR(50),
    LONG_NAME VARCHAR(100),
    ADDRESS_2 VARCHAR(100),
    CITY VARCHAR(50),
    STATE VARCHAR(50),
    ZIP VARCHAR(20),
    COUNTRY VARCHAR(50),
    PHONE VARCHAR(20),
    WEBSITE VARCHAR(100),
    LOGO_URL VARCHAR(255),
    INDUSTRY VARCHAR(100),
    SECTOR VARCHAR(100),
    BUSINESS_SUMMARY TEXT,
    NUMBER_OF_EMPLOYEES INT,
    MARKET_CAP FLOAT,
    200_DAY_AVERAGE FLOAT,
    200_DAY_AVERAGE_CHANGE FLOAT,
    200_DAY_AVERAGE_CHANGE_PERCENT FLOAT,
    REGULAR_MARKET_CHANGE FLOAT,
    REGULAR_MARKET_CHANGE_PERCENT FLOAT,
    REGULAR_MARKET_TIME VARCHAR(50),
    REGULAR_MARKET_PRICE FLOAT,
    REGULAR_MARKET_DAY_HIGH FLOAT,
    REGULAR_MARKET_DAY_LOW FLOAT,
    REGULAR_MARKET_DAY_RANGE VARCHAR(50),
    REGULAR_MARKET_VOLUME INT,
    REGULAR_MARKET_PREVIOUS_CLOSE FLOAT,
    REGULAR_MARKET_OPEN FLOAT,
    AVERAGE_DAILY_VOLUME_3_MONTHS INT,
    AVERAGE_DAILY_VOLUME_10_DAYS INT,
    52_WEEKS_LOW_CHANGE FLOAT,
    52_WEEKS_HIGH_CHANGE FLOAT,
    52_WEEKS_HIGH_CHANGE_PERCENT FLOAT,
    52_WEEKS_RANGE VARCHAR(50),
    52_WEEKS_LOW FLOAT,
    52_WEEKS_HIGH FLOAT,
    PRICE_EARNINGS FLOAT,
    EARNINGS_PER_SHARE FLOAT
);
"""

# Criar tabela PRICE
CREATE_TABLE_PRICES = """
CREATE TABLE IF NOT EXISTS PRICES (
    ID VARCHAR(128) PRIMARY KEY,
    TIMESTAMP BIGINT,
    TICKER VARCHAR(10),
    OPEN DECIMAL(10, 2),
    HIGH DECIMAL(10, 2),
    LOW DECIMAL(10, 2),
    CLOSE DECIMAL(10, 2),
    ADJUSTED_CLOSE DECIMAL(10, 2),
    VOLUME DECIMAL(20, 2),
    FOREIGN KEY (TICKER) REFERENCES TICKERS(TICKER)
);
"""

# Criar tabela PRICE
CREATE_TABLE_PRICE = """
CREATE TABLE IF NOT EXISTS PRICE (
    ID VARCHAR(128) PRIMARY KEY,
    TIMESTAMP BIGINT,
    TICKER VARCHAR(10),
    OPEN DECIMAL(10, 2),
    HIGH DECIMAL(10, 2),
    LOW DECIMAL(10, 2),
    CLOSE DECIMAL(10, 2),
    ADJUSTED_CLOSE DECIMAL(10, 2),
    VOLUME DECIMAL(20, 2),
    FOREIGN KEY (TICKER) REFERENCES TICKER(TICKER)
);
"""

# Criar tabela USER
CREATE_TABLE_USERS = """
CREATE TABLE IF NOT EXISTS USERS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    LOGIN VARCHAR(50),
    PASSWORD VARCHAR(255),
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    ENABLED INT
);
"""

CREATE_TABLE_USER = """
CREATE TABLE IF NOT EXISTS USER (
    ID INT AUTO_INCREMENT,
    LOGIN VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    ENABLED INT NOT NULL,
    UNIQUE INDEX UL (LOGIN),
    CONSTRAINT L_PK PRIMARY KEY (ID)
);
"""

# Criar tabela INDEX
CREATE_TABLE_INDEXES = """
CREATE TABLE IF NOT EXISTS INDEXES (
    DATE DATE PRIMARY KEY,
    SELIC FLOAT,
    CDI FLOAT,
    IPCA FLOAT
);
"""

CREATE_TABLE_INDEX = """
CREATE TABLE IF NOT EXISTS `INDEX` (
    `DATE` DATE PRIMARY KEY,
    SELIC FLOAT,
    CDI FLOAT,
    IPCA FLOAT
);
"""

# Criar tabela TRANSACTIONS_FIXED_INCOME
CREATE_TABLE_TRANSACTIONS_FIXED_INCOME = """
CREATE TABLE IF NOT EXISTS TRANSACTIONS_FIXED_INCOME (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    WALLET_ID INT,
    INSTITUTION VARCHAR(50),
    TYPE VARCHAR(50),
    VALUE INT,
    START_DATE DATE,
    END_DATE DATE,
    INDEX_NAME VARCHAR(10),
    TAX_VALUE INT,
    FOREIGN KEY (WALLET_ID) REFERENCES WALLETS(ID)
);
"""

CREATE_TABLE_TRANSACTION_FIXED_INCOME = """
CREATE TABLE IF NOT EXISTS TRANSACTION_FIXED_INCOME (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    WALLET_ID INT,
    INSTITUTION VARCHAR(50),
    TYPE VARCHAR(50),
    VALUE INT,
    START_DATE DATE,
    END_DATE DATE,
    INDEX_NAME VARCHAR(10),
    TAX_VALUE INT,
    FOREIGN KEY (WALLET_ID) REFERENCES WALLET(ID)
);
"""

# Criar tabela TRANSACTION_VARIABLE_INCOME
CREATE_TABLE_TRANSACTIONS_VARIABLE_INCOME = """
CREATE TABLE IF NOT EXISTS TRANSACTIONS_VARIABLE_INCOME (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    WALLET_ID INT,
    TICKER_ID VARCHAR(10),
    BUY_OR_SALE INT,
    DATE DATE,
    AMOUNT INT,
    PRICE FLOAT,
    FOREIGN KEY (WALLET_ID) REFERENCES WALLETS(ID),
    FOREIGN KEY (TICKER_ID) REFERENCES TICKERS(TICKER)
);
"""

CREATE_TABLE_TRANSACTION_VARIABLE_INCOME = """
CREATE TABLE IF NOT EXISTS TRANSACTION_VARIABLE_INCOME (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    WALLET_ID INT,
    TICKER_ID VARCHAR(10),
    BUY_OR_SALE INT,
    DATE DATE,
    AMOUNT INT,
    PRICE FLOAT,
    FOREIGN KEY (WALLET_ID) REFERENCES WALLET(ID),
    FOREIGN KEY (TICKER_ID) REFERENCES TICKER(TICKER)
);
"""

# Criar tabela WALLET
CREATE_TABLE_WALLETS = """
CREATE TABLE IF NOT EXISTS WALLETS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(50),
    OBJECTIVE VARCHAR(100),
    INTENDED_FIXED_INCOME_PERCENT INT,
    INTENDED_STOCK_PERCENT INT,
    INTENDED_FII_PERCENT INT,
    USER_ID INT,
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
"""

CREATE_TABLE_WALLET = """
CREATE TABLE IF NOT EXISTS WALLET (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(50),
    OBJECTIVE VARCHAR(100),
    INTENDED_FIXED_INCOME_PERCENT INT,
    INTENDED_STOCK_PERCENT INT,
    INTENDED_FII_PERCENT INT,
    USER_ID INT,
    FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);
"""

CREATE_TABLE_ROLES = """
CREATE TABLE IF NOT EXISTS ROLES (
    ID INT AUTO_INCREMENT PRIMARY KEY,
	ROLE VARCHAR(255),
	DESCRIPTION VARCHAR(255)
);
"""

CREATE_TABLE_ROLE = """
CREATE TABLE IF NOT EXISTS ROLE (
    ROLE VARCHAR(45) NOT NULL,
    DESCRIPTION VARCHAR(255) NULL,
    CONSTRAINT ROLE_PK PRIMARY KEY (ROLE)
);
"""

CREATE_TABLE_PERMISSIONS = """
CREATE TABLE IF NOT EXISTS PERMISSIONS (
    USER_ID INT,
    ROLE_ID INT,
	FOREIGN KEY (ROLE_ID) REFERENCES ROLES(ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
"""

CREATE_TABLE_PERMISSION = """
CREATE TABLE IF NOT EXISTS PERMISSION (
    LOGIN VARCHAR(45) NOT NULL,
    ROLE VARCHAR(45) NOT NULL,
    FOREIGN KEY (LOGIN) REFERENCES USER (LOGIN),
	FOREIGN KEY (ROLE) REFERENCES ROLE (ROLE)
);
"""

# Inserir o usuário admin na tabela LOGIN (chtt24)
INSET_INTO_USER ="""
    INSERT INTO USER (LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, ENABLED) 
    VALUES ('admin', '$2a$10$Usgy3IEMcOK1l5plvyKONeysRa/mACW9677CQaCyJi0P2u7fBykJ.', 'ADMINISTRADOR', ' DO SISTEMA', TRUE);
"""

# Inserir uma ROLE de administrador na tabela ROLE
INSERT_INTO_ROLE = """
    INSERT INTO ROLE (ROLE, DESCRIPTION) 
    VALUES ('ADMIN', 'Administrador do sistema');
"""

# Inserir uma ROLE de usuario na tabela ROLE
INSERT_INTO_ROLE_ = """
    INSERT INTO ROLE (ROLE, DESCRIPTION) 
    VALUES ('USER', 'Usuário do sistema');
"""

# Associar o usuário admin com a ROLE ADMIN
INSERT_INTO_PERMISSION = """
    INSERT INTO PERMISSION (LOGIN, ROLE) 
    VALUES ('admin', 'ADMIN');
"""

INSERT_INTO_PERMISSION_ = """
    INSERT INTO PERMISSION (LOGIN, ROLE) 
    VALUES ('admin', 'USER');
"""

TABLES = {
    'tickers':                      CREATE_TABLE_TICKERS,
    'indexes':                      CREATE_TABLE_INDEXES,
    'users':                        CREATE_TABLE_USERS,
    'prices':                       CREATE_TABLE_PRICES,
    'wallets':                      CREATE_TABLE_WALLETS,
    'roles':                        CREATE_TABLE_ROLES,
    'permissions':                  CREATE_TABLE_PERMISSIONS,
    'transactions_variable_income': CREATE_TABLE_TRANSACTIONS_VARIABLE_INCOME,
    'transactions_fixed_income':    CREATE_TABLE_TRANSACTIONS_FIXED_INCOME,
    'ticker':                       CREATE_TABLE_TICKER,
    'index':                        CREATE_TABLE_INDEX,
    'user':                         CREATE_TABLE_USER,
    'price':                        CREATE_TABLE_PRICE,
    'wallet':                       CREATE_TABLE_WALLET,
    'role':                         CREATE_TABLE_ROLE,
    'permission':                   CREATE_TABLE_PERMISSION,
    'transaction_variable_income':  CREATE_TABLE_TRANSACTION_VARIABLE_INCOME,
    'transaction_fixed_income':     CREATE_TABLE_TRANSACTION_FIXED_INCOME,
}

INSERTS = {
    'user':                         INSET_INTO_USER, 
    'role_adm':                     INSERT_INTO_ROLE, 
    'role_usr':                     INSERT_INTO_ROLE_,
    'permission_adm':               INSERT_INTO_PERMISSION, 
    'permission_usr':               INSERT_INTO_PERMISSION_
}

import mysql.connector
from mysql.connector import Error

def insert_table(insert:str, query: str) -> None:
    
    try:
        # Conectando ao banco de dados
        db = mysql.connector.connect(
            host=DB_HOST,
            user=DB_USER,
            password=DB_PASSWORD,
            database=DB_NAME
        )

        cursor = db.cursor()

        try:
            # Executando a query para criar a tabela
            cursor.execute(query)
            db.commit()
            print(f"Insert {insert} executado com sucesso!")

        except mysql.connector.Error as e:
            # Captura de erro de execução da query
            print(f"Erro ao executar a query: {e}")
            db.rollback()  # Reverter mudanças em caso de erro

        finally:
            # Garantir que o cursor seja fechado
            cursor.close()

    except Error as e:
        # Captura de erros de conexão
        print(f"Erro ao conectar ao banco de dados: {e}")

    finally:
        # Garantir que a conexão com o banco seja fechada
        if db.is_connected():
            db.close()
            
    return

def create_table(table:str, query: str) -> None:
    
    try:
        # Conectando ao banco de dados
        db = mysql.connector.connect(
            host=DB_HOST,
            user=DB_USER,
            password=DB_PASSWORD,
            database=DB_NAME
        )

        cursor = db.cursor()

        try:
            # Executando a query para criar a tabela
            cursor.execute(query)
            db.commit()
            print(f"Tabela {table} criada com sucesso!")

        except mysql.connector.Error as e:
            # Captura de erro de execução da query
            print(f"Erro ao executar a query: {e}")
            db.rollback()  # Reverter mudanças em caso de erro

        finally:
            # Garantir que o cursor seja fechado
            cursor.close()

    except Error as e:
        # Captura de erros de conexão
        print(f"Erro ao conectar ao banco de dados: {e}")

    finally:
        # Garantir que a conexão com o banco seja fechada
        if db.is_connected():
            db.close()
            
    return

def main():
  
  for table, query in TABLES.items():
    create_table(table, query)
  
  for insert, query in INSERTS.items():
    insert_table(insert, query)
    
  return

if __name__ == "__main__":
  main()