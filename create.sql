-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sdmxgateway
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `sdmxgateway` ;

-- -----------------------------------------------------
-- Schema sdmxgateway
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sdmxgateway` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `sdmxgateway` ;

-- -----------------------------------------------------
-- Table `Name`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Name` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Name` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `en` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Annotated`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Annotated` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Annotated` (
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Codelist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Codelist` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Codelist` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `ID`, `Version`),
  INDEX `fk_Codelist_Name1_idx` (`name` ASC),
  INDEX `fk_Codelist_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_Codelist_Name1`
    FOREIGN KEY (`name`)
    REFERENCES `Name` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Codelist_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Code` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Code` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `CodelistID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `id` VARCHAR(100) NOT NULL,
  `parentCode` VARCHAR(100) NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `CodelistID`, `Version`, `id`),
  INDEX `fk_Code_Name1_idx` (`name` ASC),
  INDEX `fk_Code_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_Code_Codelist`
    FOREIGN KEY (`AgencyID` , `CodelistID` , `Version`)
    REFERENCES `Codelist` (`AgencyID` , `ID` , `Version`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Code_Name1`
    FOREIGN KEY (`name`)
    REFERENCES `Name` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Code_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `DataStructure`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DataStructure` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `DataStructure` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `ID`, `Version`),
  INDEX `fk_DataStructure_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_DataStructure_Name1`
    FOREIGN KEY (`name`)
    REFERENCES `Name` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DataStructure_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `DataStructureReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DataStructureReference` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `DataStructureReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `DataStructureAgencyID` VARCHAR(100) NOT NULL,
  `DataStructureID` VARCHAR(100) NOT NULL,
  `DataStructureVersion` VARCHAR(50) NOT NULL,
  INDEX `fk_DataStructureReference_DataStructure1_idx` (`DataStructureAgencyID` ASC, `DataStructureID` ASC, `DataStructureVersion` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_DataStructureReference_DataStructure1`
    FOREIGN KEY (`DataStructureAgencyID` , `DataStructureID` , `DataStructureVersion`)
    REFERENCES `DataStructure` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Dataflow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Dataflow` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Dataflow` (
  `ID` VARCHAR(100) NOT NULL,
  `AgencyID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `structure` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`ID`, `AgencyID`, `Version`),
  INDEX `fk_Dataflow_DataStructureReference1_idx` (`structure` ASC),
  INDEX `fk_Dataflow_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_Dataflowt_Name1`
    FOREIGN KEY (`name`)
    REFERENCES `Name` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Dataflow_DataStructureReference1`
    FOREIGN KEY (`structure`)
    REFERENCES `DataStructureReference` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Dataflow_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConceptScheme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConceptScheme` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `ConceptScheme` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `ID`, `Version`),
  INDEX `fk_ConceptScheme_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_ConceptScheme_Name1`
    FOREIGN KEY (`name`)
    REFERENCES `Name` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ConceptScheme_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Languages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Languages` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Languages` (
  `lang` VARCHAR(10) NOT NULL,
  `Name` VARCHAR(2000) NULL,
  PRIMARY KEY (`lang`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `NameText`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `NameText` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `NameText` (
  `id` BIGINT NOT NULL,
  `lang` VARCHAR(10) NOT NULL,
  `text` TEXT NULL,
  PRIMARY KEY (`id`, `lang`),
  INDEX `fk_Name_Languages1_idx` (`lang` ASC),
  INDEX `fk_NameText_Name1_idx` (`id` ASC),
  CONSTRAINT `fk_Name_Languages1`
    FOREIGN KEY (`lang`)
    REFERENCES `Languages` (`lang`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_NameText_Name1`
    FOREIGN KEY (`id`)
    REFERENCES `Name` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `CodelistReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CodelistReference` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `CodelistReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `CodelistAgencyID` VARCHAR(100) NOT NULL,
  `CodelistID` VARCHAR(100) NOT NULL,
  `CodelistVersion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_CodelistReference_Codelist1_idx` (`CodelistAgencyID` ASC, `CodelistID` ASC, `CodelistVersion` ASC),
  CONSTRAINT `fk_CodelistReference_Codelist1`
    FOREIGN KEY (`CodelistAgencyID` , `CodelistID` , `CodelistVersion`)
    REFERENCES `Codelist` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConceptSchemeReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConceptSchemeReference` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `ConceptSchemeReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ConceptSchemeAgencyID` VARCHAR(100) NOT NULL,
  `ConceptSchemeID` VARCHAR(100) NOT NULL,
  `ConceptSchemeVersion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ConceptReference_ConceptScheme1_idx` (`ConceptSchemeAgencyID` ASC, `ConceptSchemeID` ASC, `ConceptSchemeVersion` ASC),
  CONSTRAINT `fk_ConceptReference_ConceptScheme1`
    FOREIGN KEY (`ConceptSchemeAgencyID` , `ConceptSchemeID` , `ConceptSchemeVersion`)
    REFERENCES `ConceptScheme` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Concept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Concept` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Concept` (
  `ConceptSchemeAgencyID` VARCHAR(100) NOT NULL,
  `ConceptSchemeID` VARCHAR(100) NOT NULL,
  `ConceptSchemeVersion` VARCHAR(50) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`ConceptSchemeAgencyID`, `ConceptSchemeID`, `ConceptSchemeVersion`, `ID`),
  INDEX `fk_Concept_Name2_idx` (`name` ASC),
  INDEX `fk_Concept_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_Concept_ConceptScheme1`
    FOREIGN KEY (`ConceptSchemeAgencyID` , `ConceptSchemeID` , `ConceptSchemeVersion`)
    REFERENCES `ConceptScheme` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Concept_Name2`
    FOREIGN KEY (`name`)
    REFERENCES `Name` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Concept_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConceptReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConceptReference` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `ConceptReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ConceptSchemeAgencyID` VARCHAR(100) NOT NULL,
  `ConceptSchemeID` VARCHAR(100) NOT NULL,
  `ConceptSchemeVersion` VARCHAR(50) NOT NULL,
  `ConceptID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ConceptReference_Concept1`
    FOREIGN KEY (`ConceptSchemeAgencyID` , `ConceptSchemeID` , `ConceptSchemeVersion` , `ConceptID`)
    REFERENCES `Concept` (`ConceptSchemeAgencyID` , `ConceptSchemeID` , `ConceptSchemeVersion` , `ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `AttributeRelationshipType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AttributeRelationshipType` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `AttributeRelationshipType` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `empty` TINYINT(1) NULL,
  `attachGroup` VARCHAR(200) NULL,
  `attachGroups` TINYINT(1) NULL,
  `primaryMeasureReference` VARCHAR(200) NULL DEFAULT 'OBS_VALUE',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `DataStructureComponent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DataStructureComponent` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `DataStructureComponent` (
  `DataStructureAgencyID` VARCHAR(100) NOT NULL,
  `DataStructureID` VARCHAR(100) NOT NULL,
  `DataStructureVersion` VARCHAR(50) NOT NULL,
  `Position` INT NOT NULL,
  `Type` INT NULL,
  `ConceptIdentity` BIGINT NULL,
  `CodelistEnumeration` BIGINT NULL,
  `ConceptSchemeEnumeration` BIGINT NULL,
  `AssignmentStatus` VARCHAR(50) NULL,
  `AttributeRelationshipType` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`DataStructureAgencyID`, `DataStructureID`, `DataStructureVersion`, `Position`),
  INDEX `fk_DataStructureComponents_ConceptReference1_idx` (`ConceptIdentity` ASC),
  INDEX `fk_DataStructureComponents_CodelistReference1_idx` (`CodelistEnumeration` ASC),
  INDEX `fk_DataStructureComponents_ConceptSchemeReference1_idx` (`ConceptSchemeEnumeration` ASC),
  INDEX `fk_DataStructureComponents_AttributeRelationshipType1_idx` (`AttributeRelationshipType` ASC),
  INDEX `fk_DataStructureComponents_Annotated1_idx` (`annotations` ASC),
  CONSTRAINT `fk_DataStructureComponents_DataStructure1`
    FOREIGN KEY (`DataStructureAgencyID` , `DataStructureID` , `DataStructureVersion`)
    REFERENCES `DataStructure` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DataStructureComponents_ConceptReference1`
    FOREIGN KEY (`ConceptIdentity`)
    REFERENCES `ConceptReference` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DataStructureComponents_CodelistReference1`
    FOREIGN KEY (`CodelistEnumeration`)
    REFERENCES `CodelistReference` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DataStructureComponents_ConceptSchemeReference1`
    FOREIGN KEY (`ConceptSchemeEnumeration`)
    REFERENCES `ConceptSchemeReference` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_DataStructureComponents_AttributeRelationshipType1`
    FOREIGN KEY (`AttributeRelationshipType`)
    REFERENCES `AttributeRelationshipType` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DataStructureComponents_Annotated1`
    FOREIGN KEY (`annotations`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `Annotation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Annotation` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Annotation` (
  `Annotated` BIGINT NOT NULL,
  `index` BIGINT NOT NULL,
  `title` VARCHAR(200) NULL,
  `url` VARCHAR(300) NULL,
  `type` VARCHAR(200) NULL,
  `annotationId` VARCHAR(200) NULL,
  PRIMARY KEY (`Annotated`, `index`),
  INDEX `fk_Annotation_Annotated1_idx` (`Annotated` ASC),
  CONSTRAINT `fk_Annotation_Annotated1`
    FOREIGN KEY (`Annotated`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `AnnotationText`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AnnotationText` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `AnnotationText` (
  `Annotated` BIGINT NOT NULL,
  `index` BIGINT NOT NULL,
  `textIndex` BIGINT NOT NULL,
  `lang` VARCHAR(10) NULL,
  `text` TEXT NULL,
  PRIMARY KEY (`Annotated`, `index`, `textIndex`),
  CONSTRAINT `fk_AnnotationText_Annotation1`
    FOREIGN KEY (`Annotated` , `index`)
    REFERENCES `Annotation` (`Annotated` , `index`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `AttributeRelationshipAttachGroups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AttributeRelationshipAttachGroups` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `AttributeRelationshipAttachGroups` (
  `id` BIGINT NOT NULL,
  `index` INT NOT NULL,
  `attachGroup` VARCHAR(200) NULL,
  PRIMARY KEY (`id`, `index`),
  CONSTRAINT `fk_AttributeRelationshipAttachGroups_AttributeRelationshipType1`
    FOREIGN KEY (`id`)
    REFERENCES `AttributeRelationshipType` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `SEQUENCE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SEQUENCE` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `SEQUENCE` (
  `SEQ_COUNT` BIGINT NOT NULL DEFAULT 0,
  `SEQ_NAME` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`SEQ_NAME`))
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
