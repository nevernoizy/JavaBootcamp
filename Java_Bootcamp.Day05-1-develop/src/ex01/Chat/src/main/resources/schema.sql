CREATE TABLE IF NOT EXISTS User_table (
    id serial PRIMARY KEY,
    login varchar(80),
    password varchar(80)
);

CREATE TABLE IF NOT EXISTS Chatroom (
    id serial PRIMARY KEY,
    name varchar(80),
    owner_id int REFERENCES User_table(id)
);

CREATE TABLE IF NOT EXISTS Message (
    id serial PRIMARY KEY,
    author_id int REFERENCES User_table(id),
    room_id int REFERENCES Chatroom(id),
    text text,
    date_time TIMESTAMP
);
