create table USERS
(
    ID                      INTEGER auto_increment
        primary key,
    USERNAME                CHARACTER VARYING(255)              not null,
    FIRST_NAME              CHARACTER VARYING(255),
    LAST_NAME               CHARACTER VARYING(255),
    MIDDLE_NAME             CHARACTER VARYING(255),
    PASSWORD                CHARACTER VARYING(255),
    ROLE_USER               CHARACTER VARYING(255),
    CREDENTIALS_EXPIRY_DATE TIMESTAMP,
    IS_ACCOUNT_NON_EXPIRED  BOOLEAN,
    IS_ACCOUNT_NON_LOCKED   BOOLEAN,
    IS_ACTIVE               BOOLEAN                             not null,
    IS_ENABLED              BOOLEAN,
    CREATED_AT              TIMESTAMP default CURRENT_TIMESTAMP not null
);

create table ENTRIES
(
    ID         BIGINT auto_increment
        primary key,
    TITLE      CHARACTER VARYING(255)              not null,
    SUMMARY    CHARACTER VARYING(500)              not null,
    CONTENT    CHARACTER VARYING(1500),
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP not null,
    USER_ID    BIGINT                              not null,
    constraint FK_USER
        foreign key (USER_ID) references USERS
            on delete cascade
);

create table IMAGES
(
    ID        BIGINT auto_increment
        primary key,
    IMAGE_URL CHARACTER VARYING(255) not null,
    ENTRY_ID  BIGINT                 not null,
    constraint FK_ENTRY
        foreign key (ENTRY_ID) references ENTRIES
            on delete cascade
);

create table PASSWORD_RESET_TOKENS
(
    ID              BIGINT auto_increment
        primary key,
    TOKEN           CHARACTER VARYING(255) not null
        unique,
    USER_ID         BIGINT
        references USERS,
    EXPIRATION_DATE TIMESTAMP              not null
);
