create table house
(
    house_id bigint auto_increment
        primary key,
    type_id  bigint       not null,
    address  varchar(100) not null,
    image_id bigint       not null,
    des      text         not null,
    price    float        not null
);

