create table contract
(
    id               bigint auto_increment
        primary key,
    dateContractSign date   not null,
    endDay           date   not null,
    maxPerson        int    not null,
    startDay         date   not null,
    totalPrice       float  not null,
    house_id         bigint null,
    tenant_id        bigint null
)
    engine = MyISAM;

create index FK7t9gq6h82u471frhrjgprn3b4
    on contract (tenant_id);

create index FKpxb43re7sw5e9brclue8pxskr
    on contract (house_id);

INSERT INTO house_renting.contract (id, dateContractSign, endDay, maxPerson, startDay, totalPrice, house_id, tenant_id) VALUES (1, '2021-08-20', '2021-08-10', 3, '2021-08-05', 5000, 1, 3);
INSERT INTO house_renting.contract (id, dateContractSign, endDay, maxPerson, startDay, totalPrice, house_id, tenant_id) VALUES (2, '2021-08-20', '2021-10-20', 5, '2021-08-10', 71000, 2, 3);