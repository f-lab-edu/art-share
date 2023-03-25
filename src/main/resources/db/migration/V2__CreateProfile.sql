CREATE TABLE profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  uid VARCHAR(255),
  display_name VARCHAR(255),
  about TEXT,
  img_path VARCHAR(255)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;
