USE codenames;

DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS categories;


CREATE TABLE categories (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    name                VARCHAR(50)     NOT NULL,
    number_of_words     BIGINT          NOT NULL,
    PRIMARY KEY (id),
    UNIQUE      (name)
);

CREATE TABLE words(
    word            VARCHAR(50) NOT NULL,
    category_id     BIGINT      NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);