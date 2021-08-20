create table house_type
(
    type_id bigint auto_increment
        primary key,
    name    varchar(20) not null
)
    engine = MyISAM;

INSERT INTO house_renting.house_type (type_id, name) VALUES (1, 'type1');
INSERT INTO house_renting.house_type (type_id, name) VALUES (2, 'type2');
INSERT INTO house_renting.house_type (type_id, name) VALUES (3, 'type3');
INSERT INTO house_renting.house_type (type_id, name) VALUES (4, 'type4');
INSERT INTO house_renting.house_type (type_id, name) VALUES (5, 'type5');