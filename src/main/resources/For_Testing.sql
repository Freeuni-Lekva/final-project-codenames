DROP DATABASE IF EXISTS testingdb;

CREATE DATABASE testingdb;

Use testingdb;

DROP TABLE IF EXISTS player_history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS game_history;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS categories;


CREATE TABLE users(
                      id                      INT                                 NOT NULL AUTO_INCREMENT,
                      user_name               VARCHAR(50)                         NOT NULL UNIQUE,
                      hashed_password         VARCHAR(64)                         NOT NULL,
                      games_won               INT                                 NOT NULL,
                      games_lost              INT                                 NOT NULL,
                      games_played            INT                                 NOT NULL,
                      winning_rate            DECIMAL                             NOT NULL,
                      black_word_selected     INT                                 NOT NULL,
                      registration_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      status                  VARCHAR(50)  NOT NULL,
                      PRIMARY KEY (id)
);

CREATE TABLE game_history(
                             id                      INT                                 NOT NULL AUTO_INCREMENT,
                             winner                  VARCHAR(50)                         NOT NULL,
                             loser                   VARCHAR(50)                         NOT NULL,
                             black_word_selected     BOOLEAN                             NOT NULL,
                             date_played             TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                             PRIMARY KEY (id)
);

CREATE TABLE player_history(
                               game_id     INT         NOT NULL,
                               user_id     INT         NOT NULL,
                               team        VARCHAR(50) NOT NULL,
                               FOREIGN KEY (game_id) REFERENCES game_history(id),
                               FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
                            id                  INT             NOT NULL AUTO_INCREMENT,
                            name                VARCHAR(50)     NOT NULL,
                            number_of_words     INT             NOT NULL,
                            PRIMARY KEY (id),
                            UNIQUE      (name)
);

CREATE TABLE words(
                      word            VARCHAR(50) NOT NULL,
                      category_id     INT         NOT NULL,
                      FOREIGN KEY (category_id) REFERENCES categories(id)
);