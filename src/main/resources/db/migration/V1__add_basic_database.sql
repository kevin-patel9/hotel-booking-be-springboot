-- USERS TABLE
CREATE TABLE users (
   id       INT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(20) NOT NULL,
   email    VARCHAR(50) NOT NULL,
   country  VARCHAR(40) NOT NULL,
   city     VARCHAR(40) NOT NULL,
   phone    VARCHAR(18) NOT NULL,
   password VARCHAR(100) NOT NULL,
   is_admin tinyint(1) default 1 null default 0,
   roles    VARCHAR(10) default 'USER' not null,
   img      VARCHAR(255) NULL DEFAULT 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png'
);

-- HOTELS TABLE
CREATE TABLE hotels (
    id       VARCHAR(100) DEFAULT (UUID()) NOT NULL PRIMARY KEY,
    type     VARCHAR(100) NOT NULL,
    city     VARCHAR(100) NOT NULL,
    address  LONGTEXT NOT NULL,
    distance VARCHAR(200) NOT NULL,
    `desc`   LONGTEXT NOT NULL,
    rating   TINYINT CHECK (rating BETWEEN 1 AND 5),
    price    DECIMAL(10, 2) NOT NULL,
    featured BOOLEAN DEFAULT FALSE
);

-- ROOMS TABLE
CREATE TABLE rooms (
    id         VARCHAR(100) DEFAULT (UUID()) NOT NULL PRIMARY KEY,
    title      VARCHAR(120) NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    max_people TINYINT NOT NULL,
    `desc`     LONGTEXT NOT NULL,
    hotel_id   VARCHAR(100) NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- HOTEL IMAGES TABLE
CREATE TABLE hotel_images (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    hotel_id  VARCHAR(100) NOT NULL,
    img_url   VARCHAR(255) NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- HOTEL ROOMS LINK TABLE (many-to-many)
CREATE TABLE hotel_rooms (
     id        INT AUTO_INCREMENT PRIMARY KEY,
     hotel_id  VARCHAR(100) NOT NULL,
     room_id   VARCHAR(100) NOT NULL,
     FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
     FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

-- ROOM NUMBERS TABLE
CREATE TABLE room_numbers (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    room_id   VARCHAR(100) NOT NULL,
    number    INT NOT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE TABLE booked_rooms (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NOT NULL,
    booked_date    DATE NOT NULL,
    FOREIGN KEY (room_id) REFERENCES room_numbers(id) ON DELETE CASCADE
);

