create table users
(
    id       bigint auto_increment primary key,
    username varchar(255) null
);

create table lecture
(
    current_capacity            int default 0 not null,
    max_capacity                int           not null,
    id                          bigint auto_increment primary key,
    lecture_date_time           datetime(6)   not null,
    registration_end_datetime   datetime      not null,
    registration_start_datetime datetime      not null,
    speaker_name                varchar(255)  not null,
    title                       varchar(255)  not null
);

create table lecture_registration
(
    id            bigint auto_increment primary key,
    lecture_id    bigint    not null,
    registered_at timestamp null,
    user_id       bigint    not null,

    constraint user_lecture_uk unique (user_id, lecture_id),
    constraint FK2cmbp0cx2uxu39s45qninb9mj foreign key (lecture_id) references lecture (id),
    constraint FKmx8u4b4d7ugjgmh8p1croclbu foreign key (user_id) references users (id)
);