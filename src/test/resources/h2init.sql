-- Drop tables if they exist (order matters due to foreign key constraints)
DROP TABLE IF EXISTS WISH;
DROP TABLE IF EXISTS WISHLIST;
DROP TABLE IF EXISTS USERS;

-- Create the USERS table (quoted because "USER" is a reserved keyword in H2)
CREATE TABLE USERS
(
    USER_ID       INT PRIMARY KEY AUTO_INCREMENT,
    USER_USERNAME VARCHAR(50) UNIQUE NOT NULL,
    USER_PASSWORD VARCHAR(60)        NOT NULL,
    USER_NAME     VARCHAR(50)
);

-- Create the WISHLIST table without DEFAULT UUID (H2 doesn't support it)
CREATE TABLE WISHLIST
(
    WISHLIST_ID      UUID PRIMARY KEY,
    WISHLIST_USER_ID INT         NOT NULL,
    WISHLIST_NAME    VARCHAR(55) NOT NULL,
    FOREIGN KEY (WISHLIST_USER_ID) REFERENCES USERS (USER_ID)
);

-- Create the WISH table
CREATE TABLE WISH
(
    WISH_ID          UUID PRIMARY KEY,
    WISH_WISHLIST_ID UUID         NOT NULL,
    WISH_NAME        VARCHAR(55)  NOT NULL,
    WISH_DESC        VARCHAR(255) NOT NULL,
    WISH_IMGURL      VARCHAR(2000),
    WISH_PRICE       DOUBLE       NOT NULL,
    WISH_URL         VARCHAR(2000),
    WISH_RESERVED    BOOLEAN      NOT NULL DEFAULT FALSE,
    FOREIGN KEY (WISH_WISHLIST_ID) REFERENCES WISHLIST (WISHLIST_ID)
);

-- Insert users
INSERT INTO USERS (USER_USERNAME, USER_PASSWORD, USER_NAME)
VALUES ('longline', '123', 'Long'),
       ('gustavtjac', '123', 'Gustavo'),
       ('fredfox', '123', 'Freddie'),
       ('nefarious', '123', 'Monster med brækket arm');

-- Insert wishlists (UUIDs generated at insert time)
INSERT INTO WISHLIST (WISHLIST_ID, WISHLIST_USER_ID, WISHLIST_NAME)
VALUES (RANDOM_UUID(), 1, 'Christmas Gifts'),
       (RANDOM_UUID(), 1, 'Birthday Wishes'),
       (RANDOM_UUID(), 2, 'Tech Wishlist'),
       (RANDOM_UUID(), 3, 'Travel Gear'),
       (RANDOM_UUID(), 4, 'Gaming Grej');

-- Insert wishes using subqueries to match wishlist names (H2 supports this)
INSERT INTO WISH (WISH_ID, WISH_WISHLIST_ID, WISH_NAME, WISH_DESC, WISH_IMGURL, WISH_PRICE, WISH_URL)
SELECT RANDOM_UUID(),
       WISHLIST_ID,
       'Wireless Headphones',
       'Noise-cancelling bluetooth headphones',
       'https://images.unsplash.com/photo-1505740420928-5e0f0bd5e321',
       199.99,
       'https://store.com/headphones'
FROM WISHLIST
WHERE WISHLIST_NAME = 'Christmas Gifts';

INSERT INTO WISH (WISH_ID, WISH_WISHLIST_ID, WISH_NAME, WISH_DESC, WISH_IMGURL, WISH_PRICE, WISH_URL)
SELECT RANDOM_UUID(),
       WISHLIST_ID,
       'Smartwatch',
       'Latest fitness tracking smartwatch',
       'https://images.unsplash.com/photo-1579586337278-3befd40fd17a',
       249.50,
       'https://store.com/smartwatch'
FROM WISHLIST
WHERE WISHLIST_NAME = 'Christmas Gifts';

INSERT INTO WISH (WISH_ID, WISH_WISHLIST_ID, WISH_NAME, WISH_DESC, WISH_IMGURL, WISH_PRICE, WISH_URL)
SELECT RANDOM_UUID(),
       WISHLIST_ID,
       'Laptop',
       'High-performance ultrabook',
       'https://images.unsplash.com/photo-1498050108023-c5249f4df085',
       1299.00,
       'https://store.com/laptop'
FROM WISHLIST
WHERE WISHLIST_NAME = 'Tech Wishlist';

INSERT INTO WISH (WISH_ID, WISH_WISHLIST_ID, WISH_NAME, WISH_DESC, WISH_IMGURL, WISH_PRICE, WISH_URL)
SELECT RANDOM_UUID(),
       WISHLIST_ID,
       'Travel Backpack',
       'Waterproof hiking backpack',
       'https://images.unsplash.com/photo-1491637639971-8a8054e9a52e',
       129.99,
       'https://store.com/backpack'
FROM WISHLIST
WHERE WISHLIST_NAME = 'Travel Gear';
