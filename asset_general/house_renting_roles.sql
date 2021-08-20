create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
)
    engine = MyISAM;

INSERT INTO house_renting.roles (id, name) VALUES (1, 'ROLE_TENANT');
INSERT INTO house_renting.roles (id, name) VALUES (2, 'ROLE_OWNER');