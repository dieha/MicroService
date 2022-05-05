
CREATE TABLE account( 
	account_id  IDENTITY,
	identity  VARCHAR(10),
	name  VARCHAR(20),
	account VARCHAR(20),
	password VARCHAR(50),
	email VARCHAR(100),
	create_date DATETIME,
    PRIMARY KEY(account_id));

CREATE TABLE  bank_code( 
	code  VARCHAR(3),
	bank_name  VARCHAR(20),
    PRIMARY KEY(code));

CREATE TABLE  transfer( 
    transfer_id  IDENTITY,
	form_account_no  VARCHAR(14),
	to_bank_code  VARCHAR(3),
	to_account_no  VARCHAR(14),
	currency VARCHAR(2),
	amount DECIMAL(20,2) ,
	status VARCHAR(5) ,
	transfer_date DATETIME,
	create_date DATETIME,
    PRIMARY KEY(transfer_id));


CREATE TABLE  deposit( 
	account_no VARCHAR(14),
	account_id BIGINT,
	currency VARCHAR(2),
	amount DECIMAL(20,2) ,

    PRIMARY KEY(account_no));
 
 

