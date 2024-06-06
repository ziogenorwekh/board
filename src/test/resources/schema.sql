CREATE TABLE users (
                       user_id VARCHAR(36) NOT NULL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       enabled BOOLEAN NOT NULL DEFAULT FALSE,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       role_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       role_name VARCHAR(255) NOT NULL UNIQUE,
                       user_id VARCHAR(36) NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE posts (
                       post_id VARCHAR(36) NOT NULL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       user_id VARCHAR(36) NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);