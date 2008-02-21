create table AuditFrequency (
	AuditFrequencyId number(19,0) not null,
	Nb_days number(10,0) not null,
	Frequency number(10,0) not null
);

create sequence auditFrequency_sequence;

alter table AuditFrequency add constraints pk_AuditFrequency primary key (AuditFrequencyId) using index tablespace squale_ind;

