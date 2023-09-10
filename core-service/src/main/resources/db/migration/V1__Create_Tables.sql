CREATE TABLE guest
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(20) NOT NULL,
    last_name    VARCHAR(20) NOT NULL,
    email        VARCHAR(50) NOT NULL UNIQUE ,
    phone_number VARCHAR(20)  NOT NULL
);

CREATE TABLE room
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    room_number INT NOT NULL UNIQUE CHECK ( room_number > 0 ),
    capacity    INT NOT NULL CHECK ( capacity >= 1 AND capacity < 6 )
);

CREATE TABLE reservation
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    check_in_date  DATE NOT NULL,
    check_out_date DATE,
    room_id        INT,
    guest_id       INT,
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (guest_id) REFERENCES guest (id)
);
