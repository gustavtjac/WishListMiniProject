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
                          WISH_IMGURL VARCHAR(999),
                          WISH_PRICE DOUBLE(10,2) NOT NULL,
                          WISH_URL VARCHAR(255),
                          WISH_RESERVED BOOLEAN NOT NULL DEFAULT 0,
                          FOREIGN KEY (WISH_WISHLIST_ID) REFERENCES WISHLIST(WISHLIST_ID)
    );