USE codenames;

DROP TABLE IF EXISTS player_history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS game_history;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS categories;


CREATE TABLE users(
    id                      BIGINT       NOT NULL AUTO_INCREMENT,
    user_name               VARCHAR(50)  NOT NULL UNIQUE,
    hashed_password         VARCHAR(64)  NOT NULL,
    games_won               BIGINT       NOT NULL,
    games_lost              BIGINT       NOT NULL,
    games_played            BIGINT       NOT NULL,
    winning_rate            DECIMAL      NOT NULL,
    black_word_selected     BIGINT       NOT NULL,
    registration_date       DATE         NOT NULL,
    status                  VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id)

);

CREATE TABLE game_history(
    id                      BIGINT       NOT NULL AUTO_INCREMENT,
    winner                  VARCHAR(50)  NOT NULL,
    loser                   VARCHAR(50)  NOT NULL,
    black_word_selected     BIT          NOT NULL,
    date_played             DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE player_history(
    game_id     BIGINT      NOT NULL,
    user_id     BIGINT      NOT NULL,
    team        VARCHAR(50) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game_history(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

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