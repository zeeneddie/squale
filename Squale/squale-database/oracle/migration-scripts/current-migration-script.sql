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
    Name varchar2(1024) not null unique,
    Description varchar2(1024),
    TagCategory number(19,0),
    primary key (TagId)
);

create table TagCategory (
    TagCategoryId number(19,0) not null,
    Name varchar2(1024) not null unique,
    Description varchar2(1024),
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
    references TagCategory

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