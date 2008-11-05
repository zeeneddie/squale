

-- Create the table adminParamsBO
create table adminParams (
        AdminParamsId number(19,0) not null,
        paramKey varchar2(255) not null,
        paramaValue varchar2(255) not null,
        primary key (AdminParamsId)
    );

-- Create the adminParams sequence
create sequence adminparams_sequence;