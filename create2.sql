-- MySQL Script generated by MySQL Workbench
-- Sat Jul 14 20:11:07 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema registry
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `registry` ;

-- -----------------------------------------------------
-- Schema registry
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `registry` DEFAULT CHARACTER SET utf8 ;
USE `registry` ;

-- -----------------------------------------------------
-- Table `Name`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Name` ;

CREATE TABLE IF NOT EXISTS `Name` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `en` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Annotated`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Annotated` ;

CREATE TABLE IF NOT EXISTS `Annotated` (
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Codelist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Codelist` ;

CREATE TABLE IF NOT EXISTS `Codelist` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `ID`, `Version`),
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

CREATE INDEX `fk_Codelist_Name1_idx` ON `Codelist` (`name` ASC);

CREATE INDEX `fk_Codelist_Annotated1_idx` ON `Codelist` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `Code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Code` ;

CREATE TABLE IF NOT EXISTS `Code` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `CodelistID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `id` VARCHAR(100) NOT NULL,
  `parentCode` VARCHAR(100) NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `CodelistID`, `Version`, `id`),
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

CREATE INDEX `fk_Code_Name1_idx` ON `Code` (`name` ASC);

CREATE INDEX `fk_Code_Annotated1_idx` ON `Code` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `DataStructure`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DataStructure` ;

CREATE TABLE IF NOT EXISTS `DataStructure` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `ID`, `Version`),
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

CREATE INDEX `fk_DataStructure_Annotated1_idx` ON `DataStructure` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `DataStructureReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DataStructureReference` ;

CREATE TABLE IF NOT EXISTS `DataStructureReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `DataStructureAgencyID` VARCHAR(100) NOT NULL,
  `DataStructureID` VARCHAR(100) NOT NULL,
  `DataStructureVersion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_DataStructureReference_DataStructure1`
    FOREIGN KEY (`DataStructureAgencyID` , `DataStructureID` , `DataStructureVersion`)
    REFERENCES `DataStructure` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_DataStructureReference_DataStructure1_idx` ON `DataStructureReference` (`DataStructureAgencyID` ASC, `DataStructureID` ASC, `DataStructureVersion` ASC);


-- -----------------------------------------------------
-- Table `Dataflow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Dataflow` ;

CREATE TABLE IF NOT EXISTS `Dataflow` (
  `ID` VARCHAR(100) NOT NULL,
  `AgencyID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `structure` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`ID`, `AgencyID`, `Version`),
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

CREATE INDEX `fk_Dataflow_DataStructureReference1_idx` ON `Dataflow` (`structure` ASC);

CREATE INDEX `fk_Dataflow_Annotated1_idx` ON `Dataflow` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `ConceptScheme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConceptScheme` ;

CREATE TABLE IF NOT EXISTS `ConceptScheme` (
  `AgencyID` VARCHAR(100) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `Version` VARCHAR(50) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`AgencyID`, `ID`, `Version`),
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

CREATE INDEX `fk_ConceptScheme_Annotated1_idx` ON `ConceptScheme` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `Languages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Languages` ;

CREATE TABLE IF NOT EXISTS `Languages` (
  `lang` VARCHAR(10) NOT NULL,
  `Name` VARCHAR(2000) NULL,
  PRIMARY KEY (`lang`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NameText`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `NameText` ;

CREATE TABLE IF NOT EXISTS `NameText` (
  `id` BIGINT NOT NULL,
  `lang` VARCHAR(10) NOT NULL,
  `text` TEXT NULL,
  PRIMARY KEY (`id`, `lang`),
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

CREATE INDEX `fk_Name_Languages1_idx` ON `NameText` (`lang` ASC);

CREATE INDEX `fk_NameText_Name1_idx` ON `NameText` (`id` ASC);


-- -----------------------------------------------------
-- Table `CodelistReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CodelistReference` ;

CREATE TABLE IF NOT EXISTS `CodelistReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `CodelistAgencyID` VARCHAR(100) NOT NULL,
  `CodelistID` VARCHAR(100) NOT NULL,
  `CodelistVersion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_CodelistReference_Codelist1`
    FOREIGN KEY (`CodelistAgencyID` , `CodelistID` , `CodelistVersion`)
    REFERENCES `Codelist` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_CodelistReference_Codelist1_idx` ON `CodelistReference` (`CodelistAgencyID` ASC, `CodelistID` ASC, `CodelistVersion` ASC);


-- -----------------------------------------------------
-- Table `ConceptSchemeReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConceptSchemeReference` ;

CREATE TABLE IF NOT EXISTS `ConceptSchemeReference` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ConceptSchemeAgencyID` VARCHAR(100) NOT NULL,
  `ConceptSchemeID` VARCHAR(100) NOT NULL,
  `ConceptSchemeVersion` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ConceptReference_ConceptScheme1`
    FOREIGN KEY (`ConceptSchemeAgencyID` , `ConceptSchemeID` , `ConceptSchemeVersion`)
    REFERENCES `ConceptScheme` (`AgencyID` , `ID` , `Version`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_ConceptReference_ConceptScheme1_idx` ON `ConceptSchemeReference` (`ConceptSchemeAgencyID` ASC, `ConceptSchemeID` ASC, `ConceptSchemeVersion` ASC);


-- -----------------------------------------------------
-- Table `Concept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Concept` ;

CREATE TABLE IF NOT EXISTS `Concept` (
  `ConceptSchemeAgencyID` VARCHAR(100) NOT NULL,
  `ConceptSchemeID` VARCHAR(100) NOT NULL,
  `ConceptSchemeVersion` VARCHAR(50) NOT NULL,
  `ID` VARCHAR(100) NOT NULL,
  `name` BIGINT NULL,
  `annotations` BIGINT NULL,
  PRIMARY KEY (`ConceptSchemeAgencyID`, `ConceptSchemeID`, `ConceptSchemeVersion`, `ID`),
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

CREATE INDEX `fk_Concept_Name2_idx` ON `Concept` (`name` ASC);

CREATE INDEX `fk_Concept_Annotated1_idx` ON `Concept` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `ConceptReference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConceptReference` ;

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


-- -----------------------------------------------------
-- Table `AttributeRelationshipType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AttributeRelationshipType` ;

CREATE TABLE IF NOT EXISTS `AttributeRelationshipType` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `empty` TINYINT(1) NULL,
  `attachGroup` VARCHAR(200) NULL,
  `attachGroups` TINYINT(1) NULL,
  `primaryMeasureReference` VARCHAR(200) NULL DEFAULT 'OBS_VALUE',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DataStructureComponent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DataStructureComponent` ;

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

CREATE INDEX `fk_DataStructureComponents_ConceptReference1_idx` ON `DataStructureComponent` (`ConceptIdentity` ASC);

CREATE INDEX `fk_DataStructureComponents_CodelistReference1_idx` ON `DataStructureComponent` (`CodelistEnumeration` ASC);

CREATE INDEX `fk_DataStructureComponents_ConceptSchemeReference1_idx` ON `DataStructureComponent` (`ConceptSchemeEnumeration` ASC);

CREATE INDEX `fk_DataStructureComponents_AttributeRelationshipType1_idx` ON `DataStructureComponent` (`AttributeRelationshipType` ASC);

CREATE INDEX `fk_DataStructureComponents_Annotated1_idx` ON `DataStructureComponent` (`annotations` ASC);


-- -----------------------------------------------------
-- Table `Annotation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Annotation` ;

CREATE TABLE IF NOT EXISTS `Annotation` (
  `Annotated` BIGINT NOT NULL,
  `index` BIGINT NOT NULL,
  `title` VARCHAR(200) NULL,
  `url` VARCHAR(300) NULL,
  `type` VARCHAR(200) NULL,
  `annotationId` VARCHAR(200) NULL,
  PRIMARY KEY (`Annotated`, `index`),
  CONSTRAINT `fk_Annotation_Annotated1`
    FOREIGN KEY (`Annotated`)
    REFERENCES `Annotated` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Annotation_Annotated1_idx` ON `Annotation` (`Annotated` ASC);


-- -----------------------------------------------------
-- Table `AnnotationText`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AnnotationText` ;

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


-- -----------------------------------------------------
-- Table `AttributeRelationshipAttachGroups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AttributeRelationshipAttachGroups` ;

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


-- -----------------------------------------------------
-- Table `SEQUENCE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SEQUENCE` ;

CREATE TABLE IF NOT EXISTS `SEQUENCE` (
  `SEQ_COUNT` BIGINT NOT NULL DEFAULT 0,
  `SEQ_NAME` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`SEQ_NAME`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
