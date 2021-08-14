create table image
(
    image_id bigint auto_increment
        primary key,
    name     varchar(20) not null,
    source   text        not null
);

