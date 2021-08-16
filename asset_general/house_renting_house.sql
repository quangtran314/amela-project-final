create table house
(
    house_id     bigint auto_increment
        primary key,
    house_name   varchar(100) null,
    type_id      bigint       not null,
    price        float        not null,
    address      varchar(100) not null,
    numBathrooms int          not null,
    numBedrooms  int          not null,
    des          varchar(255) null,
    constraint FKoyh55b0ttt5x0cd0odrd2kwek
        foreign key (type_id) references house_type (type_id)
)
    charset = utf8mb4;

INSERT INTO house_renting.house (house_id, house_name, type_id, price, address, numBathrooms, numBedrooms, des) VALUES (1, 'house1', 1, 1000, 'Hà Nam', 1, 1, null);
INSERT INTO house_renting.house (house_id, house_name, type_id, price, address, numBathrooms, numBedrooms, des) VALUES (2, 'house2', 2, 1000, 'Hà Đông', 2, 2, null);
INSERT INTO house_renting.house (house_id, house_name, type_id, price, address, numBathrooms, numBedrooms, des) VALUES (3, 'house3', 3, 1000, 'Hà Tây', 3, 3, null);
INSERT INTO house_renting.house (house_id, house_name, type_id, price, address, numBathrooms, numBedrooms, des) VALUES (4, 'house4', 4, 1000, 'Hà Hồ', 4, 4, null);
INSERT INTO house_renting.house (house_id, house_name, type_id, price, address, numBathrooms, numBedrooms, des) VALUES (5, 'house5', 5, 1000, 'Hà Giang', 5, 5, null);