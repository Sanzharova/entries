CREATE TABLE USERS (
                       ID INTEGER AUTO_INCREMENT PRIMARY KEY,
                       USERNAME VARCHAR(255) NOT NULL,
                       FIRST_NAME VARCHAR(255),
                       LAST_NAME VARCHAR(255),
                       MIDDLE_NAME VARCHAR(255),
                       PASSWORD VARCHAR(255),
                       ROLE_USER VARCHAR(255),
                       CREDENTIALS_EXPIRY_DATE TIMESTAMP,
                       IS_ACCOUNT_NON_EXPIRED BOOLEAN,
                       IS_ACCOUNT_NON_LOCKED BOOLEAN,
                       IS_ACTIVE BOOLEAN NOT NULL,
                       IS_ENABLED BOOLEAN,
                       CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);


CREATE TABLE ENTRIES (
                         ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                         TITLE VARCHAR(255) NOT NULL,
                         SUMMARY VARCHAR(500) NOT NULL,
                         CONTENT VARCHAR(1500),
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         USER_ID BIGINT NOT NULL,
                         CONSTRAINT FK_USER FOREIGN KEY (USER_ID) REFERENCES USERS ON DELETE CASCADE
);

CREATE TABLE IMAGES (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        IMAGE_URL VARCHAR(255) NOT NULL,
                        ENTRY_ID BIGINT NOT NULL,
                        CONSTRAINT FK_ENTRY FOREIGN KEY (ENTRY_ID) REFERENCES ENTRIES ON DELETE CASCADE
);


CREATE TABLE PASSWORD_RESET_TOKENS (
                                       ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       TOKEN VARCHAR(255) NOT NULL UNIQUE,
                                       USER_ID BIGINT REFERENCES USERS,
                                       EXPIRATION_DATE TIMESTAMP NOT NULL
);
