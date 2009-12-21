--#########################################
-- Modifications for the exporter for the shared repositiory

-- New sequence
create sequence job_sequence;
create sequence lastExport_sequence;

-- New table for the date of the last export for each application
create table ApplicationLastExport (
	LastExportId number(19,0) not null,
  	ComponentId number(19,0) unique,
    LastExportDate date,
    ToExport number(1,0),
    primary key (LastExportId)
);

create table Job (
    JobId number(19,0) not null,
    JobName varchar2(100),
    JobStatus varchar2(100),
    JobDate date,
    primary key (JobId)
);

alter table ApplicationLastExport modify ToExport default 0 ;
    
-- Constrain between the table component(application) and the table lastexport
alter table ApplicationLastExport 
    add constraint FK1E3E973AB660AF72 
    foreign key (ComponentId) 
    references Component;



--#########################################
-- Modifications needed to add the auditor role

Insert into ProfileBO
   (PROFILEID, NAME)
 Values
   (profile_sequence.NextVal, 'bo.profile.name.auditor');

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

Insert into UserBO
   (USERID, MATRICULE, PASSWORD,PROFILEID)
 Values
   (user_sequence.NextVal, 'squaleauditor','audit',(select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'));   