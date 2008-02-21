-- ATTENTION: NE PAS OUBLIER DE PASSER LE SCRIPT JAVA DE MIGRATION POUR METTRE A JOUR LES ANCIENS AUDITS!!!

-- les configurations
create table displayConf (
	ConfId number(19,0) not null,
    subclass varchar2(255) not null,
	componentType varchar2(255),
	X_TRE varchar2(400),
	Y_TRE varchar2(400),
	X_POS number(19,0),
	Y_POS number(19,0)
);
-- avec sa clé primaire
alter table displayConf add constraints pk_displayConf primary key (confId) using index tablespace squale_ind;
-- sa séquence
create sequence displayconf_sequence;

-- les mesures de la volumétrie
create table Volumetry_Measures (
	VolumetryId number(19,0) not null,
	Measure varchar2(255) not null
);
-- avec sa clé primaire
alter table Volumetry_Measures add constraints pk_volumetryMeasures primary key (VolumetryId, Measure) using index tablespace squale_ind;
-- clé étrangère
alter table Volumetry_Measures add constraint FK_volumetryMeasures_conf foreign key (VolumetryId) references displayConf on delete cascade;


-- le lien profil-configuration
create table Profile_DisplayConfBO (
	ConfId number(19,0) not null,
	Profile_ConfId number(19,0),
	ProfileId number(19,0)
);
-- avec sa clé primaire
alter table Profile_DisplayConfBO add constraints pk_Profile_DisplayConfBO primary key (confId) using index tablespace squale_ind;
-- les clés étrangères
alter table Profile_DisplayConfBO add constraint FK_profileDispalyConf_conf foreign key (Profile_ConfId) references displayConf;
alter table Profile_DisplayConfBO add constraint FK_profileDispalyConf_profile foreign key (ProfileId) references Tasks_User;
-- sa séquence
create sequence profiledisplayconf_sequence;

-- suppression de la colonne "bubble" des profils
alter table Tasks_User drop column bubble_conf_id;

-- suppression de la table "bubble_conf"
drop table bubble_conf;

-- lien avec les audits
create table AuditDisplayConfBO (
	AuditConfId number(19,0) not null,
	ConfId number(19,0),
	ProjectId number(19,0),
	AuditId number(19,0)
);
-- avec sa clé primaire
alter table AuditDisplayConfBO add constraints pk_AuditDisplayConfBO primary key (AuditConfId) using index tablespace squale_ind;
-- les clés étrangères
alter table AuditDisplayConfBO add constraint FK_AuditConf_audit foreign key (AuditId) references AuditBO on delete cascade;
alter table AuditDisplayConfBO add constraint FK_AuditConf_component foreign key (ProjectId) references Component on delete cascade;
alter table AuditDisplayConfBO add constraint FK_AuditConf_conf foreign key (ConfId) references displayConf;
-- la séquence
create sequence auditconf_sequence;