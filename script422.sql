CREATE TABLE person
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  age INTEGER,
  got_license BOOLEAN,
  car_id INTEGER REFERENCES car (id)
)

CREATE TABLE car
(
  id REAL,
  brand VARCHAR(50),
  model VARCHAR(50),
  price DECIMAL
)