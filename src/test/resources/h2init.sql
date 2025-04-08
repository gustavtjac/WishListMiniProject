DROP DATABASE IF EXISTS WishlistDB;
    CREATE DATABASE WishlistDB;
    USE WishlistDB;

    -- Opretter User tabellen
CREATE TABLE USER (
                      USER_ID INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
                      USER_USERNAME VARCHAR(50) UNIQUE NOT NULL,
                      USER_PASSWORD VARCHAR(60) NOT NULL,
                      USER_NAME VARCHAR(50)
);

-- Opretter Wishlist tabellen
CREATE TABLE WISHLIST (
                          WISHLIST_ID CHAR(36) PRIMARY KEY DEFAULT (UUID()),
                          WISHLIST_USER_ID INTEGER(10) NOT NULL,
                          WISHLIST_NAME VARCHAR(55) NOT NULL,
                          FOREIGN KEY (WISHLIST_USER_ID) REFERENCES USER(USER_ID)
);

-- Opretter wish tabellen
CREATE TABLE WISH (
                      WISH_ID CHAR(36) PRIMARY KEY DEFAULT (UUID()),
                      WISH_WISHLIST_ID CHAR(36) NOT NULL,
                      WISH_NAME VARCHAR(55) NOT NULL,
                      WISH_DESC VARCHAR(255) NOT NULL,
                      WISH_IMGURL VARCHAR(2000),
                      WISH_PRICE DOUBLE(10,2) NOT NULL,
                      WISH_URL VARCHAR(2000),
                      WISH_RESERVED BOOLEAN NOT NULL DEFAULT 0,
                      FOREIGN KEY (WISH_WISHLIST_ID) REFERENCES WISHLIST(WISHLIST_ID)
);

USE WishlistDB;
-- FØRST KØR SCRIPT EFTER DU HAR OPRETTET EN BRUGER SÅ DU KAN HIVE DEN KRYPTEREDE KODE
-- Opretter vores brugere,
INSERT INTO USER (USER_USERNAME, USER_PASSWORD, USER_NAME) VALUES
                                                               ('longline', '123', 'Long'),
                                                               ('gustavtjac', '123', 'Gustavo'),
                                                               ('fredfox', '123', 'Freddie'),
                                                               ('nefarious', '123', 'Monster med brækket arm');

-- Opretter nogle ønskelister
INSERT INTO WISHLIST (WISHLIST_USER_ID, WISHLIST_NAME) VALUES
                                                           (1, 'Christmas Gifts'),
                                                           (1, 'Birthday Wishes'),
                                                           (2, 'Tech Wishlist'),
                                                           (3, 'Travel Gear'),
                                                           (4, 'Gaming Grej');

-- Opretter nogle ønsker og forbinder dem via navn
INSERT INTO WISH (WISH_WISHLIST_ID, WISH_NAME, WISH_DESC, WISH_IMGURL, WISH_PRICE, WISH_URL) VALUES
                                                                                                 ((SELECT WISHLIST_ID FROM WISHLIST WHERE WISHLIST_NAME = 'Christmas Gifts'), 'Wireless Headphones', 'Noise-cancelling bluetooth headphones', 'https://images.unsplash.com/photo-1505740420928-5e0f0bd5e321', 199.99, 'https://store.com/headphones'),
                                                                                                 ((SELECT WISHLIST_ID FROM WISHLIST WHERE WISHLIST_NAME = 'Christmas Gifts'), 'Smartwatch', 'Latest fitness tracking smartwatch', 'https://images.unsplash.com/photo-1579586337278-3befd40fd17a', 249.50, 'https://store.com/smartwatch'),
                                                                                                 ((SELECT WISHLIST_ID FROM WISHLIST WHERE WISHLIST_NAME = 'Tech Wishlist'), 'Laptop', 'High-performance ultrabook', 'https://images.unsplash.com/photo-1498050108023-c5249f4df085', 1299.00, 'https://store.com/laptop'),
                                                                                                 ((SELECT WISHLIST_ID FROM WISHLIST WHERE WISHLIST_NAME = 'Travel Gear'), 'Travel Backpack', 'Waterproof hiking backpack', 'https://images.unsplash.com/photo-1491637639971-8a8054e9a52e', 129.99, 'https://store.com/backpack');
