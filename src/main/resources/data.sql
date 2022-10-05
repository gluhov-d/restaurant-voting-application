INSERT INTO USERS (first_name, last_name, email, password)
VALUES ('Dmitrii', 'Petrov', 'user@yandex.ru', '{noop}password'),
       ('Anton', 'Bashirov', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Mirazur'),
       ('Noma');

INSERT INTO DISH (local_date, name, price, restaurant_id)
VALUES ('2022-08-25', 'Goose', 25000, 1),
       ('2022-08-25', 'Beef', 20000, 1),
       ('2022-08-25', 'Frankfurters', 15000, 1),
       ('2022-08-25', 'Tuna', 35000, 2),
       ('2022-08-25', 'Lobster', 40000, 2),
       ('2022-08-25', 'Carp', 43000, 2),
       ('2022-08-26', 'Tuna', 35000, 1),
       ('2022-08-26', 'Lobster', 40000, 1),
       ('2022-08-26', 'Carp', 43000, 1),
       ('2022-08-26', 'Goose', 25000, 2),
       ('2022-08-26', 'Beef', 20000, 2),
       ('2022-08-26', 'Frankfurters', 15000, 2);

INSERT INTO USER_VOTE (restaurant_id, user_id, date_time, local_date)
VALUES (1, 1, '2022-08-25T10:59:59.735+00:00', '2022-08-25'),
       (2, 2, '2022-08-25T10:00:00.735+00:00', '2022-08-25'),
       (1, 1, '2022-08-24T09:53:43.735+00:00', '2022-08-24');