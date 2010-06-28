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