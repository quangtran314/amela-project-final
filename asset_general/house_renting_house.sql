create table house
(
    house_id   bigint auto_increment
        primary key,
    house_name varchar(100) null,
    type_id    bigint       not null,
    image_id   bigint       not null,
    des        text         not null,
    price      float        not null,
    address    varchar(100) not null
);

