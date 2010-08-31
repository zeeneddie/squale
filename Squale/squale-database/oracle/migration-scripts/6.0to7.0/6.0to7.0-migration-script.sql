--#############################################
--# Modifications for #257

alter table FactorRef
	drop constraint FK2854B0A4A4AEA807;
	
alter table FactorRef
	drop constraint FK2854B0A497209814;
	
alter table SqualeReference
	drop constraint FK32FD7E08499FD217;

alter table FactorRef 
    add constraint FK2854B0A4A4AEA807 
    foreign key (ReferencielId) 
    references SqualeReference
    on delete cascade;

alter table FactorRef 
    add constraint FK2854B0A497209814 
    foreign key (Rule) 
    references QualityRule
    on delete cascade;
        
alter table SqualeReference 
    add constraint FK32FD7E08499FD217 
    foreign key (QualityGrid) 
    references QualityGrid
    on delete cascade;
    
    
--#############################################
--# Modifications for #256

alter table Module
	drop constraint FK89B0928C7729BC88;
    
alter table Module 
    add constraint FK89B0928C7729BC88 
    foreign key (RuleId) 
    references Rule
    on delete cascade;    
    
--#############################################
--# Modifications for #259
    
alter table QualityRule add Criticality number(10,0);
update QualityRule set Criticality = 1 where subclass = 'PracticeRule';

--#############################################
-- Modify the squale version
alter table AUDITBO modify SQUALE_VERSION default '7.0' ;

--###########################################################