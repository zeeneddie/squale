--#########################################
-- Modifications for the exporter for the shared repositiory

-- New table for the date of the last export for each application
create table ApplicationLastExport (
        lastExportId bigint not null auto_increment,
        ComponentId bigint unique,
        auditDate datetime,
        primary key (lastExportId)
    ) type=InnoDB;
    
    
-- Constrain between the table component(application) and the table lastexport 
alter table ApplicationLastExport 
    add index FK1E3E973AB660AF72 (ComponentId), 
    add constraint FK1E3E973AB660AF72 
    foreign key (ComponentId) 
    references Component (ComponentId);
    
    
    
--############################################