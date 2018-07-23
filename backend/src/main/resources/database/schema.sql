create table bank
(
	id bigint auto_increment primary key,
	address varchar(255) null,
	city varchar(255) null,
	department varchar(255) null,
	name varchar(255) null,
	post_code varchar(6) null,
	sort_code varchar(8) null
) engine=MyISAM;

create table bank_account
(
	id bigint auto_increment primary key,
	account_no varchar(26) null,
	is_active bit null,
	first_name varchar(15) null,
	last_name varchar(25) null,
	money_amount decimal(19,2) null,
	money_blocked decimal(19,2) null,
	constraint UK_account_no unique (account_no)
) engine=MyISAM;

create table transfer
(
	id bigint auto_increment primary key,
	account_no varchar(26) null,
	booking_date date null,
	create_time datetime null,
	status varchar(8) null,
	title varchar(255) null,
	transfer_type varchar(8) null,
	value decimal(19,2) null,
	account_id bigint not null
) engine=MyISAM;

create index FK_account on transfer (account_id);

