CREATE DATABASE foodjet;

create table item
(
    id        bigint       not null
        primary key,
    name_food varchar(255) null,
    quantity  bigint       null
);

create table order_request
(
    id               bigint       not null
        primary key,
    client_name      varchar(255) null,
    create_date      varchar(255) null,
    last_update_date varchar(255) null,
    status           varchar(255) null,
    value            double       not null
);

create table order_request_item
(
    order_request_id bigint not null,
    items_id        bigint not null,
    constraint fk_item
        foreign key (items_id) references item (id),
    constraint fk_order_request
        foreign key (order_request_id) references order_request (id)
);

create table inventory
(
    id       bigint       not null primary key,
    name     varchar(255) null,
    quantity bigint       null primary key
);

create table burger_inventory
(
    burger_id   bigint not null,
    inventory_id bigint not null,
    quantity     bigint not null default 0 primary key,
    constraint fk_burger
        foreign key (burger_id) references burger (id),
    constraint fk_inventory
        foreign key (inventory_id) references inventory (id)
);

create table burger
(
    id   bigint       not null primary key,
    name varchar(255) null
);

ALTER TABLE inventory
    ADD CONSTRAINT fk_quantity foreign key inventory (quantity) REFERENCES burger_inventory (quantity);