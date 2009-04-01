-- #####################################################
-- Modification for the manual practice (#126)

-- Add the column timelimitation to the table qualityrule
alter table QualityRule add TimeLimitation varchar2(6);
-- Add the column timelimitation to the table qualityresult
alter table QualityResult add CreationDate date;
-- Authorize to put a null value in the column auditId of the table qualityresult
alter table QualityResult modify AuditId null;

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
alter table AuditBO add ver varchar2(100);
update AuditBO set ver = squale_version;
update AuditBO set squale_version = null;
alter table AuditBO modify squale_version varchar2(100) default '5.1';
update AuditBO set squale_version = ver;
alter table AuditBO drop column ver;

-- ###########################################################
-- For Language display customization for SQUALE version 4.1.1

-- Table Tasks_User & Ajout de la colonne Language
alter table Tasks_User add language varchar2(255) NULL;

-- Table SqualeReference & ajout de la colonne ProgrammingLanguage
alter table SqualeReference add programminglanguage varchar2(255) NULL;