create table house
(
    house_id     bigint auto_increment
        primary key,
    address      varchar(100) not null,
    des          varchar(500) null,
    house_name   varchar(20)  not null,
    numBathrooms int          not null,
    numBedrooms  int          not null,
    price        float        not null,
    type_id      bigint       null,
    sourcePath   varchar(255) not null
)
    engine = MyISAM;

create index FKoyh55b0ttt5x0cd0odrd2kwek
    on house (type_id);

INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (1, 'Hà Nam', 'Mô tả 1', 'house1', 1, 1, 1000, 1, 'room-1.jpg', null);
INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (2, 'Hà Đông', 'Mô tả 2', 'house2', 2, 2, 1000, 2, 'room-2.jpg', null);
INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (3, 'Hà Tây', 'Mô tả 3', 'house3', 3, 3, 1000, 3, 'room-3.jpg', null);
INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (4, 'Hà Hồ', 'Mô tả 4', 'house4', 4, 4, 1000, 4, 'room-4.jpg', null);
INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (5, 'Hà Giang', 'Mô tả 5
', 'house5', 5, 5, 1000, 5, 'room-5.jpg', null);
INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (6, 'số 8 Nguyễn Trãi', 'Đơn giản, tiện dụng', 'Baby house', 1, 2, 200, 1, 'room-6.jpg', null);
INSERT INTO house_renting.house (house_id, address, des, house_name, numBathrooms, numBedrooms, price, type_id, sourcePath, owner_id) VALUES (9, 'Bùi Xương Trạch', 'Lorem ipsum ', 'Love house', 3, 4, 500, 3, '938adc1a8cb5151bbedd1a3c4cdd92cf.jpg', 3);