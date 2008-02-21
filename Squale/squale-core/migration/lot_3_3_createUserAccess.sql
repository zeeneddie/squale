create table UserAccess (
	UserAccessId number(19,0) not null,
    accessDate date not null,
    matricule varchar2(1024) not null,
    ApplicationId number(19,0),
    AccessIndex number(10,0)
);

-- Clé étrangère
alter table UserAccess add constraint FKUserAccessApplication foreign key (ApplicationId) references Component;

-- Ajoute la PK dans le bon tablespace
alter table UserAccess add constraints pk_UserAccess primary key (UserAccessId) using index tablespace squale_ind;

-- Index sur la clé étrangère
create index UserAccess_Component on UserAccess(ApplicationId) tablespace squale_ind;

-- Séquence pour la table
create sequence userAccess_sequence;