create table house_type
(
    type_id bigint auto_increment
        primary key,
    name    varchar(20) not null
)
    engine = MyISAM;

INSERT INTO house_renting.house_type (type_id, name) VALUES (2, 'Hồ bơi');
INSERT INTO house_renting.house_type (type_id, name) VALUES (1, 'Nhà trên cây');
INSERT INTO house_renting.house_type (type_id, name) VALUES (3, 'Nhà nhỏ');
INSERT INTO house_renting.house_type (type_id, name) VALUES (4, 'Nhà hướng biển');
INSERT INTO house_renting.house_type (type_id, name) VALUES (5, 'Nhà khung chữ A');