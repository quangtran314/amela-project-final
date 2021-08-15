create table image
(
    image_id   bigint auto_increment
        primary key,
    name       varchar(20)  not null,
    house_id   bigint       null,
    sourcePath varchar(255) null,
    desImage   varchar(255) null,
    constraint FKgfwbx7t4780faid7r1a8nf72l
        foreign key (house_id) references house (house_id)
)
    charset = utf8mb4;

INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (1, 'img1', 1, 'tự thêm', 'mô tả 1');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (2, 'img2', 1, 'tự thêm', 'mô tả 2');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (3, 'img3', 2, 'tự thêm', 'mô tả 3');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (4, 'img4', 3, 'tự thêm', 'mô tả 4');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (5, 'img5', 5, 'tự thêm', 'mô tả 5');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (6, 'img6', 4, 'tự thêm', 'mô tả 6');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (7, 'img7', 5, 'tự thêm', 'mô tả 7');
INSERT INTO house_renting.image (image_id, name, house_id, sourcePath, desImage) VALUES (8, 'img 8', 1, 'tự thêm', 'mô tả 8');