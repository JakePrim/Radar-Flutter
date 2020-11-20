drop database if exists db_cake;

create database db_cake;

use db_cake;

drop table if exists account;

drop table if exists cake;

drop table if exists category;

create table if not exists account(
	id int unsigned auto_increment,
	account varchar(20) not null UNIQUE,
	password varchar(20) not null,
	nick_name varchar(20),
	primary key (id)
);

create table if not exists category(
	id int unsigned auto_increment,
	title varchar(20) not null unique,
	pid int unsigned,
	info varchar(100),
	primary key(id),
	constraint FK_category_pid foreign key (pid) references category (id) 
);

create table if not exists cake(
	id int unsigned auto_increment,
	title varchar(20) not null unique,
	cid int unsigned not null,
	image_path varchar(100),
	price double not null,
	taste varchar(20),
	sweetness int,
	weight double,
	size int,
	material varchar(100),
	status varchar(20),
	primary key (id),
	constraint FK_category_id foreign key (cid) references category (id)
);

insert into account(id,account,password,nick_name) values(10000,'admin','admin','管理员');

insert into catalog(id,title) values(10000,'蛋糕');


