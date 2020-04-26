CREATE TABLE user (
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    username VARCHAR(50),
    phoneNumber VARCHAR(50),
    passcode VARCHAR(50),
    created DATE NOT NULL
);