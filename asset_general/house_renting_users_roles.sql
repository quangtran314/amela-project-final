create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
)
    engine = MyISAM;

create index FKj6m8fwv7oqv74fcehir1a9ffy
    on users_roles (role_id);

INSERT INTO house_renting.users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO house_renting.users_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO house_renting.users_roles (user_id, role_id) VALUES (3, 2);