create table tenant
(
    tenant_id    bigint auto_increment
        primary key,
    full_name    varchar(100) not null,
    PIN          varchar(20)  not null,
    address      varchar(100) not null,
    phone_number varchar(10)  not null,
    email        varchar(100) not null
);

