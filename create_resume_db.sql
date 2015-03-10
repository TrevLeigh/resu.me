-- MySQL Script generated by MySQL Workbench
-- 03/09/15 18:25:45
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema resume_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `resume_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `resume_db` ;

-- -----------------------------------------------------
-- Table `resume_db`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `resume_db`.`locations` (
  `location_id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(100) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `city` VARCHAR(100) NOT NULL,
  `zip` INT NOT NULL,
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resume_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `resume_db`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `location` INT NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `location_id_idx` (`location` ASC),
  CONSTRAINT `location_id`
    FOREIGN KEY (`location`)
    REFERENCES `resume_db`.`locations` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resume_db`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `resume_db`.`employees` (
  `employee_id` INT NOT NULL,
  `skillset` VARCHAR(2000) NOT NULL,
  CONSTRAINT `user_id`
    FOREIGN KEY (`employee_id`)
    REFERENCES `resume_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resume_db`.`listings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `resume_db`.`listings` (
  `listing_id` INT NOT NULL AUTO_INCREMENT,
  `salary` VARCHAR(45) NOT NULL,
  `position` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `location` INT NOT NULL,
  PRIMARY KEY (`listing_id`),
  INDEX `location_id_idx` (`location` ASC),
  CONSTRAINT `location_id`
    FOREIGN KEY (`location`)
    REFERENCES `resume_db`.`locations` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resume_db`.`employers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `resume_db`.`employers` (
  `employer_id` INT NOT NULL,
  `company_name` VARCHAR(200) NOT NULL,
  `skills_looking_for` VARCHAR(2000) NOT NULL,
  `listing` INT NULL,
  INDEX `user_id_idx` (`employer_id` ASC),
  INDEX `listing_id_idx` (`listing` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`employer_id`)
    REFERENCES `resume_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `listing_id`
    FOREIGN KEY (`listing`)
    REFERENCES `resume_db`.`listings` (`listing_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resume_db`.`resumes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `resume_db`.`resumes` (
  `resume_id` INT NOT NULL AUTO_INCREMENT,
  `author_id` INT NOT NULL,
  `resume` VARBINARY(1024) NULL,
  PRIMARY KEY (`resume_id`),
  INDEX `author_id_idx` (`author_id` ASC),
  CONSTRAINT `author_id`
    FOREIGN KEY (`author_id`)
    REFERENCES `resume_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;