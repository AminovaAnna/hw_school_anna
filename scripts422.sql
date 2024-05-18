CREATE TABLE car (
	car_id SERIAL unique PRIMARY KEY,
    car_brand TEXT,
    car_model TEXT,
    price INTEGER
);

CREATE TABLE human (
	id SERIAL unique,
    name text PRIMARY KEY,
    age SMALLINT,
    driver_license boolean,
    car_id SERIAL references car_id
);