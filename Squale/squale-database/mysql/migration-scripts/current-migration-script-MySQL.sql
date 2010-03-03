--###########################################################
-- Modifications for the shared repository : import reference

create table Tag_Segmentation (
    segmentationId bigint not null,
    TagId bigint not null,
    primary key (segmentationId, TagId)
) type=InnoDB;


create table segmentation (
    segmentationId bigint not null auto_increment,
    primary key (segmentationId)
) type=InnoDB;

create table shared_repo_stats (
    StatsId bigint not null auto_increment,
    elementType varchar(255),
    dataType varchar(255),
    dataName varchar(255),
    language varchar(255),
    mean float,
    maxValue float,
    minValue float,
    deviation float,
    elements integer,
    segmentationId bigint not null,
    primary key (StatsId)
) type=InnoDB;

create table squale_params (
    SqualeParamsId bigint not null auto_increment,
    paramKey varchar(255) not null,
    paramaValue varchar(255) not null,
    primary key (SqualeParamsId)
) type=InnoDB;

alter table Tag_Segmentation 
    add index FK9CA21067BBF32679 (segmentationId), 
    add constraint FK9CA21067BBF32679 
    foreign key (segmentationId) 
    references segmentation (segmentationId);

alter table Tag_Segmentation 
    add index FK9CA21067FD9106F6 (TagId), 
    add constraint FK9CA21067FD9106F6 
    foreign key (TagId) 
    references Tag (TagId)
    on delete cascade;

alter table shared_repo_stats 
    add index FK2F1F0DACBBF32679 (segmentationId), 
    add constraint FK2F1F0DACBBF32679 
    foreign key (segmentationId) 
    references segmentation (segmentationId)
    on delete cascade;
    
--###########################################################