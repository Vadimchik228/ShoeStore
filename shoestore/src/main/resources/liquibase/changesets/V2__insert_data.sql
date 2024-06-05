INSERT INTO users (name, username, password)
VALUES ('John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

INSERT INTO users_roles (user_id, role)
VALUES (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');

INSERT INTO shoes (title, description, price, sex, shoe_model, season, size)
VALUES ('Sneakers 40 for 1000 rub', NULL, 1000, 'MALE', 'SNEAKERS', 'DEMISEASON', 40),
       ('Sneakers 42 for 2500 rub', NULL, 2500, 'MALE', 'SNEAKERS', 'DEMISEASON', 42),
       ('Sandals 39 for 3500 rub', NULL, 3500, 'FEMALE', 'SANDALS', 'SUMMER', 39);

INSERT INTO cart_items (shoe_id, quantity)
VALUES (1, 2),
       (2, 1),
       (3, 3),
       (1, 1);

INSERT INTO carts (user_id, cart_item_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4);

INSERT INTO orders (order_time, first_name, last_name, city, address, email, phone_number, user_id)
VALUES ('2024-6-01 12:30', 'John', 'Doe', 'Boston', 'Some street 25', 'johndoe@gmail.com', '89507742275', 1);

INSERT INTO order_items (order_id, arrival_time, shoe_id, quantity, status)
VALUES (1, NULL, 1, 2, 'IN_ASSEMBLY'),
       (1, NULL, 2, 1, 'IN_ASSEMBLY'),
       (1, NULL, 3, 3, 'IN_ASSEMBLY');