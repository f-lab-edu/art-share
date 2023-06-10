--create table PERSON (
--    ID int not null,
--    NAME varchar(100) not null
--);

CREATE TABLE Profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uid VARCHAR(255) NOT NULL,
    displayName VARCHAR(255) NOT NULL,
    about VARCHAR(255) NOT NULL,
    imgPath VARCHAR(255) NOT NULL
);
