CREATE TABLE users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   email VARCHAR(255) UNIQUE NOT NULL,
   password VARCHAR(255) NOT NULL,
   nickname VARCHAR(100) UNIQUE NOT NULL,
   role VARCHAR(50) NOT NULL,
   created_at DATETIME,
   updated_at DATETIME,
   deleted_at DATETIME DEFAULT NULL
);

CREATE TABLE stores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    open_time TIME NOT NULL,
    close_time TIME NOT NULL,
    min_order_amount INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    average_rating DOUBLE DEFAULT 0.0,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE menus (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   store_id BIGINT NOT NULL,
   name VARCHAR(255) NOT NULL,
   price DECIMAL(10,2) NOT NULL,
   description VARCHAR(500) NULL,
   status VARCHAR(50) NOT NULL,
   created_at DATETIME,
   updated_at DATETIME,
   deleted_at DATETIME NULL,
   FOREIGN KEY (store_id) REFERENCES stores(id)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    deliver_address VARCHAR(255) NOT NULL,
    store_comment TEXT,
    rider_comment TEXT,
    status VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    canceled_at DATETIME DEFAULT NULL,
    FOREIGN KEY (store_id) REFERENCES stores(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     order_id BIGINT NOT NULL,
     menu_id BIGINT NOT NULL,
     price DECIMAL(10,2) NOT NULL,
     quantity INT NOT NULL,
     FOREIGN KEY (order_id) REFERENCES orders(id),
     FOREIGN KEY (menu_id) REFERENCES menus(id)
);


CREATE TABLE reviews (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     user_id BIGINT NOT NULL,
     order_id BIGINT NOT NULL UNIQUE,
     store_id BIGINT,
     rating INT NOT NULL,
     content TEXT NOT NULL,
     created_at DATETIME,
     updated_at DATETIME,
     canceled_at DATETIME DEFAULT NULL,
     FOREIGN KEY (user_id) REFERENCES users(id),
     FOREIGN KEY (order_id) REFERENCES orders(id),
     FOREIGN KEY (store_id) REFERENCES stores(id)
);

CREATE TABLE owner_comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    review_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME DEFAULT NULL,
    FOREIGN KEY (review_id) REFERENCES reviews(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
