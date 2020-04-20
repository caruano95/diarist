CREATE TABLE journal_entry (
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    created DATE NOT NULL
);