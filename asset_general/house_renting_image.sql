create table image
(
    image_id   bigint auto_increment
        primary key,
    des        varchar(500) null,
    name       varchar(20)  not null,
    sourcePath varchar(255) not null,
    house_id   bigint       null
)
    engine = MyISAM;

create index FKgfwbx7t4780faid7r1a8nf72l
    on image (house_id);

INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (1, 'mô tả 1', 'img1', 'room-1.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (2, 'mô tả 2', 'img2', 'room-1.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (3, 'mô tả 3', 'img3', 'room-2.jpg', 2);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (4, 'mô tả 4', 'img4', 'room-3.jpg', 3);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (5, 'mô tả 5', 'img5', 'room-4.jpg', 5);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (6, 'mô tả 6', 'img6', 'room-5.jpg', 4);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (7, 'mô tả 7', 'img7', 'room-6.jpg', 5);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (10, 'lorem ipsum', 'avatar', '5ac1538309a5ed2d4fc23de4a4909c89.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (9, 'lorem ipsum', 'hello', '60541461_296828641200785_600533399752409088_n.jpg', 1);