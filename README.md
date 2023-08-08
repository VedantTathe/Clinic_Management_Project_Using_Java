# Clinic_Management_Project_Using_Java
This Software contains mainly two sections Menu Section and Apointment Section.
Menu Sectioncontains one sub section called history section which includes the history of the selected patient.
Menu Section has following features:- insert, delete, update and history of patient 
Appointment Section contains the table which manages the order of patients appointment.
Resources Required:-
1. JDK must be installed
2. MySQL
3. Mysql connector for jdbc
4. create following database in your mysql
Database:-

use MyClinic;
create database MyClinic;
create table patients
(
    id int auto_increment,
    date varchar(20) not null,
    pname varchar(50) not null,
    pmno varchar(10) unique not null,
    preg varchar(15) unique not null,
    primary key (id)
);

drop table patients;

select * from patients;
