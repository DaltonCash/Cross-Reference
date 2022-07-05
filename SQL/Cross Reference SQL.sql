DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS distributor;

CREATE TABLE business (
business_id int NOT NULL AUTO_INCREMENT,
business_name varchar(40),
business_description varchar(100),
location varchar(100),
zip varchar(5),
fb_followers varchar(10),
email varchar(50),
in_another_zip_code boolean,
date_searched TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
additional_messages varchar(500),
PRIMARY KEY(business_id)
);

CREATE TABLE distributor(
distributor_id int NOT NULL AUTO_INCREMENT,
distributor_name varchar(40) NOT NULL,
location varchar(100),
zip varchar(5),
PRIMARY KEY(distributor_id)
);