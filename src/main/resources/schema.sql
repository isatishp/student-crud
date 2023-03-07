CREATE TABLE IF NOT EXISTS STUDENT (
    id serial PRIMARY KEY,
    name VARCHAR(255),
    created_by VARCHAR(50),
    created_at TIMESTAMP,
    modified_by VARCHAR(50),
    modified_at TIMESTAMP,
    version INTEGER
);