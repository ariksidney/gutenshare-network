CREATE TABLE T_USER (
  username VARCHAR(64) NOT NULL,
  name     VARCHAR (64) NOT NULL,
  surname  VARCHAR (64) NOT NULL,
  mail     VARCHAR (64) NOT NULL,
  password VARCHAR (64) NOT NULL,
  CONSTRAINT PK_USERNAME PRIMARY KEY (username)
);

CREATE TABLE T_DOCUMENT (
  document_id       VARCHAR(64) NOT NULL,
  title             VARCHAR(64) NOT NULL,
  path_to_file      VARCHAR(200) NOT NULL,
  filetype          VARCHAR(20) NOT NULL,
  CONSTRAINT PK_DOCUMENT PRIMARY KEY (document_id)
);

INSERT INTO T_USER (username, name, surname, mail, password) VALUES('AG', 'Guggenheim', 'Arik', 'ag@gutenshare.network', '123');
INSERT INTO T_USER (username, name, surname, mail, password) VALUES('AR', 'Rueegg', 'Alicia', 'ar@gutenshare.network', '123');
INSERT INTO T_USER (username, name, surname, mail, password) VALUES('AS', 'Spasojevic', 'Aleksander', 'as@gutenshare.network', '123');
INSERT INTO T_USER (username, name, surname, mail, password) VALUES('LM', 'Mueller', 'Louis', 'lm@gutenshare.network', '123');
INSERT INTO T_USER (username, name, surname, mail, password) VALUES('KW', 'Wolfisberg', 'Kaspar', 'kw@gutenshare.network', '123');
