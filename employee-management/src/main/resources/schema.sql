CREATE TABLE department ( 
deptid int NOT NULL AUTO_INCREMENT,
deptname varchar(255),
PRIMARY KEY (deptid)
);

CREATE TABLE employee (
    uuid binary(16) NOT NULL,
    email varchar(255), 	
    firstname varchar(255),
    lastname varchar(255),
    birthdate date,
    deptid int,
	PRIMARY KEY (uuid),
	UNIQUE KEY unique_email (email),
    FOREIGN KEY (deptid) REFERENCES department(deptid)
);