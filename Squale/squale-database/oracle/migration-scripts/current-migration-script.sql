--###########################################################
-- Modifications for the shared repository : import reference

    create table Tag_Segmentation (
        segmentationId number(19,0) not null,
        TagId number(19,0) not null,
        primary key (segmentationId, TagId)
    );
    
    create table segmentation (
        segmentationId number(19,0) not null,
        primary key (segmentationId)
    );

    create table shared_repo_stats (
        StatsId number(19,0) not null,
        elementType varchar2(255),
        dataType varchar2(255),
        dataName varchar2(255),
        language varchar2(255),
        mean float,
        maxValue float,
        minValue float,
        deviation float,
        elements number(10,0),
        segmentationId number(19,0) not null,
        primary key (StatsId)
    );

    create table squale_params (
        SqualeParamsId number(19,0) not null,
        paramKey varchar2(255) not null,
        paramaValue varchar2(255) not null,
        primary key (SqualeParamsId)
    );
    
	alter table Tag_Segmentation 
        add constraint FK9CA21067BBF32679 
        foreign key (segmentationId) 
        references segmentation;

    alter table Tag_Segmentation 
        add constraint FK9CA21067FD9106F6 
        foreign key (TagId) 
        references Tag
        on delete cascade;


    alter table shared_repo_stats 
        add constraint FK2F1F0DACBBF32679 
        foreign key (segmentationId) 
        references segmentation
        on delete cascade;

    create sequence segmentation_sequence;
        
    create sequence shared_repo_stats_sequence;

    create sequence squaleparams_sequence;
    
--###########################################################