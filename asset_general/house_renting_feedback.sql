create table feedback
(
    feedback_id bigint auto_increment
        primary key,
    amt_date    date         null,
    comment     varchar(255) not null,
    rate        int          not null,
    house_id    bigint       null,
    owner_id    bigint       null
)
    engine = MyISAM;

create index FKllpo45jfcfbcnbe9mn6297ewv
    on feedback (owner_id);

create index FKpotbswer62qhupc7h1595jdsa
    on feedback (house_id);

INSERT INTO house_renting.feedback (feedback_id, amt_date, comment, rate, house_id, owner_id) VALUES (1, '2021-08-09', 'Phòng đẹp', 5, 1, 2);
INSERT INTO house_renting.feedback (feedback_id, amt_date, comment, rate, house_id, owner_id) VALUES (2, '2021-08-05', 'Cũng được', 4, 1, 2);
INSERT INTO house_renting.feedback (feedback_id, amt_date, comment, rate, house_id, owner_id) VALUES (3, '2021-08-02', 'Rất đẹp', 5, 2, 3);
INSERT INTO house_renting.feedback (feedback_id, amt_date, comment, rate, house_id, owner_id) VALUES (4, '2021-08-06', 'Sẽ ủng hộ', 5, 3, 3);