CREATE TABLE comment (
    id bigint not null auto_increment,
    content varchar(255) not null,
    created_by varchar(15) not null,
    updated_by varchar(15) not null,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    parent_id bigint,
    post_id bigint,
    primary key (id)
) engine=InnoDB;
