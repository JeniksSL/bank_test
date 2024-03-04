begin;
CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    login      VARCHAR(255)                            NOT NULL,
    password   VARCHAR(255)                            NOT NULL,
    is_enabled BOOLEAN                                 NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
CREATE TABLE accounts
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name            VARCHAR(255),
    birth_date      date,
    initial_deposit DECIMAL(15,5)                                 NOT NULL,
    balance         DECIMAL(15,5)                                    NOT NULL,
    user_id         BIGINT                                  NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE contacts
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    contact      VARCHAR(255)                            NOT NULL,
    contact_type INTEGER                                 NOT NULL,
    account_id   BIGINT                                  NOT NULL,
    CONSTRAINT pk_contacts PRIMARY KEY (id)
);

ALTER TABLE contacts
    ADD CONSTRAINT contact_type_contact_unique UNIQUE (contact_type, contact);

ALTER TABLE contacts
    ADD CONSTRAINT FK_CONTACTS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_login UNIQUE (login);

commit;