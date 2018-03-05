CREATE TABLE YelpUser(
			yelpingSince DATE,
			reviewsCount INT,
			name CHAR(40),
			userID CHAR(30),
			fans INT,
			averageStars FLOAT,
			userType CHAR(10),
			compliments CHAR(50),
			elite CHAR(20),
			PRIMARY KEY (userID));
			
CREATE TABLE UserVote(
			userID CHAR(30),
			vote CHAR(10),
			voteCount INT,
			FOREIGN KEY (userID) REFERENCES YelpUser ON DELETE CASCADE);

CREATE TABLE Friend(
			userID CHAR(30),
			friendID CHAR(30),
			FOREIGN KEY (userID) REFERENCES YelpUser ON DELETE CASCADE,
			FOREIGN KEY (friendID) REFERENCES YelpUser ON DELETE CASCADE);

CREATE TABLE BusinessCategory(
			categoryName CHAR(30),
			PRIMARY KEY (categoryName));    

CREATE TABLE Business(
			businessID CHAR(30),
			open CHAR(5),
			city CHAR (20),
			state CHAR (20),
			latitude CHAR(20),
			longitude CHAR(20),
			reviewsCount INT,
			name CHAR(30),
			neighborhoods CHAR(30),
			stars FLOAT,
			attributes CHAR(20),
			PRIMARY KEY (businessID));
			
CREATE TABLE BusinessHours(
			businessID CHAR(30),
			day CHAR(10),
			OpenTime DATE,
			CloseTime DATE,
			FOREIGN KEY (businessID) REFERENCES Business);
			
CREATE TABLE BusinessWithCategory(
			businessID CHAR(30),
			categoryName CHAR(30),
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
			text CHAR(1000),
			type CHAR(10),
			businessID CHAR(30),
			PRIMARY KEY (reviewsID),
			FOREIGN KEY(userID) REFERENCES YelpUser,
			FOREIGN KEY(businessID) REFERENCES Business);
			
CREATE TABLE ReviewsVote(
			reviewsID CHAR(30),
			vote CHAR(10),
			voteCount INT,
			FOREIGN KEY (reviewsID) REFERENCES Reviews ON DELETE CASCADE);
			
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