-- auto-generated definition
create table agent
(
    id_agent         serial primary key not null,
    fio_agent        varchar(15),
    gender_agent     varchar(1),
    callnumber_agent varchar(12),
    login            varchar(20),
    password         varchar(20)
);


create table auto
(
    id_авто     serial primary key not null,
    marka       varchar(10),
    model       varchar(10),
    color       varchar(10),
    power       varchar(10),
    volume      varchar(10),
    number_auto varchar(10),
    speed       varchar(10),
    drive       varchar(10),
    body        varchar(10)
);



create table client(
                       id_client serial primary key not null ,
                       fio_client varchar(15),
                       birth_date varchar(10),
                       number_prav varchar(15),
                       auto_skill varchar(15),
                       gender varchar(15),
                       address varchar(30),
                       phone varchar(15),
                       city varchar(20)
);
create table dogovor
(
    id_договора       serial primary key not null ,
    dateofissue       varchar(15),
    breakupdate       varchar(10),
    id_agent          integer references main_office.public.agent(id_agent),
    id_client         integer references main_office.public.client(id_client),
    insurance_type    varchar(15),
    insurance_payment varchar(30),
    id_auto           integer references main_office.public.auto(id_авто),
    pay               varchar(15)
);
