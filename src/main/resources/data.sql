INSERT INTO USERS (first_name, last_name, email, password)
VALUES ('Dmitrii', 'Petrov', 'user@yandex.ru', 'password'),
       ('Anton', 'Bashirov', 'admin@gmail.com', 'admin');

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
       ('2022-08-25', 'Carp', 43000, 2);

INSERT INTO VOTING (restaurant_id, user_id)
VALUES (1, 1),
       (2, 2);