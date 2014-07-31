CREATE TABLE tbl_top_artist (id_top_artist INTEGER PRIMARY KEY AUTOINCREMENT, id_place INTEGER, rank INTEGER, artist_mbid VARCHAR(60), artist_name VARCHAR(75) NOT NULL, url VARCHAR(255), image_small VARCHAR(200), image_medium VARCHAR(200), image_large VARCHAR(200));
CREATE TABLE tbl_top_track (id_top_track INTEGER PRIMARY KEY AUTOINCREMENT, id_place INTEGER, rank INTEGER, name VARCHAR(75), duration INTEGER, url VARCHAR(255), artist_mbid VARCHAR(60), artist_name VARCHAR(75), image_small VARCHAR(200), image_medium VARCHAR(200), image_large VARCHAR(200));
CREATE TABLE tbl_place (id_place INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(75) NOT NULL, latitude FLOAT, longitude FLOAT); 