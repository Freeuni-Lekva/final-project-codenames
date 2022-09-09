USE codenames;

DROP TABLE IF EXISTS player_history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS game_history;
DROP TABLE IF EXISTS words;


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
    status                  VARCHAR(50)                         NOT NULL,
    points                  INT                                 NOT NULL,
    PRIMARY KEY (id)

);

INSERT INTO users (user_name, hashed_password, games_won, games_lost, games_played, winning_rate, black_word_selected, registration_date, status, points)
VALUES ('Admin', '7af2d10b73ab7cd8f603937f7697cb5fe432c7ff', 0, 0, 0, 0.0, 0, sysdate(), 'ADMIN', 0);
# Admin password is the following: Admin123

CREATE TABLE game_history(
    id                      INT       NOT NULL AUTO_INCREMENT,
    winner                  VARCHAR(50)      NOT NULL,
    loser                   VARCHAR(50)     NOT NULL,
    black_word_selected     BIT          NOT NULL,
    date_played             TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE player_history(
    game_id     INT      NOT NULL,
    user_id     INT      NOT NULL,
    team        VARCHAR(50) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game_history(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE words(
    word            VARCHAR(50) NOT NULL,
    category        VARCHAR(50) NOT NULL,
    PRIMARY KEY     (word, category)
);