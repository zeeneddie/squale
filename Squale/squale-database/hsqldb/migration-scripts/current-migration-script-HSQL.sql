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
    references SqualeReference (ReferencielId)
    on delete cascade;

alter table FactorRef  
    add constraint FK2854B0A497209814 
    foreign key (Rule) 
    references QualityRule (QualityRuleId)
    on delete cascade;
    
alter table SqualeReference  
    add constraint FK32FD7E08499FD217 
    foreign key (QualityGrid) 
    references QualityGrid (QualityGridId)
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
   
alter table QualityRule add Criticality integer;
update QualityRule set Criticality = 1 where subclass = 'PracticeRule';
    
--###########################################################
--Modify the squale version
alter table AuditBO alter SQUALE_VERSION set default '7.0' ;

--###########################################################