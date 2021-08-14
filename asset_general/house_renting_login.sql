create table login
(
    id int auto_increment primary key,
    username varchar(50) charset utf8 null,
    password varchar(50) charset utf8 null
);

INSERT INTO house_renting.login (id, username, password) VALUES (1, 'admin', 'admin');
