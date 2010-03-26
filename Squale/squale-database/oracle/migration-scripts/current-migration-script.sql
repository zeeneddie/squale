--###########################################################
-- Modifications for the shared repository

create table Segment (
    SegmentId number(19,0) not null,
    Name varchar2(255) not null,
    Identifier number(19,0) not null,
    Deprecated number(1,0) not null,
    CategoryId number(19,0) not null,
    primary key (SegmentId)
);

create table SegmentCategory (
    CategoryId number(19,0) not null,
    Name varchar2(255) not null unique,
    Identifier number(19,0) not null unique,
    Type varchar2(255) not null,
    Deprecated number(1,0) not null,
    primary key (CategoryId)
);

create table Segment_Module (
    SegmentId number(19,0) not null,
    ComponentId number(19,0) not null,
    primary key (SegmentId, ComponentId)
);

create table Segment_Segmentation (
    SegmentationId number(19,0) not null,
    SegmentId number(19,0) not null,
    primary key (SegmentationId, SegmentId)
);

create table Segmentation (
    SegmentationId number(19,0) not null,
    primary key (SegmentationId)
);

create table SharedRepoStats (
    StatsId number(19,0) not null,
    ElementType varchar2(255),
    DataType varchar2(255),
    DataName varchar2(255),
    Language varchar2(255),
    Mean float,
    MaxValue float,
    MinValue float,
    Deviation float,
    Elements number(10,0),
    SegmentationId number(19,0) not null,
    primary key (StatsId)
);

create table SqualeParams (
    SqualeParamsId number(19,0) not null,
    ParamKey varchar2(255) not null,
    ParamaValue varchar2(255) not null,
    primary key (SqualeParamsId)
);

alter table Segment 
    add constraint FKD8DD37132D40D50F 
    foreign key (CategoryId) 
    references SegmentCategory
    on delete cascade;

alter table Segment_Module 
    add constraint FK25FDCD381A146766 
    foreign key (SegmentId) 
    references Segment
    on delete cascade;

alter table Segment_Module 
    add constraint FK25FDCD38D4A7AD7B 
    foreign key (ComponentId) 
    references Component
    on delete cascade;

alter table Segment_Segmentation 
    add constraint FK4C70016EBBF32679 
    foreign key (SegmentationId) 
    references Segmentation
    on delete cascade;

alter table Segment_Segmentation 
    add constraint FK4C70016E1A146766 
    foreign key (SegmentId) 
    references Segment
    on delete cascade;

alter table SharedRepoStats 
    add constraint FK3C971D48BBF32679 
    foreign key (SegmentationId) 
    references Segmentation
    on delete cascade;

create sequence segmentCategory_sequence;

create sequence segment_sequence;

create sequence segmentation_sequence;

create sequence sharedRepoStats_sequence;

create sequence squaleParams_sequence;
    
--###########################################################
-- Modify the squale version
alter table AUDITBO modify SQUALE_VERSION default '6.0' ;

--###########################################################