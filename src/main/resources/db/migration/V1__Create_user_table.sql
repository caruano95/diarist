CREATE TABLE user (
    id SERIAL,
    username VARCHAR(50),
    phoneNumber VARCHAR(50),
    passcode VARCHAR(50),
    created DATE NOT NULL,
    PRIMARY KEY (id)
);