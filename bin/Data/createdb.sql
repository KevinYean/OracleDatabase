CREATE TABLE YelpUser(
			yelpingSince DATE,
			reviewsCount INT,
			name VARCHAR(40),
			userID CHAR(30),
			friendCount INT,
			averageStars FLOAT,
			PRIMARY KEY (userID));
			
CREATE TABLE BusinessCategory(
			categoryName VARCHAR(30),
			PRIMARY KEY (categoryName));    
			
CREATE TABLE SubCategory(
			subName VARCHAR(40),
			mainCategory VARCHAR(30),
			FOREIGN KEY (mainCategory) references BusinessCategory ON DELETE CASCADE);
			

CREATE TABLE Business(
			businessID CHAR(30),
			city VARCHAR (50),
			state VARCHAR(20),
			reviewsCount INT,
			name VARCHAR(100),
			stars FLOAT,
			PRIMARY KEY (businessID));
			
CREATE TABLE BusinessWithCategory(
			businessID CHAR(30),
			categoryName VARCHAR(30),
			FOREIGN KEY (businessID) REFERENCES Business ON DELETE CASCADE,
			FOREIGN KEY (categoryName) REFERENCES BusinessCategory ON DELETE CASCADE);
		
CREATE TABLE Checkin(
			checkinType CHAR(20),
			businessID CHAR(30),
			hours INT,
			day INT,
			checkCount INT,
			FOREIGN KEY (businessID) REFERENCES Business ON DELETE CASCADE);
			
CREATE TABLE Reviews(
			userID CHAR(30),
			reviewsID CHAR(30),
			stars FLOAT,
			voteCount INT,
			reviewDate DATE,
			businessID CHAR(30),
			PRIMARY KEY (reviewsID),
			FOREIGN KEY(userID) REFERENCES YelpUser ON DELETE CASCADE,
			FOREIGN KEY(businessID) REFERENCES Business ON DELETE CASCADE);
			
INSERT INTO BusinessCategory VALUES ('Active Life');
INSERT INTO BusinessCategory VALUES ('Arts & Entertainment');
INSERT INTO BusinessCategory VALUES ('Automotive');
INSERT INTO BusinessCategory VALUES ('Car Rental');
INSERT INTO BusinessCategory VALUES ('Cafes');
INSERT INTO BusinessCategory VALUES ('Beauty & Spas');
INSERT INTO BusinessCategory VALUES ('Convenience Stores');
INSERT INTO BusinessCategory VALUES ('Dentists');
INSERT INTO BusinessCategory VALUES ('Doctors');
INSERT INTO BusinessCategory VALUES ('Drugstores');
INSERT INTO BusinessCategory VALUES ('Department Stores');
INSERT INTO BusinessCategory VALUES ('Education');
INSERT INTO BusinessCategory VALUES ('Event Planning & Services');
INSERT INTO BusinessCategory VALUES ('Flowers & Gifts');
INSERT INTO BusinessCategory VALUES ('Food');
INSERT INTO BusinessCategory VALUES ('Health & Medical');
INSERT INTO BusinessCategory VALUES ('Home Services');
INSERT INTO BusinessCategory VALUES ('Home & Garden');
INSERT INTO BusinessCategory VALUES ('Hospitals');
INSERT INTO BusinessCategory VALUES ('Hotels & Travel');
INSERT INTO BusinessCategory VALUES ('Hardware Stores');
INSERT INTO BusinessCategory VALUES ('Grocery');
INSERT INTO BusinessCategory VALUES ('Medical Centers');
INSERT INTO BusinessCategory VALUES ('Nurseries & Gardening');
INSERT INTO BusinessCategory VALUES ('Nightlife');
INSERT INTO BusinessCategory VALUES ('Restaurants');
INSERT INTO BusinessCategory VALUES ('Shopping');
INSERT INTO BusinessCategory VALUES ('Transportation')