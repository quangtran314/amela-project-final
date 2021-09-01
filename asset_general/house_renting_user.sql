create table user
(
    id          bigint auto_increment
        primary key,
    PIN         varchar(255) not null,
    address     varchar(255) not null,
    email       varchar(255) not null,
    fullName    varchar(255) not null,
    password    varchar(255) not null,
    phoneNumber varchar(20)  not null,
    city        varchar(255) null
)
    engine = MyISAM;

INSERT INTO house_renting.user (id, PIN, address, email, fullName, password, phoneNumber, city) VALUES (2, '12345678', 'Bùi Xương Trạch', 'abc@gmail.com', 'quang tran', '$2a$10$uM2c1qvpWt7u2EsE47/nL.VXb4FM5o6A6FNUR9hnXglTt7R5A8IXK', '123456789', null);
INSERT INTO house_renting.user (id, PIN, address, email, fullName, password, phoneNumber, city) VALUES (3, '87654321', 'Cầu Giấy', 'bca@gmail.com', 'quang tran', '$2a$10$NEFBJmTQ73SxSnx0wahbFuLLEuh4BjJfJYr3YwiIiqW/k5kQJf4XK', '123894841', null);