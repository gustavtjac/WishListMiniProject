CREATE DATABASE WishlistDB;
USE WishlistDB;

CREATE TABLE USER (
	USER_ID INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
    USER_USERNAME VARCHAR(25) UNIQUE NOT NULL,
    USER_PASSWORD VARCHAR(25) NOT NULL,
    USER_NAME VARCHAR(25)
);

CREATE TABLE WISHLIST (
	WISHLIST_ID INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
    WISHLIST_USER_ID INTEGER(10) Not NULL,
    WISHLIST_NAME VARCHAR(55) NOT NULL,
    FOREIGN KEY (WISHLIST_USER_ID) REFERENCES USER(USER_ID)
);

CREATE TABLE WISH (
	WISH_IS INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
    WISH_WISHLIST_ID INTEGER(10) NOT NULL,
    WISH_NAME VARCHAR(55) NOT NULL,
    WISH_DESC VARCHAR(255) NOT NULL,
    WISH_IMGURL VARCHAR(255),
    WISH_PRICE DOUBLE(10,2) NOT NULL,
    WISH_URL VARCHAR(255),
    FOREIGN KEY (WISH_WISHLIST_ID) REFERENCES WISHLIST(WISHLIST_ID)
);