-- Script de création d'une nouvelle base  
create table AtomicRights (
   AtomicRightsId number(19,0) not null,
   Name varchar2(255)
);
create table User_Rights (
   UserId number(19,0) not null,
   ProfileId number(19,0) not null,
   ApplicationId number(19,0) not null
);
create table UserBO (
   UserId number(19,0) not null,
   FullName varchar2(255),
   Matricule varchar2(255),
   Password varchar2(255),
   Email varchar2(255),
   ProfileId number(19,0) not null
);
create table PortailConf (
   PortailConfId number(19,0) not null
);
create table QualityRule_Coefficients (
   QualityRuleId number(19,0) not null,
   Coefficients_value float,
   Coefficients number(10,0) not null
);
create table PracticeResult_Repartition (
   PracticeResultId number(19,0) not null,
   Repartition_value number(10,0),
   Repartition number(10,0) not null
);
create table CriteriumPractice_Rule (
   CriteriumRuleId number(19,0) not null,
   PracticeRuleId number(19,0) not null
);
create table Profile_Rights (
   ProfileId number(19,0) not null,
   Rights_Value varchar2(255),
   AtomicRightsId number(19,0) not null
);
create table QualityRule (
   QualityRuleId number(19,0) not null,
   subclass varchar2(255) not null,
   DateOfCreation date not null
);
create table SqualeReference (
   ReferencielId number(19,0) not null,
   PublicApplication number(1,0),
   ApplicationName varchar2(255),
   ProjectName varchar2(255),
   ProjectLanguage varchar2(255),
   Version varchar2(255),
   AuditDate date,
   CodeLineNumber number(10,0),
   MethodNumber number(10,0),
   ClassNumber number(10,0),
   Validated number(1,0)
);
create table PortailConf_parameters (
   PortailConfId number(19,0) not null,
   param_Value long raw not null,
   param_Key varchar2(255) not null
);
create table Project_QualRule (
   ProjectId number(19,0) not null,
   QualityRuleId number(19,0) not null
);

-- table partitionnée ==> ajout de la date_calcul
CREATE TABLE METRIC
(
  METRICID     NUMBER(19)                       NOT NULL,
  SUBCLASS     VARCHAR2(255 BYTE)               NOT NULL,
  NAME         VARCHAR2(255 BYTE),
  MEASUREID    NUMBER(19),
  STRING_VAL   VARCHAR2(4000 BYTE),
  BOOLEAN_VAL  NUMBER(1),
  BLOB_VAL     BLOB,
  INTEGER_VAL  NUMBER(10),
  DATE_CALCUL  DATE		 DEFAULT SYSDATE		NOT NULL
)
PARTITION BY RANGE (DATE_CALCUL) 
(  
  PARTITION P1 VALUES LESS THAN (TO_DATE(' 2006-06-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    TABLESPACE SQUALE_TSD_P1,  
  PARTITION P2 VALUES LESS THAN (TO_DATE(' 2006-08-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    TABLESPACE SQUALE_TSD_P2,
  PARTITION P3 VALUES LESS THAN (TO_DATE(' 2006-10-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    TABLESPACE SQUALE_TSD_P3
);

create table Component (
   AbstractComponentId number(19,0) not null,
   subclass varchar2(255) not null,
   Name varchar2(1024) not null,
   Parent number(19,0),
   AuditFrequency number(10,0),
   ResultsStorageOptions number(10,0),
   Status number(10,0),
   Site varchar2(255),
   PublicApplication number(1,0),
   LastConsultation date,
   ProfileBO number(19,0),
   Location varchar2(255),
   LongFileName varchar2(2048)
);
create table Components_Audits (
   ComponentId number(19,0) not null,
   AuditId number(19,0) not null
);
create table Mark (
   MarkId number(19,0) not null,
   Value number(10,0) not null,
   ComponentId number(19,0) not null,
   PracticeResultId number(19,0) not null
);
create table Project_parameters (
   ProjectId number(19,0) not null,
   param_Value varchar2(4000),
   param_Key varchar2(255) not null
);
create table Measure (
   MeasureId number(19,0) not null,
   subclass varchar2(255) not null,
   TaskName varchar2(255),
   AuditId number(19,0) not null,
   ComponentId number(19,0) not null
);
create table ProfileBO (
   ProfileId number(19,0) not null,
   Name varchar2(255)
);
create table QualityResult (
   QualityResultId number(19,0) not null,
   subclass varchar2(255) not null,
   QualityRuleId number(19,0) not null,
   MeanMark float not null,
   ProjectId number(19,0) not null,
   AuditId number(19,0) not null
);
create table FactorCriterium_Rule (
   FactorRuleId number(19,0) not null,
   CriteriumRuleId number(19,0) not null
);
create table AuditBO (
   AuditId number(19,0) not null,
   Name varchar2(255),
   auditDate date,
   auditType varchar2(255),
   Status number(10,0) not null,
   Comments varchar2(255)
);
create table Error (
   ErrorId number(19,0) not null,
   InitialMessage varchar2(2048),
   Message varchar2(2048),
   CriticityLevel varchar2(255),
   TaskName varchar2(255),
   AuditId number(19,0) not null,
   ProjectId number(19,0) not null
);
create table FactorRef (
   ReferencielId number(19,0) not null,
   Factor_Value float,
   Rule number(19,0) not null
);

create table Version (
   VersionId number(19,0) not null,
   FileContent blob,
   Version varchar2(255)
);



create table Rule (
   RuleId number(19,0) not null,
   Category varchar2(255),
   Code varchar2(255),
   
   Severity varchar2(255),
   VersionId number(19,0)
);


create table Module (
   ModuleId number(19,0) not null,
   Message varchar2(255),
   Name varchar2(255),
   RuleId number(19,0)
  
);

create table Analysis_Task (
   TasksUserId number(19,0) not null,
   TaskId number(19,0) not null,
   AnalysisTaskIndex number(10,0) not null,
   primary key (TasksUserId, AnalysisTaskIndex)
);


create table StopTimeBO (
	StopTimeId number(19,0) not null,
    DayOfWeek varchar2(9) not null,
    TimeOfDay varchar2(5) not null,
    primary key (StopTimeId)
);

create table Task (
	TaskId number(19,0) not null,
	Name varchar2(255) not null unique,
	Class varchar2(2048) not null,
	Configurable number(1,0) not null,
	primary key (TaskId)
);

create table Tasks_User (
	AbstractTasksUserId number(19,0) not null,
	subclass varchar2(255) not null,
	Name varchar2(255) not null unique,
	MilestoneAudit number(1,0),
	NormalAudit number(1,0),
	primary key (AbstractTasksUserId)
);

create table Termination_Task (
	TasksUserId number(19,0) not null,
	TaskId number(19,0) not null,
	TerminationTaskIndex number(10,0) not null,
	primary key (TasksUserId, TerminationTaskIndex)
);


-- Ajoute les PK dans le bon tablespace
alter table AtomicRights add constraints pk_AtomicRights primary key (AtomicRightsId) using index tablespace squale_ind;
alter table User_Rights add constraint pk_User_Rights primary key (UserId, ApplicationId) using index tablespace squale_ind;
alter table UserBO add constraint pk_UserBO primary key (UserId) using index tablespace squale_ind;
alter table PortailConf add constraint pk_PortailConf primary key (PortailConfId) using index tablespace squale_ind;
alter table QualityRule_Coefficients add constraint pk_QualityRule_Coefficients primary key (QualityRuleId, Coefficients) using index tablespace squale_ind;
alter table PracticeResult_Repartition add constraint pk_PracticeResult_Repartition primary key (PracticeResultId, Repartition) using index tablespace squale_ind;
alter table Profile_Rights add constraint pk_Profile_Rights primary key (ProfileId, AtomicRightsId) using index tablespace squale_ind;
alter table QualityRule add constraint pk_QualityRule primary key (QualityRuleId) using index tablespace squale_ind;
alter table SqualeReference add constraint pk_SqualeReference primary key (ReferencielId) using index tablespace squale_ind;
alter table PortailConf_parameters add constraint pk_PortailConf_parameters primary key (PortailConfId, param_Key) using index tablespace squale_ind;
alter table Metric add constraint pk_Metric primary key (MetricId) using index tablespace squale_ind;
alter table Component add constraint pk_Component primary key (AbstractComponentId) using index tablespace squale_ind;
alter table Components_Audits add constraint pk_Components_Audits primary key (ComponentId, AuditId) using index tablespace squale_ind;
alter table Mark add constraint pk_Mark primary key (MarkId) using index tablespace squale_ind;
alter table Project_parameters add constraint pk_Project_parameters primary key (ProjectId, param_Key) using index tablespace squale_ind;
alter table Measure add constraint pk_Measure primary key (MeasureId) using index tablespace squale_ind;
alter table ProfileBO add constraint pk_ProfileBO primary key (ProfileId) using index tablespace squale_ind;
alter table QualityResult add constraint pk_QualityResult primary key (QualityResultId) using index tablespace squale_ind;
alter table AuditBO add constraint pk_AuditBO primary key (AuditId) using index tablespace squale_ind;
alter table Error add constraint pk_Error primary key (ErrorId) using index tablespace squale_ind;
alter table FactorRef add constraint pk_FactorRef primary key (ReferencielId, Rule) using index tablespace squale_ind;

alter table Version add constraints pk_Version primary key (VersionId) using index tablespace squale_ind;
alter table Rule add constraints pk_Rule primary key (RuleId) using index tablespace squale_ind;
alter table Module add constraints pk_Module primary key (ModuleId) using index tablespace squale_ind;

alter table Analysis_Task add constraints pk_Analysis_Task primary key (TasksUserId, AnalysisTaskIndex) using index tablespace squale_ind;
alter table StopTimeBO add constraints pk_StopTimeBO primary key (StopTimeId) using index tablespace squale_ind;
alter table Task add constraints pk_Task primary key (TaskId) using index tablespace squale_ind;
alter table Tasks_User add constraints pk_Tasks_User primary key (AbstractTasksUserId) using index tablespace squale_ind;
alter table Termination_User add constraints pk_Termination_User primary key (TasksUserId, TerminationTaskIndex) using index tablespace squale_ind;

-- pk non généré par hibernate
alter table criteriumpractice_rule add (primary key (criteriumruleid,practiceruleid) using index tablespace squale_ind);
alter table factorcriterium_rule add (primary key (factorruleid,criteriumruleid) using index tablespace squale_ind);
alter table project_qualrule add (primary key (projectid,qualityruleid) using index tablespace squale_ind);

-- contrainte en "on delete cascade" pour supprimer rapidement un audit
alter table Metric add constraint FKMetricMeasure foreign key (MeasureId) references Measure on delete cascade; 
alter table Components_Audits add constraint FKAudit foreign key (AuditId) references AuditBO on delete cascade;
alter table Measure add constraint FKMeasureAudit foreign key (AuditId) references AuditBO on delete cascade;
alter table QualityResult add constraint FKQualityResAudit foreign key (AuditId) references AuditBO on delete cascade;
alter table Error add constraint FKErrorAudit foreign key (AuditId) references AuditBO on delete cascade;
alter table PracticeResult_Repartition add constraint FKRepPracticeResult foreign key (PracticeResultId) references QualityResult on delete cascade;
alter table Mark add constraint FKMarkPraticeResult foreign key (PracticeResultId) references QualityResult on delete cascade;
alter table RuleCheckingTransgressionItem add constraint FKMEASUREID foreign key(MeasureId) references Measure on delete cascade;

-- contrainte en "on delete cascade" pour supprimer rapidement un projet
alter table Component add constraint FKParent foreign key (Parent) references Component on delete cascade;
alter table User_Rights add constraint FKRightsApplicationId foreign key (ApplicationId) references Component on delete cascade;
alter table Project_QualRule add constraint FKRuleProject foreign key (ProjectId) references Component on delete cascade;
alter table Project_parameters add constraint FKParamProject foreign key (ProjectId) references Component on delete cascade;

-- renommage des FK
alter table FactorRef add constraint FKFactorRef foreign key (ReferencielId) references SqualeReference;
alter table FactorRef add constraint FKFactorRule foreign key (Rule) references QualityRule;
alter table User_Rights add constraint FKRightsUser foreign key (UserId) references UserBO;
alter table User_Rights add constraint FKRightsProfile foreign key (ProfileId) references ProfileBO;
alter table UserBO add constraint FKUserProfile foreign key (ProfileId) references ProfileBO;
alter table QualityRule_Coefficients add constraint FKCoeffQualityRule foreign key (QualityRuleId) references QualityRule;
alter table CriteriumPractice_Rule add constraint FKPraticeRule foreign key (PracticeRuleId) references QualityRule;
alter table CriteriumPractice_Rule add constraint FKPCriteriumRule foreign key (CriteriumRuleId) references QualityRule;
alter table Profile_Rights add constraint FKProfileAtRigths foreign key (AtomicRightsId) references AtomicRights;
alter table Profile_Rights add constraint FKAtRightsProfile foreign key (ProfileId) references ProfileBO;
alter table PortailConf_parameters add constraint FKPortailConf foreign key (PortailConfId) references PortailConf;
alter table Project_QualRule add constraint FKProjectQaulRule foreign key (QualityRuleId) references QualityRule;
alter table Components_Audits add constraint FKAuditsComponent foreign key (ComponentId) references Component;
alter table Mark add constraint FKMarkComponent foreign key (ComponentId) references Component;
alter table Measure add constraint FKMeasureComponent foreign key (ComponentId) references Component;
alter table QualityResult add constraint FKQualityResSubProj foreign key (ProjectId) references Component;
alter table QualityResult add constraint FKQualityResRule foreign key (QualityRuleId) references QualityRule;
alter table FactorCriterium_Rule add constraint FKFactorCrit foreign key (FactorRuleId) references QualityRule;
alter table FactorCriterium_Rule add constraint FKCritFactor foreign key (CriteriumRuleId) references QualityRule;
alter table Error add constraint FKErrorProject foreign key (ProjectId) references Component;

alter table Rule add constraint FKRuleVersion foreign key (VersionId) references Version;
alter table Module add constraint FKModuleRule foreign key (RuleId) references Rule;

alter table Component add constraint FKProjectProfileId foreign key (ProfileBO) references Tasks_User;
alter table Analysis_Task add constraint FKTasksUserId foreign key (TasksUserId) references Tasks_User;
alter table Analysis_Task add constraint FKTaskId foreign key (TaskId) references Task;
alter table Termination_Task add constraint FKTasksUserId foreign key (TasksUserId) references Tasks_User;
alter table Termination_Task add constraint FKTerminationTaskId foreign key (TaskId) references Task;


REM index sur les FK
create index User_RightsUserBO on User_Rights(UserId) tablespace squale_ind;
create index User_RightsProfileBO on User_Rights(ProfileId) tablespace squale_ind;
create index User_RightsApplication on User_Rights(ApplicationId) tablespace squale_ind;
create index UserBOProfileBO on UserBO(ProfileId) tablespace squale_ind;
create index QualityRule1 on QualityRule_Coefficients(QualityRuleId) tablespace squale_ind;
create index PracticeResult1 on PracticeResult_Repartition(PracticeResultId) tablespace squale_ind;
create index CriteriumPractice1 on CriteriumPractice_Rule(CriteriumRuleId) tablespace squale_ind;
create index CriteriumPractice2 on CriteriumPractice_Rule(PracticeRuleId) tablespace squale_ind;
create index Profile1 on Profile_Rights(AtomicRightsId) tablespace squale_ind;
create index Profile2 on Profile_Rights(ProfileId) tablespace squale_ind;
create index PortailConf1 on PortailConf_parameters(PortailConfId) tablespace squale_ind;
create index ComponentParent on Component(Parent) tablespace squale_ind;
create index MarkPracticeResult on Mark(PracticeResultId) tablespace squale_ind;
create index MarkComponent on Mark(ComponentId) tablespace squale_ind;
create index Project on Project_parameters(ProjectId) tablespace squale_ind;
create index Measure1 on Measure(ComponentId,AuditId,subclass) tablespace squale_ind;
create index Metric1 on Metric(MeasureId,Name) tablespace squale_ind;
create index QualityResult1 on QualityResult(QualityRuleId) tablespace squale_ind;
create index QualityResult2 on QualityResult(AuditId) tablespace squale_ind;
create index QualityResult3 on QualityResult(ProjectId) tablespace squale_ind;
create index ErrorAuditBO on Error(AuditId) tablespace squale_ind;
create index ErrorProject on Error(ProjectId) tablespace squale_ind;


-- une sequences par table !
create sequence measure_sequence;
create sequence portail_sequence;
create sequence profile_sequence;
create sequence error_sequence;
create sequence user_sequence;
create sequence component_sequence;
create sequence qualityres_sequence;
create sequence metric_sequence;
create sequence mark_sequence;
create sequence qualityrule_sequence;
create sequence reference_sequence;
create sequence rigth_sequence;
create sequence audit_sequence;

create sequence Version_sequence;
create sequence Rule_sequence;
create sequence Module_sequence;

create sequence tasksUser_sequence;
create sequence task_sequence;
create sequence stoptime_sequence;
