INSERT INTO days (name) VALUES ('monday');
INSERT INTO days (name) VALUES ('tuesday');
INSERT INTO days (name) VALUES ('wednesday');
INSERT INTO days (name) VALUES ('thursday');
INSERT INTO days (name) VALUES ('friday');
INSERT INTO days (name) VALUES ('saturday');
INSERT INTO days (name) VALUES ('sunday');
insert into users (active, password, username) values (true, '1', 'vova');
insert into roles (user_id, roles) values (1, 'USER');
insert into users (active, password, username) values (true, '1', 'admin');
insert into roles (user_id, roles) values (2, 'ADMIN');
INSERT INTO films (title, description, image_path) VALUES ('The Lord of the Rings', 'Cool film', '/images/TLOTR.jpg');
INSERT INTO films (title, description, image_path) VALUES ('Space Odyssey', '2001', '/images/space_odyssey.jpg');
INSERT INTO sessions (start_time, film_id, day_id, seats_counter) VALUES ('9:00', 1, 1, 0);
INSERT INTO sessions (start_time, film_id, day_id, seats_counter) VALUES ('12:00', 2, 1, 0);
INSERT INTO sessions (start_time, film_id, day_id, seats_counter) VALUES ('15:00', 1, 1, 0);
INSERT INTO sessions (start_time, film_id, day_id, seats_counter) VALUES ('18:00', 2, 1, 0);