    create table Tag_Segementation (
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
        max float,
        min float,
        deviation float,
        elements float,
        segmentationId number(19,0) not null,
        primary key (StatsId)
    );

    create table squale_params (
        SqualeParamsId number(19,0) not null,
        paramKey varchar2(255) not null,
        paramaValue varchar2(255) not null,
        primary key (SqualeParamsId)
    );
    
    alter table Tag_Segementation 
        add constraint FK328D3382BBF32679 
        foreign key (segmentationId) 
        references segmentation;

    alter table Tag_Segementation 
        add constraint FK328D3382FD9106F6 
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