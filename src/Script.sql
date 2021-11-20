drop database if exists saloon_System;
create database if not exists saloon_System;
use saloon_System;
drop table if exists customer;
create table if not exists customer(
                                       cust_NIC varchar(20) not null ,
                                       cust_Name varchar(30) not null,
                                       cust_Age int(2),
                                       cust_Hair_Style varchar(30),
                                       constraint primary key (cust_NIC)
);
show tables ;
desc customer;

drop table if exists orders;
create table if not exists orders(
                                     order_Id varchar(6) not null ,
                                     customer_Nic varchar(10),
                                     constraint primary key (order_Id),
                                     constraint foreign key (customer_Nic) references customer(cust_NIC) on delete cascade on update cascade
);
show tables ;
desc orders;

drop table if exists item;
create table if not exists item(
                                   item_Id varchar(6) not null ,
                                   Item_Name varchar(30),
                                   item_Price DECIMAL(6,2) not null ,
                                   qty_On_Hand int(6) ,
                                   constraint primary key (item_Id)
);
show tables ;
desc item;

drop table if exists order_Detail;
create table if not exists order_Detail(
                                           order_Id varchar(6),
                                           item_Id varchar(6),
                                           qty int(6),
                                           constraint primary key(order_Id,item_Id),
                                           constraint foreign key (order_Id) references orders(order_Id) on delete cascade on update cascade ,
                                           constraint foreign key (item_Id) references item(item_Id) on delete cascade on update cascade
);
show tables ;
desc order_Detail;

drop table if exists service;
create table if not exists service(
                                      service_Id varchar(6)not null ,
                                      service_Name varchar(30),
                                      service_Price decimal(6,2),
                                      service_Description varchar(50),
                                      constraint primary key (service_Id)
);
show tables ;
desc service;

drop table if exists employee;
create table if not exists employee(
                                       emp_Role varchar(10)not null,
                                       emp_Id varchar(2) not null ,
                                       emp_Name varchar(30) ,
                                       emp_Address varchar(50),
                                       emp_Contact varchar(11),
                                       emp_Salary decimal(8,2),
                                       emp_Password varchar(30),
                                       constraint primary key (emp_Id)
);
show tables ;
desc employee;

drop table if exists appoinment;
create table if not exists appoinment(
                                         app_No varchar(6) not null ,
                                         customer_NIC varchar(20),
                                         employee_Id varchar(2),
                                         price decimal(8,2),
                                         discount decimal(4),
                                         date date,
                                         constraint primary key (app_No),
                                         constraint foreign key (customer_NIC) references customer(cust_NIC) on delete cascade on update cascade ,
                                         constraint foreign key (employee_Id) references employee(emp_Id) on delete cascade on update cascade
);
show tables ;
desc appoinment;

drop table if exists service_Details;
create table if not exists service_Details(
                                              service_Id varchar(6),
                                              app_No varchar(6),
                                              price decimal(8,2),
                                              constraint primary key (service_Id,app_No),
                                              constraint foreign key (service_Id) references service(service_Id) on delete cascade on update cascade ,
                                              constraint foreign key (app_No) references appoinment(app_No) on delete cascade on update cascade
);
show tables ;
desc service_Details;

drop table if exists attendance;
create table if not exists attendance(
                                         attendance_id int not null auto_increment,
                                         employee_Id varchar(2),
                                         status varchar(8) default false,
                                         date date,
                                         constraint primary key(attendance_id),
                                         constraint foreign key(employee_Id) references employee(emp_Id) on delete cascade on update cascade
);
show tables;
desc attendance;
drop table if exists payments;
create table if not exists payments(
                                       paymentId int not null auto_increment,
                                       appNo varchar(6) not null,
                                       payment DOUBLE,
                                       constraint primary key (paymentId),
                                       constraint foreign key (appNo) references appoinment(app_No) on delete cascade on update cascade
);
show tables;
desc payments;

