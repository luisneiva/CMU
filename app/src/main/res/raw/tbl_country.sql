CREATE TABLE `countries` (`code` int(3) NOT NULL, `alpha2` varchar(2) NOT NULL UNIQUE, `alpha3` varchar(3) NOT NULL UNIQUE, `langCS` varchar(45) NOT NULL, `langDE` varchar(45) NOT NULL, `langEN` varchar(45) NOT NULL, `langES` varchar(45) NOT NULL, `langFR` varchar(45) NOT NULL, `langIT` varchar(45) NOT NULL, `langNL` varchar(45) NOT NULL, PRIMARY KEY (`code`));