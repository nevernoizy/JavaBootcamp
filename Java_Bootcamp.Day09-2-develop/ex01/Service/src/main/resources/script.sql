CREATE TABLE IF NOT EXISTS User_table (
    id serial PRIMARY KEY,
    email varchar(80),
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

INSERT INTO user_table ( email, password) VALUES
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3'),
('user4', 'password4'),
('user5', 'password5'),
('user6', 'password6'),
('user7', 'password7'),
('user8', 'password8'),
('user9', 'password9'),
('user10', 'password10');

INSERT INTO Chatroom (name, owner_id) VALUES
('Room 1', 1),
('Room 2', 2),
('Room 3', 3);