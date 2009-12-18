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
    
--########################################