INSERT INTO ACCOUNT (id, balance) SELECT 12345678, 10000000 where not exists(select * from ACCOUNT where id = 12345678);
INSERT INTO ACCOUNT (id, balance) SELECT 88888888, 10000000 where not exists(select * from ACCOUNT where id = 88888888);
