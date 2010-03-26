--###########################################################
-- Modifications for the shared repository : import reference
create table Segment (
    SegmentId bigint not null auto_increment,
    Name varchar(255) not null,
    Identifier bigint not null,
    Deprecated bit not null,
    CategoryId bigint not null,
    primary key (SegmentId)
) type=InnoDB;

create table SegmentCategory (
    CategoryId bigint not null auto_increment,
    Name varchar(255) not null unique,
    Identifier bigint not null unique,
    Type varchar(255) not null,
    Deprecated bit not null,
    primary key (CategoryId)
) type=InnoDB;

create table Segment_Module (
    SegmentId bigint not null,
    ComponentId bigint not null,
    primary key (SegmentId, ComponentId)
) type=InnoDB;

create table Segment_Segmentation (
    SegmentationId bigint not null,
    SegmentId bigint not null,
    primary key (SegmentationId, SegmentId)
) type=InnoDB;

create table Segmentation (
    SegmentationId bigint not null auto_increment,
    primary key (SegmentationId)
) type=InnoDB;

    
create table SharedRepoStats (
    StatsId bigint not null auto_increment,
    ElementType varchar(255),
    DataType varchar(255),
    DataName varchar(255),
    Language varchar(255),
    Mean float,
    MaxValue float,
    MinValue float,
    Deviation float,
    Elements integer,
    SegmentationId bigint not null,
    primary key (StatsId)
) type=InnoDB;

create table SqualeParams (
    SqualeParamsId bigint not null auto_increment,
    ParamKey varchar(255) not null,
    ParamaValue varchar(255) not null,
    primary key (SqualeParamsId)
) type=InnoDB;    



alter table Segment 
    add index FKD8DD37132D40D50F (CategoryId), 
    add constraint FKD8DD37132D40D50F 
    foreign key (CategoryId) 
    references SegmentCategory (CategoryId)
    on delete cascade;

alter table Segment_Module 
    add index FK25FDCD381A146766 (SegmentId), 
    add constraint FK25FDCD381A146766 
    foreign key (SegmentId) 
    references Segment (SegmentId)
    on delete cascade;

alter table Segment_Module 
    add index FK25FDCD38D4A7AD7B (ComponentId), 
    add constraint FK25FDCD38D4A7AD7B 
    foreign key (ComponentId) 
    references Component (ComponentId)
    on delete cascade;

alter table Segment_Segmentation 
    add index FK4C70016EBBF32679 (SegmentationId), 
    add constraint FK4C70016EBBF32679 
    foreign key (SegmentationId) 
    references Segmentation (SegmentationId)
    on delete cascade;

alter table Segment_Segmentation 
    add index FK4C70016E1A146766 (SegmentId), 
    add constraint FK4C70016E1A146766 
    foreign key (SegmentId) 
    references Segment (SegmentId)
    on delete cascade;

alter table SharedRepoStats 
    add index FK3C971D48BBF32679 (SegmentationId), 
    add constraint FK3C971D48BBF32679 
    foreign key (SegmentationId) 
    references Segmentation (SegmentationId)
    on delete cascade;
    
    
--###########################################################
-- Modify the squale version
alter table AuditBO alter SQUALE_VERSION set default '6.0' ;

--###########################################################