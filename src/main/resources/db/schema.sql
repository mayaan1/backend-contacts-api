CREATE TABLE `UserLoginCred` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `token` varchar(45) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `USERS` (
  `UID` varchar(50) NOT NULL,
  `MOBILENO` varchar(20) DEFAULT NULL,
  `ADDRESS` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `NAME` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `Contacts` (
  `UID` varchar(50) NOT NULL,
  `ContactUID` varchar(45) NOT NULL,
  `ContactName` varchar(45) DEFAULT NULL,
  `Score` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`UID`,`ContactUID`),
  KEY `contactUID_idx` (`ContactUID`),
  CONSTRAINT `contactUID` FOREIGN KEY (`ContactUID`) REFERENCES `USERS` (`UID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
