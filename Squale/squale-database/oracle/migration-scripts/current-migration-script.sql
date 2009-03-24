-- #####################################################
-- Modification for the manual practice (#126)

-- Add the column timelimitation to the table qualityrule
ALTER TABLE qualityrule ADD timelimitation varchar2(6);
-- Add the column timelimitation to the table qualityresult
ALTER TABLE qualityresult ADD CreationDate date;
-- Authorize to put a null value in the column auditId of the table qualityresult
ALTER TABLE qualityresult MODIFY AuditId null;

-- #####################################################
-- Modifications for the Tags implementation
create table Tag (
    TagId number(19,0) not null,
    Name varchar2(255) not null unique,
    Description varchar2(1024) not null ,
    TagCategory number(19,0),
    primary key (TagId)
);

create table TagCategory (
    TagCategoryId number(19,0) not null,
    Name varchar2(255) not null unique,
    Description varchar2(1024) not null,
    primary key (TagCategoryId)
);

create table Tag_Component (
    ComponentId number(19,0) not null,
    TagId number(19,0) not null,
    primary key (ComponentId, TagId)
);

alter table Tag 
    add constraint FK1477A5C258AC2 
    foreign key (TagCategory) 
    references TagCategory;

alter table Tag_Component 
    add constraint FKE093EE5863D3E5E8 
    foreign key (ComponentId) 
    references Component
    on delete cascade;

alter table Tag_Component 
    add constraint FKE093EE5828B1F3C1 
    foreign key (TagId) 
    references Tag
    on delete cascade;

create sequence tagCategory_sequence;
create sequence tag_sequence;

-- #####################################################
-- Modifications for [#152]
ALTER TABLE auditbo ADD ver VARCHAR2(100);
UPDATE auditbo SET ver = squale_version;
UPDATE auditbo SET squale_version = null;
ALTER TABLE auditbo MODIFY squale_version VARCHAR2(100) DEFAULT '5.1';
UPDATE auditbo SET squale_version = ver;
ALTER TABLE auditbo DROP COLUMN ver;

-- ###########################################################
-- For Language display customization for SQUALE version 4.1.1

-- Table Tasks_User & Ajout de la colonne Language
ALTER TABLE Tasks_User ADD language varchar2(255) NULL;

-- Table SqualeReference & ajout de la colonne ProgrammingLanguage
ALTER TABLE squaleReference ADD programminglanguage varchar2(255) NULL;