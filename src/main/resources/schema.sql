create sequence	IF NOT EXISTS hibernate_sequence start with 1 increment by 1
create table IF NOT EXISTS account (id bigint not null, balance decimal(19,2), primary key (id))
create table IF NOT EXISTS transaction (id bigint not null, accountid bigint, amount decimal(19,2), balance decimal(19,2), reference varchar(255), transaction_time timestamp, primary key (id))
create table IF NOT EXISTS transfer (id bigint not null, amount decimal(19,2), from_account_id bigint, reference varchar(255), status integer, to_account_id bigint, transfer_time timestamp, primary key (id))
