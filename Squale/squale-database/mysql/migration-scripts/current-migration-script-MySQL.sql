-- #####################################################
-- Modification for the manual practice comments

--Add the comments table
create table QualityResult_Comment (
	QR_CommentId bigint not null auto_increment,
	Comments varchar(255),
	QualityResultId bigint unique,
	primary key (QR_CommentId)
) type=InnoDB;
    
alter table QualityResult_Comment 
	add index FKD36C3ADCCCF6BB41 (QualityResultId), 
	add constraint FKD36C3ADCCCF6BB41 
	foreign key (QualityResultId) 
	references QualityResult (QualityResultId);