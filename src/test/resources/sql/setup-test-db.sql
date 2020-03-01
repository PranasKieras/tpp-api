drop table if exists PS_USER;

create table PS_USER(
    ID IDENTITY NOT NULL PRIMARY KEY,
    PERSONAL_ID varchar2(100) NOT NULL,
    BANK_LOGIN_ID varchar2(100) NOT NULL,
    PHONE_NUMBER varchar2(100) NOT NULL,
    TOKEN varchar2(100) NOT NULL
);
