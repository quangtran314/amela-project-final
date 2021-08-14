create table contact
(
    contact_id bigint auto_increment
        primary key,
    tenant_id  bigint not null,
    house_id   bigint not null,
    start_day  date   not null,
    end_day    date   not null,
    max_person int    not null,
    term       text   not null
);

