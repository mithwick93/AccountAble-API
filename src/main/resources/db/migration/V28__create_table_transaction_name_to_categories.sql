create table transaction_name_to_categories
(
    id               int auto_increment primary key,
    transaction_name varchar(255)                        not null,
    category_id      int                                 not null,
    user_id          int                                 not null,
    created          timestamp default CURRENT_TIMESTAMP not null,
    modified         timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint fk_category_id
        foreign key (category_id) references transaction_categories (id),
    constraint fk_user_id
        foreign key (user_id) references users (id),
    constraint unique_transaction_name_category
        unique (transaction_name, category_id)
);