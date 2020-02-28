create database medicalc;

use medicalc;

-- drop database medicalc;

create table room (
	Rno int not null,
    Room_Type varchar(30) not null,
    Room_Cost int not null,
    primary key(Rno)
);

create table users (
	Type varchar(10) not null,
    User_Name varchar(30) not null,
    Password varchar(32) not null,
    Id int not null,
    primary key(Id)
);

create table drug (
	Trade_Name varchar(30) not null,
    Price float not null,
    primary key(Trade_Name)
);

create table nurse (
	Nid int not null,
    Name varchar(30) not null,
    Sex char(1) not null,
    Phone_No bigint not null,
    Room_No int,
    Shift_Start int not null,
    Shift_End int not null,
    Date_Of_Joining date not null,
    primary key(Nid),
    foreign key(Room_No) references room(Rno) on update cascade on delete cascade
);


create table doctor (
	D_Name varchar(30) not null,
    Speciality varchar(30) not null,
    Phy_Id int not null,
    Phone_No bigint not null,
    Date_Of_Joining date not null,
    Sex char(1) not null,
    primary key(Phy_Id)
);

create table clerk (
	Clerk_Id int not null,
    Name varchar(30) not null,
    Contact_No bigint not null,
    Date_Of_Joining date not null,
    Sex char(1) not null,
    primary key(Clerk_Id)
);

create table patient (
	Pid int not null,
    Name varchar(50) not null,
    DOB date not null,
    Sex char(1) not null,
    Address varchar(50) not null,
    Contact_No bigint not null,
    Room_Start date,
    Room_End date,
    Nid int,
    Date date not null,
    primary key(Pid),
    foreign key(Nid) references nurse(Nid) on delete cascade on update cascade
);

create table admitted_in (
	Pid int,
    Rno int,
    Bed_No int not null,
    Date date not null,
    primary key(Pid, Rno, Bed_No, Date),
    foreign key(Pid) references patient(Pid) on update cascade on delete cascade,
    foreign key(Rno) references room(Rno) on update cascade on delete cascade
);

create table prescribe (
	Pid int,
    Phy_Id int,
    Trade_Name varchar(30),
    Date date not null,
    Quantity int not null,
    Ailment varchar(50) not null,
    foreign key(Pid) references patient(Pid) on update cascade on delete cascade,
    foreign key(Phy_Id) references doctor(Phy_Id) on update cascade on delete cascade,
    foreign key(Trade_Name) references drug(Trade_Name) on update cascade on delete cascade,
    primary key(Pid, Phy_Id, Date)
);

insert into doctor values ('', '', 0, 0, '0000-00-00', '');
insert into room values (0, '', 0);
insert into drug values ('none', 0);
insert into nurse values (0, '', '', 0, 0, 0, 0, '0000-00-00');

select * from doctor;
select * from users;
select * from clerk;
select * from drug;
select * from room;
select * from patient;
select * from nurse;
select * from prescribe;
select * from admitted_in;


