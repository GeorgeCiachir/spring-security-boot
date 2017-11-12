--
-- Application DB creation
--
DROP DATABASE `application_security_db`;
CREATE SCHEMA `application_security_db`;


--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
CREATE TABLE `application_security_db`.`user` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(45) NOT NULL,
  `HASHED_PASSWORD` VARCHAR(70) NOT NULL,
  `ENABLED` TINYINT(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;