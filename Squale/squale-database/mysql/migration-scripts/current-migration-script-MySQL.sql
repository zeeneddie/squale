--#########################################
-- Modifications for the exporter for the shared repositiory

-- New table for the date of the last export for each application
create table ApplicationLastExport (
    LastExportId bigint not null auto_increment,
    ComponentId bigint unique,
    LastExportDate datetime,
    ToExport bit,
    primary key (LastExportId)
) type=InnoDB;

create table Job (
    JobId bigint not null auto_increment,
    JobName varchar(100),
    JobStatus varchar(100),
    JobDate datetime,
    primary key (JobId)
) type=InnoDB;
    
alter table ApplicationLastExport alter ToExport set default 0 ;
    
-- Constrain between the table component(application) and the table lastexport 
alter table ApplicationLastExport 
    add index FK1E3E973AB660AF72 (ComponentId), 
    add constraint FK1E3E973AB660AF72 
    foreign key (ComponentId) 
    references Component (ComponentId);



--#########################################
-- Modifications needed to add the auditor role

Insert into ProfileBO
   (NAME)
 Values
   ('bo.profile.name.auditor');

Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.portal_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_quality_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_component_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_creation'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.documentation'));
