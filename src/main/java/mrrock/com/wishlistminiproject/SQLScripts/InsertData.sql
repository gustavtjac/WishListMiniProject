Use WishlistDB;

-- opret brugere 
insert into USER (USER_USERNAME,USER_PASSWORD,USER_NAME) value ('longline','123','Long');
insert into USER (USER_USERNAME,USER_PASSWORD,USER_NAME) value ('gustavtjac','123','Gustavo');
insert into USER (USER_USERNAME,USER_PASSWORD,USER_NAME) value ('fredfox','123','Freddie');
insert into USER (USER_USERNAME,USER_PASSWORD,USER_NAME) value ('nefarious','123','Monster med brækket arm');


-- Opret test ønskelister
INSERT INTO WISHLIST (WISHLIST_USER_ID, WISHLIST_NAME) VALUES
(1, 'Christmas Gifts'),
(1, 'Birthday Wishes'),
(2, 'Tech Wishlist'),
(3, 'Travel Gear'),
(4, 'Gaming Grej');

-- opret test ønsker
INSERT INTO WISH (WISH_WISHLIST_ID, WISH_NAME, WISH_DESC, WISH_IMGURL, WISH_PRICE, WISH_URL) VALUES
(1, 'Wireless Headphones', 'Noise-cancelling bluetooth headphones', 'https://images.unsplash.com/photo-1505740420928-5e0f0bd5e321', 199.99, 'https://store.com/headphones'),
(1, 'Smartwatch', 'Latest fitness tracking smartwatch', 'https://images.unsplash.com/photo-1579586337278-3befd40fd17a', 249.50, 'https://store.com/smartwatch'),
(3, 'Laptop', 'High-performance ultrabook', 'https://images.unsplash.com/photo-1498050108023-c5249f4df085', 1299.00, 'https://store.com/laptop'),
(4, 'Travel Backpack', 'Waterproof hiking backpack', 'https://images.unsplash.com/photo-1491637639971-8a8054e9a52e', 129.99, 'https://store.com/backpack');