CREATE SCHEMA CHATRMI;
#CREATE DATEBASE CHATRMI;
USE CHATRMI;

CREATE TABLE T_USUARIO (
	ID_USUARIO INT PRIMARY KEY auto_increment,
    APELIDO VARCHAR(20) NOT NULL,
    SENHA VARCHAR(10) NOT NULL,
    FL_ONLINE VARCHAR(1) COMMENT 'USUARIO ONLINE (S)IM, (N)AO'    
	);
    
CREATE TABLE T_MENSAGEM (
	ID_MENSAGEM INT PRIMARY KEY auto_increment,
    ID_USUARIO INT,
    CONTEUDO VARCHAR(200) NOT NULL,
	DATA DATETIME,
    foreign key(ID_USUARIO) references T_USUARIO(ID_USUARIO));