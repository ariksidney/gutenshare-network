CREATE TABLE T_SCHOOL (
  school_id VARCHAR(64) NOT NULL,
  name      VARCHAR(64) NOT NULL UNIQUE,
  CONSTRAINT PK_SCHOOL PRIMARY KEY (school_id)
);

CREATE TABLE T_DEPARTMENT (
  department_id VARCHAR(64) NOT NULL,
  name          VARCHAR(64) NOT NULL UNIQUE,
  CONSTRAINT PK_DEPARTMENT PRIMARY KEY (department_id)
);

CREATE TABLE T_COURSE (
  course_id VARCHAR(64) NOT NULL,
  name      VARCHAR(64) NOT NULL,
  CONSTRAINT PK_COURSE PRIMARY KEY (course_id)
);

CREATE TABLE T_USER (
  username VARCHAR(64) NOT NULL,
  name     VARCHAR(64) NOT NULL,
  surname  VARCHAR(64) NOT NULL,
  mail     VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  CONSTRAINT PK_USERNAME PRIMARY KEY (username)
);

CREATE TABLE T_DOCUMENT (
  document_id   VARCHAR(64)  NOT NULL,
  title         VARCHAR(64)  NOT NULL,
  documenttype  VARCHAR(64)  NOT NULL,
  school_id     VARCHAR(64),
  department_id VARCHAR(64),
  course_id     VARCHAR(64),
  description   VARCHAR(500),
  path_to_file  VARCHAR(200) NOT NULL,
  filetype      VARCHAR(20)  NOT NULL,
  upload_date   TIMESTAMP    NOT NULL,
  CONSTRAINT PK_DOCUMENT PRIMARY KEY (document_id),
  CONSTRAINT FK_DOCUMENT_SCHOOL FOREIGN KEY (school_id) REFERENCES T_SCHOOL (school_id),
  CONSTRAINT FK_DOCUMENT_DEPARTMENT FOREIGN KEY (department_id) REFERENCES T_DEPARTMENT (department_id),
  CONSTRAINT FK_DOCUMENT_COURSE FOREIGN KEY (course_id) REFERENCES T_COURSE (course_id)
);

CREATE TABLE T_TAG (
  name VARCHAR(64) NOT NULL,
  CONSTRAINT PK_TAG PRIMARY KEY (name)
);

CREATE TABLE T_TAG_DOCUMENT (
  document_id VARCHAR(64) NOT NULL,
  tag_name    VARCHAR(64) NOT NULL,
  CONSTRAINT PK_TAG_DOCUMENT PRIMARY KEY (document_id, tag_name),
  CONSTRAINT FK_TAG FOREIGN KEY (tag_name) REFERENCES T_TAG (name) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_DOCUMENT FOREIGN KEY (document_id) REFERENCES T_DOCUMENT (document_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE T_COMMENT (
  comment_id   VARCHAR(64)  NOT NULL,
  document_id  VARCHAR(64)  NOT NULL,
  comment      VARCHAR(500) NOT NULL,
  username     VARCHAR(64)  NOT NULL,
  comment_date TIMESTAMP    NOT NULL,
  CONSTRAINT PK_COMMENT PRIMARY KEY (comment_id),
  CONSTRAINT FK_COMMENTS_DOCUMENT FOREIGN KEY (document_id) REFERENCES T_DOCUMENT (document_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_COMMENTS_USER FOREIGN KEY (username) REFERENCES T_USER (username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE T_RATING (
  rating_id   VARCHAR(64) NOT NULL,
  document_id VARCHAR(64) NOT NULL,
  rating      INTEGER     NOT NULL,
  username    VARCHAR(64) NOT NULL,
  rating_date TIMESTAMP   NOT NULL,
  CONSTRAINT PK_RATING PRIMARY KEY (rating_id),
  CONSTRAINT FK_RATING_DOCUMENT FOREIGN KEY (document_id) REFERENCES T_DOCUMENT (document_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_RATING_USER FOREIGN KEY (username) REFERENCES T_USER (username) ON DELETE CASCADE ON UPDATE CASCADE
);
