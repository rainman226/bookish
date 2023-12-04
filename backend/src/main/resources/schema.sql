-- Creating USERS table
CREATE TABLE _USERS (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        full_name VARCHAR(255),
                        profile_picture VARCHAR(255),
                        bio VARCHAR(255),
                        location VARCHAR(255),
                        privacy_settings VARCHAR(255),
                        role VARCHAR(255) NOT NULL
);

-- Creating GENRES table
CREATE TABLE GENRES (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

-- Creating BOOKS table
CREATE TABLE BOOKS (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       genre_id INT,
                       ISBN VARCHAR(20),
                       description VARCHAR(255),
                       FOREIGN KEY (genre_id) REFERENCES GENRES(id)
);

-- Creating EXCHANGE table
CREATE TABLE EXCHANGE (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          book_id_1 INT,
                          book_id_2 INT,
                          owner_id INT,
                          exchangeDate DATE,
                          status VARCHAR(255),
                          condition VARCHAR(255),
                          FOREIGN KEY (book_id_1) REFERENCES BOOKS(id),
                          FOREIGN KEY (book_id_2) REFERENCES BOOKS(id),
                          FOREIGN KEY (owner_id) REFERENCES _USERS(id)
);