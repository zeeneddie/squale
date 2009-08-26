-- #####################################################
-- Modification for the manual practice comments

--Add the comments table
create table QualityResult_Comment (
        QR_CommentId number(19,0) not null,
        Comments varchar2(4000),
        QualityResultId number(19,0) unique,
	primary key (QR_CommentId)
    );
alter table QualityResult_Comment 
    add constraint FKD36C3ADCCCF6BB41 
    foreign key (QualityResultId) 
    references QualityResult;
--Add the sequence
    create sequence qualityres_comment_sequence;

-- Modification for #208
alter table Component add QualityApproachOnStart number(1,0);
alter table Component add InInitialDev number(1,0);
alter table Component add GlobalCost number(10,0);
alter table Component add DevCost number(10,0);
alter table component modify InInitialDev default 1;
alter table component modify QualityApproachOnStart default 1;
alter table component modify GlobalCost default 0;
alter table component modify DevCost default 0;
update Component set InInitialDev=1, QualityApproachOnStart=0 , GlobalCost=0 , DevCost=0;