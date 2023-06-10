CREATE TABLE profile (
    id bigint not null auto_increment,
    uid VARCHAR(255) NOT NULL,
    displayName VARCHAR(255) NOT NULL,
    about VARCHAR(255) NOT NULL,
    imgPath VARCHAR(255) NOT NULL,
    primary key (id)
) engine=InnoDB;

CREATE TABLE post (
    id bigint not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    created_by varchar(15) not null,
    updated_by varchar(15) not null,
    content varchar(255) not null,
    title varchar(255) not null,
    primary key (id)
) engine=InnoDB;
