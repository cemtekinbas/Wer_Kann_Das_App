DROP DATABASE IF EXISTS mso_wkd;
CREATE DATABASE mso_wkd;

CREATE TABLE user (
  pk                 INT(11)      NOT NULL AUTO_INCREMENT,
  mail               VARCHAR(250) NOT NULL,
  user_name          VARCHAR(50)  NOT NULL,
  plz                VARCHAR(15)  NOT NULL,
  password           VARCHAR(50)  NOT NULL,
  forename           VARCHAR(50),
  surename           VARCHAR(50),
  village            VARCHAR(250),
  street_with_number VARCHAR(250),
  birthdate          DATE,
  PRIMARY KEY (pk)
);

-- count created, fullfilledOwn and fullfilledOther Request or count with DATABASE

-- add settings and achievments as column (fewer joins but less dynamic) or seperate tables?

CREATE TABLE achievment (
  pk               INT(11) NOT NULL AUTO_INCREMENT,
  name             VARCHAR(250),
  description VARCHAR(250),
  icon_path VARCHAR(250),
  unlock_condition VARCHAR(500),
  PRIMARY KEY (pk)
);

CREATE TABLE user_has_achievment (
  user_fk       INT(11),
  achievment_fk INT(11),
  PRIMARY KEY (user_fk, achievment_fk),
  CONSTRAINT user_has_achievment_user_fk FOREIGN KEY (user_fk) REFERENCES user (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT user_has_achievment_achievment_fk FOREIGN KEY (achievment_fk) REFERENCES achievment (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE setting (
  pk   INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(250),
  PRIMARY KEY (pk)
);

CREATE TABLE user_has_setting (
  user_fk       INT(11),
  setting_fk    INT(11),
  setting_value VARCHAR(250),
  PRIMARY KEY (user_fk, setting_fk),
  CONSTRAINT user_has_setting_user_fk FOREIGN KEY (user_fk) REFERENCES user (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT user_has_setting_setting_fk FOREIGN KEY (setting_fk) REFERENCES setting (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
-- end settings and achievments

CREATE TABLE chat (
  pk           INT(11)      NOT NULL AUTO_INCREMENT,
  message      VARCHAR(250) NOT NULL, -- limit size!?
  from_user_fk INT(11),
  to_user_fk   INT(11),
  sent_date    DATETIME     NOT NULL DEFAULT now(), -- validate now(), remove if invalid
  read_date    DATETIME              DEFAULT NULL, -- optional column
  PRIMARY KEY (pk),
  CONSTRAINT chat_from_user_fk FOREIGN KEY (from_user_fk) REFERENCES user (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT chat_to_user_fk FOREIGN KEY (to_user_fk) REFERENCES user (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE request_state (
  pk          INT(11)     NOT NULL AUTO_INCREMENT,
  description VARCHAR(50) NOT NULL,
  PRIMARY KEY (pk)
);

INSERT INTO request_state (pk, description) VALUES
  (1, "OPEN"),
  (2, "CLOSED"),
  (3, "FULLFILLED"),
  (4, "TRASH");

CREATE TABLE request (
  pk           INT(11)      NOT NULL AUTO_INCREMENT,
  from_user_fk INT(11),
  title        VARCHAR(250) NOT NULL, -- limit size!?
  message      VARCHAR(250) NOT NULL, -- limit size!?
  is_premium   BIT(1), -- validate datatype bit, replace with TINYINT(1) if invalid
  create_date  DATETIME     NOT NULL DEFAULT now(), -- validate now(), remove if invalid
  state_fk     INT(11)      NOT NULL DEFAULT 1,
  PRIMARY KEY (pk),
  CONSTRAINT request_from_user_fk FOREIGN KEY (from_user_fk) REFERENCES user (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT request_state_fk FOREIGN KEY (state_fk) REFERENCES request_state (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE request_response (
  request_fk    INT(11)  NOT NULL,
  user_fk       INT(11)  NOT NULL,
  response_date DATETIME NOT NULL DEFAULT now(), -- validate now(), remove if invalid
  PRIMARY KEY (request_fk, user_fk),
  CONSTRAINT request_response_user_fk FOREIGN KEY (user_fk) REFERENCES user (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT request_response_request_fk FOREIGN KEY (request_fk) REFERENCES request (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE request_tag (
  pk  INT(11)     NOT NULL AUTO_INCREMENT,
  tag VARCHAR(50) NOT NULL,
  PRIMARY KEY (pk)
);

CREATE TABLE request_has_tag (
  request_fk INT(11) NOT NULL,
  tag_fk     INT(11) NOT NULL,
  PRIMARY KEY (request_fk, tag_fk),
  CONSTRAINT request_has_tag_request_fk FOREIGN KEY (request_fk) REFERENCES request (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT request_has_tag_tag_fk FOREIGN KEY (tag_fk) REFERENCES request_tag (pk)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
