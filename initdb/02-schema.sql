-- 01-init-all.sql

-- 1) Crear ambas bases
CREATE DATABASE db_customer;
CREATE DATABASE db_transaction;

-- 2) Switch a db_customer y crear sus tablas
\connect db_customer

CREATE TABLE public.tperson (
    id bigserial NOT NULL,
    identification varchar(50) NOT NULL,
    "name" varchar(100) NOT NULL,
    gender bpchar(1) NULL,
    age int4 NULL,
    addresses text NULL,
    phone varchar(20) NULL,
    CONSTRAINT tperson_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tcustomer (
    id int8 NOT NULL,
    state bpchar(1) NOT NULL,
    "password" varchar(100) NOT NULL,
    CONSTRAINT tcustomer_pkey PRIMARY KEY (id),
    CONSTRAINT tcustomer_id_fkey FOREIGN KEY (id)
      REFERENCES public.tperson(id) ON DELETE CASCADE
);

-- 3) Switch a db_transaction y crear sus tablas
\connect db_transaction

CREATE TABLE public.taccount (
    id bigserial NOT NULL,
    number_account varchar(50) NOT NULL,
    type_account varchar(30) NOT NULL,
    initial_balance numeric(19,4) NOT NULL,
    state varchar(30) NOT NULL,
    customer_id int8 NOT NULL,
    date_create timestamp DEFAULT now() NOT NULL,
    CONSTRAINT taccount_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_taccount_customer ON public.taccount(customer_id);
CREATE INDEX idx_taccount_number   ON public.taccount(number_account);

CREATE TABLE public.ttransaction (
    id bigserial NOT NULL,
    date_transaction timestamp DEFAULT now() NOT NULL,
    type_transaction varchar(30) NOT NULL,
    value numeric(19,4) NOT NULL,
    balance numeric(19,4) NOT NULL,
    state varchar(30) NOT NULL,
    account_id int8 NOT NULL,
    date_from timestamp NULL,
    date_to   timestamp NULL,
    CONSTRAINT ttransaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id)
      REFERENCES public.taccount(id) ON DELETE CASCADE
);
CREATE INDEX idx_ttransaction_account ON public.ttransaction(account_id);
