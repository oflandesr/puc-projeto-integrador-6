-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-09-26 21:26:06.848

-- tables
-- Table: Login
CREATE TABLE Login (
    id int NOT NULL AUTO_INCREMENT,
    login varchar(45) NOT NULL,
    password varchar(128) NOT NULL,
    enabled bool NOT NULL DEFAULT true,
    UNIQUE INDEX AK_0 (login),
    CONSTRAINT Login_Pk PRIMARY KEY (id)
);

-- Table: Login_Role
CREATE TABLE Login_Role (
    id int NOT NULL AUTO_INCREMENT,
    login varchar(45) NOT NULL,
    role varchar(45) NOT NULL,
    CONSTRAINT Login_Role_Pk PRIMARY KEY (id)
);

-- Table: Role
CREATE TABLE Role (
    id int NOT NULL AUTO_INCREMENT,
    role varchar(45) NOT NULL,
    description varchar(255) NULL,
    UNIQUE INDEX AK_3 (role),
    CONSTRAINT Role_Pk PRIMARY KEY (id)
);

-- Table: Usuario
CREATE TABLE Usuario (
    id int NOT NULL AUTO_INCREMENT,
    login varchar(45) NOT NULL,
    first_name varchar(45) NOT NULL,
    last_name varchar(255) NOT NULL,
    UNIQUE INDEX AK_4 (login),
    CONSTRAINT Usuario_Pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Login_Role_Login (table: Login_Role)
ALTER TABLE Login_Role ADD CONSTRAINT Login_Role_Login FOREIGN KEY Login_Role_Login (login)
    REFERENCES Login (login);

-- Reference: Login_Role_Role (table: Login_Role)
ALTER TABLE Login_Role ADD CONSTRAINT Login_Role_Role FOREIGN KEY Login_Role_Role (role)
    REFERENCES Role (role);

-- Reference: Usuario_Login (table: Usuario)
ALTER TABLE Usuario ADD CONSTRAINT Usuario_Login FOREIGN KEY Usuario_Login (login)
    REFERENCES Login (login);
    
-- 
-- 
-- 
-- 
-- 
-- 
-- Inserir o usuário admin na tabela LOGIN (chtt24)
INSERT INTO Login (login, password, enabled) 
VALUES ('admin', '$2a$10$G2db2lZ7soC3n3lAdn5uiO1dDF0aPllZUQamOCdTzEKfw7CyftBdy', TRUE);

-- Inserir os dados do usuário na tabela USER (associando ao LOGIN_ID)
INSERT INTO Usuario (login, first_name, last_name)
VALUES ('admin', 'Admin', 'User');

-- Inserir uma role de administrador na tabela ROLE
INSERT INTO Role (role, description) 
VALUES ('ADMIN', 'Administrador do sistema');

-- Inserir uma role de usuario na tabela ROLE
INSERT INTO Role (role, description) 
VALUES ('USER', 'Usuário do sistema');

-- Associar o usuário admin com a role ADMIN
INSERT INTO Login_Role (login, role) 
VALUES ('admin', 'ADMIN');

INSERT INTO Login_Role (login, role) 
VALUES ('admin', 'USER');

-- End of file.
