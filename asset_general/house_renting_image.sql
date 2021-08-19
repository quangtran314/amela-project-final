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

INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (29, 'Thủ đô của Trung Quốc', 'Bắc Kinh', 'room-2.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (30, 'dfsdf', 'sdfsdf', 'room-1.jpg', 2);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (31, 'Thủ đô của Trung Quốc', 'Bắc Kinh', 'room-1.jpg', 3);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (32, 'Thủ đô của Trung Quốc', 'Bắc Kinh', 'room-3.jpg', 4);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (33, 'Thủ đô của Trung Quốc', 'tedre', 'room-3.jpg', 5);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (34, 'Thủ đô của Trung Quốc', 'Bắc Kinh', '195530229_275541087700652_3280725022280916041_n.jpg', 5);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (35, 'dfddddfsd', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (36, 'Thủ đô của Trung Quốc', 'Bắc Kinh', '195530229_275541087700652_3280725022280916041_n.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (37, 'dfddddfsd', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (38, 'dfddddfsd', 'Bắc Kinh', '195530229_275541087700652_3280725022280916041_n.jpg', 1);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (39, 'dfddddfsd', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 2);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (40, 'dfddddfsd', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 3);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (41, 'Thủ đô của Trung Quốc', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 5);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (42, 'dfddddfsd', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 2);
INSERT INTO house_renting.image (image_id, des, name, sourcePath, house_id) VALUES (43, 'dfddddfsd', 'nguyen amnh toan', '195530229_275541087700652_3280725022280916041_n.jpg', 3);