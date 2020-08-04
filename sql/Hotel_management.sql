USE Hotel_management;

CREATE TABLE Room(
	room_id				int PRIMARY KEY,
    floor				int,
    room_type			varchar(10)
);

CREATE TABLE Customer(
	email_address		varchar(50) PRIMARY KEY,
    full_name			varchar(30),
    phone_num			varchar(15)
);

CREATE TABLE Reservation(
	reservation_id		int PRIMARY KEY auto_increment,
	room_id				int,
    customer_id			varchar(50),
    check_in_date		date,
    check_out_date		date,
    price				decimal,
    person				int,
    FOREIGN KEY(room_id) REFERENCES Room(room_id),
    FOREIGN KEY(customer_id) REFERENCES Customer(email_address)
);