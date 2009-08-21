-- #####################################################
-- Modification for the manual practice comments

--Add the comments table
create table QualityResult_Comment (
        QR_CommentId number(19,0) not null,
        Comments varchar2(255),
        QualityResultId number(19,0) unique,
	primary key (QR_CommentId)
    );
alter table QualityResult_Comment 
    add constraint FKD36C3ADCCCF6BB41 
    foreign key (QualityResultId) 
    references QualityResult
    on delete cascade;
--Add the sequence
    create sequence qualityres_comment_sequence;