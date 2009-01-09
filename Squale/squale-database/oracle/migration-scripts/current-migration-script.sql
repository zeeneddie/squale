-- #####################################################
-- For MySQL support
-- Needs at least Oracle 9

-- table ProjectParameter
alter table ProjectParameter rename column key to IndexKey;

-- Table News
alter table News rename column key to NewsKey;

-- Table Message
alter table Message drop primary key;
alter table Message rename column key to MessageKey;
alter table Message add constraint pk_messages primary key(MessageKey,lang);
-- #####################################################



-- #####################################################
-- For mail configuration in the database

-- Create the table adminParamsBO
create table adminParams (
        AdminParamsId number(19,0) not null,
        paramKey varchar2(255) not null,
        paramaValue varchar2(255) not null,
        primary key (AdminParamsId)
    );

-- Create the adminParams sequence
create sequence adminparams_sequence;
-- #####################################################



-- #####################################################
-- For homepage customisation

-- Create the HomepageComponent table
create table HomepageComponent (
        HomepageComponentId number(19,0) not null,
        ComponentName varchar2(255) not null,
        UserBO number(19,0) not null,
        ComponentPosition number(10,0) not null,
        ComponentValue varchar2(255),
        primary key (HomepageComponentId)
    );
	
-- Create the constrain 
alter table HomepageComponent 
        add constraint FK8D93B88FFDF902BC 
        foreign key (UserBO) 
        references UserBO;
		
-- Create the sequence
create sequence HomepageComponent_sequence;
-- #####################################################

