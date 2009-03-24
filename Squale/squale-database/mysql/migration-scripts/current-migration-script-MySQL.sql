-- #####################################################
-- Modification for the manual practice (#126)

-- Add the column timelimitation to the table qualityrule
ALTER TABLE qualityrule ADD timelimitation varchar(6);
-- Add the column timelimitation to the table qualityresult
ALTER TABLE qualityresult ADD CreationDate datetime;
-- Authorize to put a null value in the column auditId of the table qualityresult
ALTER TABLE qualityresult MODIFY AuditId bigint null;

-- #####################################################
-- Modifications for the Tags implementation
create table Tag (
    TagId bigint not null auto_increment,
    Name varchar(255) not null unique,
    Description text not null,
    TagCategory bigint,
    primary key (TagId)
) type=InnoDB;

create table TagCategory (
    TagCategoryId bigint not null auto_increment,
    Name varchar(255) not null unique,
    Description text not null,
    primary key (TagCategoryId)
) type=InnoDB;

create table Tag_Component (
    ComponentId bigint not null,
    TagId bigint not null,
    primary key (ComponentId, TagId)
) type=InnoDB;

alter table Tag 
    add index FK1477A5C258AC2 (TagCategory), 
    add constraint FK1477A5C258AC2 
    foreign key (TagCategory) 
    references TagCategory (TagCategoryId);

alter table Tag_Component 
    add index FKE093EE5863D3E5E8 (ComponentId), 
    add constraint FKE093EE5863D3E5E8 
    foreign key (ComponentId) 
    references Component (ComponentId);

alter table Tag_Component 
    add index FKE093EE5828B1F3C1 (TagId), 
    add constraint FKE093EE5828B1F3C1 
    foreign key (TagId) 
    references Tag (TagId);

-- #####################################################
--Modifications for [#152]

ALTER TABLE auditbo MODIFY squale_version VARCHAR(100) DEFAULT '5.1';

-- #####################################################
-- For Language display customization
-- This migration script HAS NOT BEEN TESTED

-- Table Tasks_User
ALTER TABLE Tasks_User 
	ADD language varchar(255) NULL;

-- Table SqualeReference & ajout de la colonne ProgrammingLanguage
ALTER TABLE squaleReference 
	ADD programminglanguage varchar(255) NULL;