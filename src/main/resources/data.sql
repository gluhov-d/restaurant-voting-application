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
VALUES (CURRENT_DATE - 1, 'Goose', 25000, 1),
       (CURRENT_DATE - 1, 'Beef', 20000, 1),
       (CURRENT_DATE - 1, 'Frankfurters', 15000, 1),
       (CURRENT_DATE - 1, 'Tuna', 35000, 2),
       (CURRENT_DATE - 1, 'Lobster', 40000, 2),
       (CURRENT_DATE - 1, 'Carp', 43000, 2),
       (CURRENT_DATE, 'Tuna', 35000, 1),
       (CURRENT_DATE, 'Lobster', 40000, 1),
       (CURRENT_DATE, 'Carp', 43000, 1),
       (CURRENT_DATE, 'Goose', 25000, 2),
       (CURRENT_DATE, 'Beef', 20000, 2),
       (CURRENT_DATE, 'Frankfurters', 15000, 2);

INSERT INTO USER_VOTE (restaurant_id, user_id, date_time, local_date)
VALUES (1, 1, CURRENT_DATE - 1 + TIME '10:00:00', CURRENT_DATE - 1),
       (2, 2, CURRENT_DATE - 1 + TIME '9:53:43', CURRENT_DATE - 1),
       (1, 1, CURRENT_DATE + TIME '13:01:59', CURRENT_DATE);