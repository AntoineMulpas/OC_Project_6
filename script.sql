create table app_account
(
    id      bigint not null
        primary key,
    sold    double precision,
    user_id bigint
);


create table app_transaction
(
    id              bigint not null
        primary key,
    amount          double precision,
    local_date_time timestamp,
    receiver_id     bigint,
    sender_id       bigint
);


create table bank_account
(
    id             bigint not null
        primary key,
    account_number varchar(255),
    bank_code      integer,
    counter_code   integer,
    iban           varchar(255),
    rib_key        integer,
    swift          varchar(255),
    username       varchar(255)
);

create table bank_transaction
(
    id      bigint not null
        primary key,
    amount  double precision,
    date    timestamp,
    user_id bigint
);


create table fees
(
    id                  bigint not null
        primary key,
    transaction_id      bigint,
    amount              double precision,
    app_transaction_id  bigint,
    bank_transaction_id bigint,
    date                timestamp
);


create table "user"
(
    id                  bigint       not null
        primary key,
    birthday            date,
    first_name          varchar(255) not null,
    last_name           varchar(255) not null,
    user_authentication varchar(255)
);


create table user_authentication
(
    id       bigint       not null
        primary key,
    password varchar(255) not null,
    username varchar(255) not null
        constraint uk_2f7rnxlwhdd4uo32nuljwkgtg
            unique
);

create table user_relations
(
    id        bigint not null
        primary key,
    friend_id bigint,
    user_id   bigint
);



