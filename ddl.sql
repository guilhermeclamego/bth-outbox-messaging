------Postgres
--schema
CREATE SCHEMA OUTBOX_DRIVEN;

--sequence
CREATE SEQUENCE EVENT_SEQUENCE_ID
    INCREMENT 1
START 1;

--table
CREATE TABLE 'OUTBOX_DRIVEN.EVENT'
(
    id serial PRIMARY KEY,
    aggregateId varchar(30),
    aggregateType varchar(30),
    register varchar(20),
    eventType varchar(30),
    payload jsonb
);


--Oracle
CREATE TABLE 'OUTBOX_DRIVEN.EVENT'
(
    id serial PRIMARY KEY,
    aggregateType varchar(30),
    aggregateId varchar(30),
    register varchar(20),
    eventType varchar(30),
    payload clob
    CONSTRAINT "PK_OUTBOX_EVENT_DRIVEN_ID" PRIMARY KEY ("ID")
);