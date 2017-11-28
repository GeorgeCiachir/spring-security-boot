-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema application_security_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `application_security_db` ;

-- -----------------------------------------------------
-- Schema application_security_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `application_security_db` DEFAULT CHARACTER SET utf8 ;
USE `application_security_db` ;

-- -----------------------------------------------------
-- Table `application_security_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `application_security_db`.`user` ;

CREATE TABLE IF NOT EXISTS `application_security_db`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(70) NULL,
  `enabled` TINYINT(1) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `user_username_UNIQUE` (`username` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `application_security_db`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `application_security_db`.`role` ;

CREATE TABLE IF NOT EXISTS `application_security_db`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_role_name_UNIQUE` (`role_name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `application_security_db`.`permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `application_security_db`.`permission` ;

CREATE TABLE IF NOT EXISTS `application_security_db`.`permission` (
  `permission_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`permission_id`),
  UNIQUE INDEX `prmission_name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `application_security_db`.`user_has_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `application_security_db`.`user_has_role` ;

CREATE TABLE IF NOT EXISTS `application_security_db`.`user_has_role` (
  `user_user_id` INT NOT NULL,
  `role_role_id` INT NOT NULL,
  PRIMARY KEY (`user_user_id`, `role_role_id`),
  INDEX `fk_user_has_role_role1_idx` (`role_role_id` ASC),
  INDEX `fk_user_has_role_user_idx` (`user_user_id` ASC),
  CONSTRAINT `fk_user_has_role_user`
  FOREIGN KEY (`user_user_id`)
  REFERENCES `application_security_db`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_role_role1`
  FOREIGN KEY (`role_role_id`)
  REFERENCES `application_security_db`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `application_security_db`.`role_has_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `application_security_db`.`role_has_permission` ;

CREATE TABLE IF NOT EXISTS `application_security_db`.`role_has_permission` (
  `role_role_id` INT NOT NULL,
  `permission_permission_id` INT NOT NULL,
  PRIMARY KEY (`role_role_id`, `permission_permission_id`),
  INDEX `fk_role_has_permission_permission1_idx` (`permission_permission_id` ASC),
  INDEX `fk_role_has_permission_role1_idx` (`role_role_id` ASC),
  CONSTRAINT `fk_role_has_permission_role1`
  FOREIGN KEY (`role_role_id`)
  REFERENCES `application_security_db`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_has_permission_permission1`
  FOREIGN KEY (`permission_permission_id`)
  REFERENCES `application_security_db`.`permission` (`permission_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

INSERT INTO `application_security_db`.`user` (`username`, `email`, `password`, `enabled`) VALUES ('George@company.com', 'George@company.com', '$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q', '1');
INSERT INTO `application_security_db`.`user` (`username`, `email`, `password`, `enabled`) VALUES ('Petre@gmail.com', 'Petre@gmail.com', '$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q',  '1');
INSERT INTO `application_security_db`.`user` (`username`, `email`, `password`, `enabled`) VALUES ('Alex@yahoo.com', 'Alex@yahoo.com', '$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q',  '1');
INSERT INTO `application_security_db`.`user` (`username`, `email`, `password`, `enabled`) VALUES ('Mihai@company.com', 'Mihai@company.com', '$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q',  '1');


INSERT INTO `application_security_db`.`role` (`role_name`) VALUES ('ROLE_ADMIN_SITE');
INSERT INTO `application_security_db`.`role` (`role_name`) VALUES ('ROLE_ADMIN_OPS');
INSERT INTO `application_security_db`.`role` (`role_name`) VALUES ('ROLE_SIMPLE_USER');
INSERT INTO `application_security_db`.`role` (`role_name`) VALUES ('ROLE_PREMIUM_USER');

INSERT INTO `application_security_db`.`user_has_role` (`user_user_id`, `role_role_id`) VALUES ('1', '1');
INSERT INTO `application_security_db`.`user_has_role` (`user_user_id`, `role_role_id`) VALUES ('1', '2');
INSERT INTO `application_security_db`.`user_has_role` (`user_user_id`, `role_role_id`) VALUES ('2', '3');
INSERT INTO `application_security_db`.`user_has_role` (`user_user_id`, `role_role_id`) VALUES ('3', '4');
INSERT INTO `application_security_db`.`user_has_role` (`user_user_id`, `role_role_id`) VALUES ('4', '2');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;