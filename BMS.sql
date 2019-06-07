use BookManagementSystem;

drop table transactions
drop table books
drop table users

create table books(
	[name] varchar(50) primary key not null,
	publisher varchar(50) not null,
	author varchar(50) not null,
	year int not null,
	available int not null,
	category varchar(50) not null,
);

create table users(
	uname varchar(50) primary key not null,
	passwd varchar(50) not null,
	[address] varchar(50) not null,
	contact varchar(50) not null
);

create table [transactions](
	uname varchar(50) not null,
	bname varchar(50) not null,
	dueDate varchar(50) not null,
	primary key (uname, bname),
	foreign key (uname) references users,
	foreign key (bname) references books
);