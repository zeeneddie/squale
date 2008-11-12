-- #####################################################
-- For the MySQL support
-- Need at least Oracle 9

-- table ProjectParameter
alter table ProjectParameter rename column key to IndexKey;

-- Table News
alter table News rename column key to NewsKey;

-- Table Message
alter table Message drop primary key;
alter table Message rename column key to MessageKey;
alter table Message add constraint pk_messages primary key(MessageKey,lang);


-- #####################################################
-- For save the mail configuration in the database

-- Create the table adminParamsBO
create table adminParams (
        AdminParamsId number(19,0) not null,
        paramKey varchar2(255) not null,
        paramaValue varchar2(255) not null,
        primary key (AdminParamsId)
    );

-- Create the adminParams sequence
create sequence adminparams_sequence;