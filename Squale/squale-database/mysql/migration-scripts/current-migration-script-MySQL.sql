-- #####################################################
-- Modification for the manual practice comments

--Add the comments table
create table QualityResult_Comment (
	QR_CommentId bigint not null auto_increment,
	Comments varchar(4000),
	QualityResultId bigint unique,
	primary key (QR_CommentId)
) type=InnoDB;
    
alter table QualityResult_Comment 
	add index FKD36C3ADCCCF6BB41 (QualityResultId), 
	add constraint FKD36C3ADCCCF6BB41 
	foreign key (QualityResultId) 
	references QualityResult (QualityResultId);	

-- Modification for #208 
alter table Component add QualityApproachOnStart bit;
alter table Component add InInitialDev bit;
alter table Component add GlobalCost integer;
alter table Component add DevCost integer;
alter table Component alter InInitialDev set default 1;
alter table Component alter QualityApproachOnStart set default 0;
alter table Component alter GlobalCost set default 0;
alter table Component alter DevCost set default 0;
update Component set InInitialDev=1, QualityApproachOnStart=0 , GlobalCost=0 , DevCost=0;
