CREATE TABLE specialty
(
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(128) UNIQUE NOT NULL,
    status VARCHAR(10)         NOT NULL
);

CREATE TABLE skill
(
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(128) UNIQUE NOT NULL,
    status VARCHAR(10)         NOT NULL
);

CREATE TABLE developer
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(128) NOT NULL,
    last_name    VARCHAR(128) NOT NULL,
    specialty_id BIGINT,
    status       VARCHAR(10)  NOT NULL,
    UNIQUE (first_name, last_name)
);

CREATE TABLE developer_skill
(
    developer_id BIGINT,
    skill_id     BIGINT

);

ALTER TABLE developer
    ADD FOREIGN KEY (specialty_id) REFERENCES specialty (id);

ALTER TABLE developer_skill
    ADD FOREIGN KEY (developer_id) REFERENCES developer (id) ON DELETE CASCADE ;

ALTER TABLE developer_skill
    ADD FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE ;
