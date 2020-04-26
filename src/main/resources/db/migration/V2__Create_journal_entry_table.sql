CREATE TABLE journal_entry (
    id SERIAL,
    user_id BIGINT UNSIGNED NOT NULL,
    content TEXT NOT NULL,
    created DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);