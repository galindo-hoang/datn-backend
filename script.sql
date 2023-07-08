create table account
(
    id           bigserial
        primary key,
    version      bigint,
    birthday     timestamp(6),
    created_on   timestamp(6),
    image_path   varchar(255),
    password     varchar(255) not null,
    phone_number varchar(255),
    user_name    varchar(255) not null
        constraint uk_f6xpj7h12wr185bqhfi1hqlbr
            unique,
    user_role    smallint
);

alter table account
    owner to postgres;

create table category
(
    id      bigserial
        primary key,
    version bigint,
    image   varchar(255),
    name    varchar(255)
        constraint uk_46ccwnsi9409t36lurvtyljak
            unique
);

alter table category
    owner to postgres;

create table drug
(
    id                bigserial
        primary key,
    version           bigint,
    contraindications varchar(255),
    dosage_form       varchar(255),
    drug_name         varchar(255),
    drug_storage      varchar(255),
    image_path        varchar(255),
    indications       varchar(255),
    interactions      varchar(255),
    label             varchar(255),
    last_modify       timestamp(6),
    price             bigint,
    register_number   varchar(255),
    remarks           varchar(255),
    side_effects      varchar(255),
    usage_and_dosage  varchar(255),
    category_id       bigint
        constraint fk8m2axwo2rgn6wyym576ag8n53
            references category
);

alter table drug
    owner to postgres;

create table category_drug
(
    version     bigint,
    drug_id     bigint not null
        constraint fks33n5b5egjuwit6ivd2ftkvnf
            references drug,
    category_id bigint not null
        constraint fk2n9df7uvu7gujocr061ia6x3x
            references category,
    primary key (category_id, drug_id)
);

alter table category_drug
    owner to postgres;

create table ingredient
(
    id      bigserial
        primary key,
    version bigint,
    name    varchar(255)
);

alter table ingredient
    owner to postgres;

create table ingredient_drug
(
    version       bigint,
    number_unit   double precision,
    unit          varchar(255),
    drug_id       bigint not null
        constraint fk4tdmm7l9hdhtas2iy0capc49d
            references drug,
    ingredient_id bigint not null
        constraint fkavoyijkqgu5cdlm7ihhxdgrji
            references ingredient,
    primary key (drug_id, ingredient_id)
);

alter table ingredient_drug
    owner to postgres;

create table prescription
(
    id         bigserial
        primary key,
    version    bigint,
    created_on timestamp(6),
    image_path varchar(255),
    note       varchar(255),
    user_id    bigint
        constraint fk3fov0rp7pbuppn00s6puurt7f
            references account
);

alter table prescription
    owner to postgres;

create table reminder
(
    id            bigserial
        primary key,
    version       bigint,
    active        boolean,
    before_minus  double precision,
    interval      double precision,
    message       varchar(255),
    time_duration bigint,
    user_id       bigint
        constraint fkbnt112ggfo0u83adg1pl4ycqw
            references account
);

alter table reminder
    owner to postgres;

create table prescription_drug
(
    version         bigint,
    note            varchar(255),
    quantity        bigint,
    drug_id         bigint not null
        constraint fkghtjifovo0g19b9wc5w4ngt3i
            references drug,
    prescription_id bigint not null
        constraint fkom0pg7l17e3uqov7dog7owsm6
            references prescription,
    reminder_id     bigint
        constraint fkaapq97frud2obtme4pmbdji78
            references reminder,
    primary key (drug_id, prescription_id)
);

alter table prescription_drug
    owner to postgres;

create table role_entity
(
    id        bigserial
        primary key,
    role_name varchar(255)
);

alter table role_entity
    owner to postgres;

create table top_search_drug
(
    id          bigserial
        primary key,
    version     bigint,
    count       bigint,
    last_search timestamp(6),
    drug_id     bigint
        constraint fkk7uxs8cxhwhkeqi9ggyfxp8d5
            references drug
);

alter table top_search_drug
    owner to postgres;


