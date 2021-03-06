﻿/*DROP TABLE PERSON_PREFERENCE;
DROP TABLE PERSON_ACTIVITY_COMMENT;
DROP TABLE REACTION;
DROP TABLE LOGIN;
DROP TABLE ACTIVITY;
DROP TABLE PERSON;
DROP TABLE ACTIVITY_TYPE;*/


CREATE TABLE PERSON_TYPE(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(255) NOT NULL
);

CREATE TABLE PERSON(
	ID SERIAL PRIMARY KEY,
	FIRST_NAME VARCHAR(255) NOT NULL,
	LAST_NAME VARCHAR(255) NOT NULL,
	EMAIL VARCHAR(255) NOT NULL,
	AGE DATE,
	PERSON_TYPE_ID INT NOT NULL REFERENCES PERSON_TYPE
);

CREATE TABLE LOGIN(
	ID SERIAL PRIMARY KEY,
	PERSON_ID INT NOT NULL REFERENCES PERSON,
	LOGIN_NAME VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(255) NOT NULL
);

CREATE TABLE ACTIVITY(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(255) NOT NULL,
	SHORT_DESCRIPTION VARCHAR(255) NOT NULL,
	DESCRIPTION VARCHAR(255),
	LOCATION VARCHAR(255) NOT NULL,
	PRICE DECIMAL(10,2),
	PERSON_ID INT NOT NULL REFERENCES PERSON
);

CREATE TABLE ACTIVITY_TYPE(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(255) NOT NULL
);

CREATE TABLE PERSON_ACTIVITY_COMMENT(
	ID SERIAL PRIMARY KEY,
	COMMENT VARCHAR(1500) NOT NULL,
	PERSON_ID INT NOT NULL REFERENCES PERSON,
	ACTIVITY_ID INT NOT NULL REFERENCES ACTIVITY
);

CREATE TABLE REACTION(
	ID SERIAL PRIMARY KEY,
	REACTION CHAR(1) NOT NULL,
	PERSON_ID INT NOT NULL REFERENCES PERSON,
	ACTIVITY INT NOT NULL REFERENCES ACTIVITY
);

COMMENT ON COLUMN REACTION.REACTION IS 'I - INTERESTED, M - MAY GO, N - NO WAY TO GO';

CREATE TABLE PERSON_PREFERENCE(
	ID SERIAL PRIMARY KEY,
	PERSON_ID INT NOT NULL REFERENCES PERSON,
	ACTIVITY_TYPE_ID INT NOT NULL REFERENCES ACTIVITY_TYPE
);

/*INSERT INTO PERSON_TYPE VALUES (1,'ADMIN');
INSERT INTO PERSON_TYPE VALUES (2,'EVENT_MANAGER');
INSERT INTO PERSON_TYPE VALUES (3,'COMMON');
INSERT INTO PERSON VALUES (DEFAULT,'LUCAS ALESSANDRO','DE OLIVEIRA SOARES','LUCAS.ALESSANRO2@GMAIL.COM','1994-04-12',1);*/


/*
CREATE TABLE COUNTRY(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(255)
)

CREATE TABLE PROVINCE(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(255)
)

CREATE TABLE CITY(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(255),
)
*/
DROP TABLE PERSON_TYPE;