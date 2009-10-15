--#########################################
-- Modifications for the exporter for the shared repositiory

-- New sequence
create sequence lastExport_sequence;
    
-- New table for the date of the last export for each application
create table ApplicationLastExport (
	lastExportId number(19,0) not null,
  	ComponentId number(19,0) unique,
    auditDate date,
    primary key (lastExportId)
    );
    
-- Constrain between the table component(application) and the table lastexport
alter table ApplicationLastExport 
    add constraint FK1E3E973AB660AF72 
    foreign key (ComponentId) 
    references Component;
    

--########################################