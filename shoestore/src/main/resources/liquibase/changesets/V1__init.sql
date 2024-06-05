CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL       PRIMARY KEY,
    name        TEXT            NOT NULL,
    username    TEXT            NOT NULL UNIQUE,
    password    TEXT            NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id     BIGINT          NOT NULL,
    role        TEXT            NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_users_roles_users FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS shoes
(
    id          BIGSERIAL       PRIMARY KEY,
    title       TEXT            NOT NULL,
    description TEXT            NULL,
    price       DECIMAL(15, 2)  NOT NULL,
    sex         TEXT            NOT NULL,
    shoe_model  TEXT            NOT NULL,
    season      TEXT            NOT NULL,
    size        INTEGER         NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_items
(
    id          BIGSERIAL       PRIMARY KEY,
    shoe_id     INTEGER         NOT NULL,
    quantity    INTEGER         NOT NULL,
    CONSTRAINT fk_cart_items_shoes FOREIGN KEY (shoe_id)
        REFERENCES shoes (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS carts
(
    user_id       BIGINT          NOT NULL,
    cart_item_id  BIGINT          NOT NULL,
    PRIMARY KEY (user_id, cart_item_id),
    CONSTRAINT fk_carts_users FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_carts_cart_items FOREIGN KEY (cart_item_id)
        REFERENCES cart_items (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS orders
(
    id            BIGSERIAL       PRIMARY KEY,
    order_time    TIMESTAMP       NOT NULL,
    first_name    TEXT            NOT NULL,
    last_name     TEXT            NOT NULL,
    city          TEXT            NOT NULL,
    address       TEXT            NOT NULL,
    email         TEXT            NOT NULL,
    phone_number  TEXT            NOT NULL,
    user_id       BIGINT          NOT NULL,
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS order_items
(
    id            BIGSERIAL       PRIMARY KEY,
    order_id      BIGINT          NOT NULL,
    arrival_time  TIMESTAMP       NULL,
    shoe_id       BIGINT          NOT NULL,
    quantity      INTEGER         NOT NULL,
    status        TEXT            NOT NULL,
    CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id)
        REFERENCES orders (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE INDEX ON shoes (id);