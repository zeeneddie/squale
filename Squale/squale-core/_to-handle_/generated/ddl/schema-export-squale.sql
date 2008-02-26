
    drop table Analysis_Task cascade constraints;

    drop table AtomicRights cascade constraints;

    drop table AuditBO cascade constraints;

    drop table AuditDisplayConfBO cascade constraints;

    drop table AuditFrequency cascade constraints;

    drop table AuditGridBO cascade constraints;

    drop table Component cascade constraints;

    drop table Components_Audits cascade constraints;

    drop table CriteriumPractice_Rule cascade constraints;

    drop table Error cascade constraints;

    drop table FactorCriterium_Rule cascade constraints;

    drop table FactorRef cascade constraints;

    drop table Formula cascade constraints;

    drop table Formula_Conditions cascade constraints;

    drop table Formula_Measures cascade constraints;

    drop table GridFactor_Rule cascade constraints;

    drop table Mark cascade constraints;

    drop table Measure cascade constraints;

    drop table Message cascade constraints;

    drop table Metric cascade constraints;

    drop table Module cascade constraints;

    drop table News cascade constraints;

    drop table PracticeResult_Repartition cascade constraints;

    drop table ProfileBO cascade constraints;

    drop table Profile_DisplayConfBO cascade constraints;

    drop table Profile_Rights cascade constraints;

    drop table Profiles_Grids cascade constraints;

    drop table ProjectParameter cascade constraints;

    drop table QualityGrid cascade constraints;

    drop table QualityResult cascade constraints;

    drop table QualityRule cascade constraints;

    drop table Rule cascade constraints;

    drop table RuleCheckingTransgressionItem cascade constraints;

    drop table RuleSet cascade constraints;

    drop table Serveur cascade constraints;

    drop table SqualeReference cascade constraints;

    drop table Stats_squale_dict cascade constraints;

    drop table Stats_squale_dict_annexe cascade constraints;

    drop table StopTimeBO cascade constraints;

    drop table Task cascade constraints;

    drop table TaskParameter cascade constraints;

    drop table TaskRef cascade constraints;

    drop table Tasks_User cascade constraints;

    drop table Termination_Task cascade constraints;

    drop table UserAccess cascade constraints;

    drop table UserBO cascade constraints;

    drop table User_Rights cascade constraints;

    drop table Volumetry_Measures cascade constraints;

    drop table displayConf cascade constraints;

    drop sequence Module_sequence;

    drop sequence RuleSet_sequence;

    drop sequence Rule_sequence;

    drop sequence TransgressionItem_sequence;

    drop sequence auditFrequency_sequence;

    drop sequence audit_sequence;

    drop sequence auditconf_sequence;

    drop sequence auditgrid_sequence;

    drop sequence component_sequence;

    drop sequence displayconf_sequence;

    drop sequence error_sequence;

    drop sequence formula_sequence;

    drop sequence mark_sequence;

    drop sequence measure_sequence;

    drop sequence metric_sequence;

    drop sequence news_sequence;

    drop sequence profile_sequence;

    drop sequence profiledisplayconf_sequence;

    drop sequence project_parameter_sequence;

    drop sequence qualitygrid_sequence;

    drop sequence qualityres_sequence;

    drop sequence qualityrule_sequence;

    drop sequence reference_sequence;

    drop sequence rigth_sequence;

    drop sequence stats_annexe_sequence;

    drop sequence stats_sequence;

    drop sequence stoptime_sequence;

    drop sequence taskParameter_sequence;

    drop sequence taskRef_sequence;

    drop sequence task_sequence;

    drop sequence tasksUser_sequence;

    drop sequence userAccess_sequence;

    drop sequence user_sequence;

    create table Analysis_Task (
        TasksUserId number(19,0) not null,
        TaskRefId number(19,0) not null,
        AnalysisTaskIndex number(10,0) not null,
        primary key (TasksUserId, AnalysisTaskIndex)
    );

    create table AtomicRights (
        AtomicRightsId number(19,0) not null,
        Name varchar2(255),
        primary key (AtomicRightsId)
    );

    create table AuditBO (
        AuditId number(19,0) not null,
        Name varchar2(255),
        auditDate date,
        auditType varchar2(255),
        Status number(10,0) not null,
        Comments varchar2(255),
        historicalDate date,
        Duration varchar2(255),
        END_DATE date,
        MAX_FILE_SYSTEM_SIZE number(19,0),
        BEGINNING_DATE date,
        squale_version double precision,
        primary key (AuditId)
    );

    create table AuditDisplayConfBO (
        AuditConfId number(19,0) not null,
        ConfId number(19,0),
        ProjectId number(19,0),
        AuditId number(19,0),
        primary key (AuditConfId)
    );

    create table AuditFrequency (
        AuditFrequencyId number(19,0) not null,
        Nb_days number(10,0) not null,
        Frequency number(10,0) not null,
        primary key (AuditFrequencyId)
    );

    create table AuditGridBO (
        AuditGridId number(19,0) not null,
        QualityGridId number(19,0),
        ProjectId number(19,0),
        AuditId number(19,0),
        primary key (AuditGridId)
    );

    create table Component (
        ComponentId number(19,0) not null,
        subclass varchar2(255) not null,
        Excluded number(1,0) not null,
        Justification varchar2(4000),
        Name varchar2(1024) not null,
        Parent number(19,0),
        ProjectId number(19,0),
        LongFileName varchar2(2048),
        StartLine number(10,0),
        AuditFrequency number(10,0),
        ResultsStorageOptions number(10,0),
        Status number(10,0),
        PublicApplication number(1,0),
        LastUpdate date,
        EXTERNAL_DEV number(1,0),
        IN_PRODUCTION number(1,0),
        lastUser varchar2(1024),
        Serveur number(19,0),
        ProfileBO number(19,0),
        ParametersSet number(19,0),
        QualityGrid number(19,0),
        SourceManager number(19,0),
        primary key (ComponentId)
    );

    create table Components_Audits (
        ComponentId number(19,0) not null,
        AuditId number(19,0) not null,
        primary key (ComponentId, AuditId)
    );

    create table CriteriumPractice_Rule (
        CriteriumRuleId number(19,0) not null,
        Weight float not null,
        PracticeRuleId number(19,0) not null,
        primary key (CriteriumRuleId, PracticeRuleId)
    );

    create table Error (
        ErrorId number(19,0) not null,
        InitialMessage varchar2(2048),
        Message varchar2(2048),
        CriticityLevel varchar2(255),
        TaskName varchar2(255),
        AuditId number(19,0) not null,
        ProjectId number(19,0) not null,
        primary key (ErrorId)
    );

    create table FactorCriterium_Rule (
        FactorRuleId number(19,0) not null,
        Weight float not null,
        CriteriumRuleId number(19,0) not null,
        primary key (FactorRuleId, CriteriumRuleId)
    );

    create table FactorRef (
        ReferencielId number(19,0) not null,
        Factor_Value float,
        Rule number(19,0) not null,
        primary key (ReferencielId, Rule)
    );

    create table Formula (
        FormulaId number(19,0) not null,
        subclass varchar2(255) not null,
        ComponentLevel varchar2(255),
        TriggerCondition varchar2(4000),
        Formula varchar2(4000),
        primary key (FormulaId)
    );

    create table Formula_Conditions (
        FormulaId number(19,0) not null,
        Value varchar2(4000),
        Rank number(10,0) not null,
        primary key (FormulaId, Rank)
    );

    create table Formula_Measures (
        FormulaId number(19,0) not null,
        Measure varchar2(255)
    );

    create table GridFactor_Rule (
        QualityGridId number(19,0) not null,
        FactorRuleId number(19,0) not null,
        primary key (QualityGridId, FactorRuleId)
    );

    create table Mark (
        MarkId number(19,0) not null,
        Value float not null,
        ComponentId number(19,0) not null,
        PracticeResultId number(19,0) not null,
        primary key (MarkId)
    );

    create table Measure (
        MeasureId number(19,0) not null,
        subclass varchar2(255) not null,
        TaskName varchar2(255),
        AuditId number(19,0) not null,
        ComponentId number(19,0) not null,
        RuleSetId number(19,0),
        primary key (MeasureId)
    );

    create table Message (
        key varchar2(255) not null,
        lang varchar2(6) not null,
        Title varchar2(4000),
        Text varchar2(4000) not null,
        primary key (key, lang)
    );

    create table Metric (
        MetricId number(19,0) not null,
        subclass varchar2(255) not null,
        Name varchar2(255),
        MeasureId number(19,0),
        Boolean_val number(1,0),
        String_val varchar2(255),
        Blob_val blob,
        Number_val float,
        primary key (MetricId)
    );

    create table Module (
        ModuleId number(19,0) not null,
        Message varchar2(255),
        Name varchar2(255),
        RuleId number(19,0) not null,
        primary key (ModuleId)
    );

    create table News (
        Id number(19,0) not null,
        Key varchar2(4000) not null,
        Beginning_Date date not null,
        End_Date date not null,
        primary key (Id)
    );

    create table PracticeResult_Repartition (
        PracticeResultId number(19,0) not null,
        Repartition_value number(10,0),
        Repartition number(10,0) not null,
        primary key (PracticeResultId, Repartition)
    );

    create table ProfileBO (
        ProfileId number(19,0) not null,
        Name varchar2(255),
        primary key (ProfileId)
    );

    create table Profile_DisplayConfBO (
        ConfId number(19,0) not null,
        Profile_ConfId number(19,0),
        ProfileId number(19,0),
        primary key (ConfId)
    );

    create table Profile_Rights (
        ProfileId number(19,0) not null,
        Rights_Value varchar2(255),
        AtomicRightsId number(19,0) not null,
        primary key (ProfileId, AtomicRightsId)
    );

    create table Profiles_Grids (
        GridId number(19,0) not null,
        ProfileId number(19,0) not null,
        primary key (ProfileId, GridId)
    );

    create table ProjectParameter (
        ParameterId number(19,0) not null,
        subclass varchar2(255) not null,
        Value varchar2(255),
        MapId number(19,0),
        Key varchar2(255),
        ListId number(19,0),
        Rank number(10,0),
        primary key (ParameterId)
    );

    create table QualityGrid (
        QualityGridId number(19,0) not null,
        Name varchar2(255) not null,
        DateOfUpdate date not null,
        primary key (QualityGridId)
    );

    create table QualityResult (
        QualityResultId number(19,0) not null,
        subclass varchar2(255) not null,
        QualityRuleId number(19,0) not null,
        MeanMark float not null,
        ProjectId number(19,0) not null,
        AuditId number(19,0) not null,
        primary key (QualityResultId)
    );

    create table QualityRule (
        QualityRuleId number(19,0) not null,
        subclass varchar2(255) not null,
        Help_Key varchar2(255),
        Name varchar2(255) not null,
        DateOfCreation date not null,
        Formula number(19,0),
        WeightFunction varchar2(255),
        effort number(10,0),
        primary key (QualityRuleId)
    );

    create table Rule (
        RuleId number(19,0) not null,
        subclass varchar2(255) not null,
        Category varchar2(255),
        Code varchar2(255),
        RuleSetId number(19,0) not null,
        Severity varchar2(255),
        primary key (RuleId)
    );

    create table RuleCheckingTransgressionItem (
        ItemId number(19,0) not null,
        Line number(10,0) not null,
        Path varchar2(3000),
        Message varchar2(3000) not null,
        ComponentId number(19,0),
        RuleId number(19,0) not null,
        ComponentInvolvedId number(19,0),
        MeasureId number(19,0),
        primary key (ItemId)
    );

    create table RuleSet (
        RuleSetId number(19,0) not null,
        subclass varchar2(255) not null,
        Name varchar2(255),
        DateOfUpdate date not null,
        ProjectId number(19,0),
        FileContent blob,
        Language varchar2(255),
        CppTestName varchar2(255) unique,
        primary key (RuleSetId)
    );

    create table Serveur (
        ServeurId number(19,0) not null,
        Name varchar2(256) not null unique,
        primary key (ServeurId)
    );

    create table SqualeReference (
        ReferencielId number(19,0) not null,
        QualityGrid number(19,0),
        PublicApplication number(1,0),
        ApplicationName varchar2(255),
        ProjectName varchar2(255),
        ProjectLanguage varchar2(255),
        Version varchar2(255),
        AuditDate date,
        CodeLineNumber number(10,0),
        MethodNumber number(10,0),
        ClassNumber number(10,0),
        HIDDEN number(1,0) not null,
        AUDIT_TYPE varchar2(50) not null,
        primary key (ReferencielId)
    );

    create table Stats_squale_dict (
        Id number(19,0) not null,
        NB_FACTEURS_ACCEPTES number(10,0) not null,
        NB_FACTEURS_ACCEPTES_RESERVES number(10,0) not null,
        NB_FACTEURS_REFUSES number(10,0) not null,
        NB_TOTAL_APPLI number(10,0) not null,
        NB_APPLI_AVEC_AUDIT_EXECUTE number(10,0) not null,
        NB_APPLI_AVEC_AUDIT_REUSSI number(10,0) not null,
        Date_calcul date not null,
        ROI_EN_MHI double precision not null,
        NB_APPLI_A_VALIDER number(10,0) not null,
        NB_AUDITS_ECHECS number(10,0) not null,
        NB_AUDITS_PROGRAMME number(10,0) not null,
        NB_AUDITS_PARTIELS number(10,0) not null,
        NB_AUDITS_REUSSIS number(10,0) not null,
        NB_APPLI_AVEC_AUCUN_AUDIT number(10,0) not null,
        Serveur number(19,0),
        primary key (Id)
    );

    create table Stats_squale_dict_annexe (
        Id number(19,0) not null,
        NB_LIGNES number(10,0) not null,
        Profil varchar2(4000) not null,
        NB_PROJETS number(10,0) not null,
        Serveur number(19,0),
        primary key (Id)
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
        Standard number(1,0) not null,
        Mandatory number(1,0) not null,
        primary key (TaskId)
    );

    create table TaskParameter (
        TaskParameterId number(19,0) not null,
        Name varchar2(255) not null,
        Value varchar2(255) not null,
        TaskRefId number(19,0),
        primary key (TaskParameterId)
    );

    create table TaskRef (
        TaskRefId number(19,0) not null,
        TaskId number(19,0),
        primary key (TaskRefId)
    );

    create table Tasks_User (
        AbstractTasksUserId number(19,0) not null,
        subclass varchar2(255) not null,
        Name varchar2(255) not null unique,
        MilestoneAudit number(1,0),
        NormalAudit number(1,0),
        export_IDE number(1,0),
        primary key (AbstractTasksUserId)
    );

    create table Termination_Task (
        TasksUserId number(19,0) not null,
        TaskRefId number(19,0) not null,
        TerminationTaskIndex number(10,0) not null,
        primary key (TasksUserId, TerminationTaskIndex)
    );

    create table UserAccess (
        UserAccessId number(19,0) not null,
        ApplicationId number(19,0) not null,
        accessDate date not null,
        matricule varchar2(1024) not null,
        AccessIndex number(10,0),
        primary key (UserAccessId)
    );

    create table UserBO (
        UserId number(19,0) not null,
        FullName varchar2(255),
        Matricule varchar2(255),
        Password varchar2(255),
        Email varchar2(255),
        ProfileId number(19,0) not null,
        unsubscribed number(1,0),
        primary key (UserId)
    );

    create table User_Rights (
        UserId number(19,0) not null,
        ProfileId number(19,0) not null,
        ApplicationId number(19,0) not null,
        primary key (UserId, ApplicationId)
    );

    create table Volumetry_Measures (
        VolumetryId number(19,0) not null,
        Measure varchar2(255) not null,
        primary key (VolumetryId, Measure)
    );

    create table displayConf (
        ConfId number(19,0) not null,
        subclass varchar2(255) not null,
        componentType varchar2(255),
        X_TRE varchar2(400),
        Y_TRE varchar2(400),
        X_POS number(19,0),
        Y_POS number(19,0),
        primary key (ConfId)
    );

    alter table Analysis_Task 
        add constraint FK91CAC908A096529F 
        foreign key (TaskRefId) 
        references TaskRef;

    alter table Analysis_Task 
        add constraint FK91CAC908BE3D10F3 
        foreign key (TasksUserId) 
        references Tasks_User;

    alter table AuditDisplayConfBO 
        add constraint FKBF5515D87E8A4226 
        foreign key (AuditId) 
        references AuditBO;

    alter table AuditDisplayConfBO 
        add constraint FKBF5515D883596FA2 
        foreign key (ProjectId) 
        references Component;

    alter table AuditDisplayConfBO 
        add constraint FKBF5515D873586D71 
        foreign key (ConfId) 
        references displayConf;

    alter table AuditGridBO 
        add constraint FK71AB79AE7E8A4226 
        foreign key (AuditId) 
        references AuditBO;

    alter table AuditGridBO 
        add constraint FK71AB79AE83596FA2 
        foreign key (ProjectId) 
        references Component;

    alter table AuditGridBO 
        add constraint FK71AB79AED2B0987 
        foreign key (QualityGridId) 
        references QualityGrid;

    alter table Component 
        add constraint FK24013CDDF53F33A0 
        foreign key (Serveur) 
        references Serveur;

    alter table Component 
        add constraint FK24013CDD49BB62A2 
        foreign key (SourceManager) 
        references Tasks_User;

    alter table Component 
        add constraint FK24013CDD76518568 
        foreign key (ProjectId) 
        references Component;

    alter table Component 
        add constraint FK24013CDD649B6A3E 
        foreign key (ParametersSet) 
        references ProjectParameter;

    alter table Component 
        add constraint FK24013CDD78D3A978 
        foreign key (ProfileBO) 
        references Tasks_User;

    alter table Component 
        add constraint FK24013CDDD0519AC 
        foreign key (QualityGrid) 
        references QualityGrid;

    alter table Component 
        add constraint FK24013CDD7052981E 
        foreign key (Parent) 
        references Component;

    alter table Components_Audits 
        add constraint FK5D3B01417E8A4226 
        foreign key (AuditId) 
        references AuditBO;

    alter table Components_Audits 
        add constraint FK5D3B014163D3E5E8 
        foreign key (ComponentId) 
        references Component;

    alter table CriteriumPractice_Rule 
        add constraint FK8ADDE86E3A648E9 
        foreign key (CriteriumRuleId) 
        references QualityRule;

    alter table CriteriumPractice_Rule 
        add constraint FK8ADDE86BEFBA921 
        foreign key (PracticeRuleId) 
        references QualityRule;

    alter table Error 
        add constraint FK401E1E87E8A4226 
        foreign key (AuditId) 
        references AuditBO;

    alter table Error 
        add constraint FK401E1E883596FA2 
        foreign key (ProjectId) 
        references Component;

    alter table FactorCriterium_Rule 
        add constraint FK89734030CFD14A09 
        foreign key (FactorRuleId) 
        references QualityRule;

    alter table FactorCriterium_Rule 
        add constraint FK89734030E3A648E9 
        foreign key (CriteriumRuleId) 
        references QualityRule;

    alter table FactorRef 
        add constraint FK2854B0A42CC32BDC 
        foreign key (ReferencielId) 
        references SqualeReference;

    alter table FactorRef 
        add constraint FK2854B0A44AD989DF 
        foreign key (Rule) 
        references QualityRule;

    alter table Formula_Conditions 
        add constraint FKB3141771A528EE84 
        foreign key (FormulaId) 
        references Formula;

    alter table Formula_Measures 
        add constraint FK7A19C1CE1DADC1A7 
        foreign key (FormulaId) 
        references Formula;

    alter table GridFactor_Rule 
        add constraint FKBC5A70C6CFD14A09 
        foreign key (FactorRuleId) 
        references QualityRule;

    alter table GridFactor_Rule 
        add constraint FKBC5A70C6D2B0987 
        foreign key (QualityGridId) 
        references QualityGrid;

    alter table Mark 
        add constraint FK247AED4B98962 
        foreign key (PracticeResultId) 
        references QualityResult;

    alter table Mark 
        add constraint FK247AED63D3E5E8 
        foreign key (ComponentId) 
        references Component;

    alter table Measure 
        add constraint FK9B263D3E7E8A4226 
        foreign key (AuditId) 
        references AuditBO;

    alter table Measure 
        add constraint FK9B263D3EE5BC5FE3 
        foreign key (RuleSetId) 
        references RuleSet;

    alter table Measure 
        add constraint FK9B263D3E63D3E5E8 
        foreign key (ComponentId) 
        references Component;

    alter table Metric 
        add constraint FK892AE1D02FA052BA 
        foreign key (MeasureId) 
        references Measure;

    alter table Module 
        add constraint FK89B0928C612DF65D 
        foreign key (RuleId) 
        references Rule;

    alter table PracticeResult_Repartition 
        add constraint FK84B2F9904B98962 
        foreign key (PracticeResultId) 
        references QualityResult;

    alter table Profile_DisplayConfBO 
        add constraint FKC122A97D802460C7 
        foreign key (Profile_ConfId) 
        references displayConf;

    alter table Profile_DisplayConfBO 
        add constraint FKC122A97D78D3AA46 
        foreign key (ProfileId) 
        references Tasks_User;

    alter table Profile_Rights 
        add constraint FK62F82D6D9B681818 
        foreign key (AtomicRightsId) 
        references AtomicRights;

    alter table Profile_Rights 
        add constraint FK62F82D6D75D5ECAE 
        foreign key (ProfileId) 
        references ProfileBO;

    alter table Profiles_Grids 
        add constraint FK9949B158E49543C8 
        foreign key (GridId) 
        references QualityGrid;

    alter table Profiles_Grids 
        add constraint FK9949B15878D3AA46 
        foreign key (ProfileId) 
        references Tasks_User;

    alter table ProjectParameter 
        add constraint FK6D7538B0AA5FCCA5 
        foreign key (ListId) 
        references ProjectParameter;

    alter table ProjectParameter 
        add constraint FK6D7538B081587B7D 
        foreign key (MapId) 
        references ProjectParameter;

    alter table QualityResult 
        add constraint FKE9E7A9DC7E8A4226 
        foreign key (AuditId) 
        references AuditBO;

    alter table QualityResult 
        add constraint FKE9E7A9DC83596FA2 
        foreign key (ProjectId) 
        references Component;

    alter table QualityResult 
        add constraint FKE9E7A9DC330CF5F3 
        foreign key (QualityRuleId) 
        references QualityRule;

    alter table QualityRule 
        add constraint FK420ADC7B883C760C 
        foreign key (Formula) 
        references Formula;

    alter table Rule 
        add constraint FK270B1CE5BC5FE3 
        foreign key (RuleSetId) 
        references RuleSet;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D4973B5ECF9F1 
        foreign key (RuleId) 
        references Rule;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D4973B26EE3F8 
        foreign key (MeasureId) 
        references Measure;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D497363D3E5E8 
        foreign key (ComponentId) 
        references Component;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D4973C617EA6F 
        foreign key (ComponentInvolvedId) 
        references Component;

    alter table RuleSet 
        add constraint FKBF8713A683596FA2 
        foreign key (ProjectId) 
        references Component;

    alter table SqualeReference 
        add constraint FK32FD7E08D0519AC 
        foreign key (QualityGrid) 
        references QualityGrid;

    alter table Stats_squale_dict 
        add constraint FK9B3A9E52F53F33A0 
        foreign key (Serveur) 
        references Serveur;

    alter table Stats_squale_dict_annexe 
        add constraint FKBC8FB71EF53F33A0 
        foreign key (Serveur) 
        references Serveur;

    alter table TaskParameter 
        add constraint FK16AD3384A096529F 
        foreign key (TaskRefId) 
        references TaskRef;

    alter table TaskRef 
        add constraint FK797F8AE1ADBED57 
        foreign key (TaskId) 
        references Task;

    alter table Termination_Task 
        add constraint FKC739A220A096529F 
        foreign key (TaskRefId) 
        references TaskRef;

    alter table Termination_Task 
        add constraint FKC739A220BE3D10F3 
        foreign key (TasksUserId) 
        references Tasks_User;

    alter table UserAccess 
        add constraint FKB60A252F6EEDFC90 
        foreign key (ApplicationId) 
        references Component;

    alter table UserBO 
        add constraint FK9790197875D5ECAE 
        foreign key (ProfileId) 
        references ProfileBO;

    alter table User_Rights 
        add constraint FK21885CCBFDF9038A 
        foreign key (UserId) 
        references UserBO;

    alter table User_Rights 
        add constraint FK21885CCB75D5ECAE 
        foreign key (ProfileId) 
        references ProfileBO;

    alter table User_Rights 
        add constraint FK21885CCB6EEDFC90 
        foreign key (ApplicationId) 
        references Component;

    alter table Volumetry_Measures 
        add constraint FK92AE693368A4458F 
        foreign key (VolumetryId) 
        references displayConf;

    create sequence Module_sequence;

    create sequence RuleSet_sequence;

    create sequence Rule_sequence;

    create sequence TransgressionItem_sequence;

    create sequence auditFrequency_sequence;

    create sequence audit_sequence;

    create sequence auditconf_sequence;

    create sequence auditgrid_sequence;

    create sequence component_sequence;

    create sequence displayconf_sequence;

    create sequence error_sequence;

    create sequence formula_sequence;

    create sequence mark_sequence;

    create sequence measure_sequence;

    create sequence metric_sequence;

    create sequence news_sequence;

    create sequence profile_sequence;

    create sequence profiledisplayconf_sequence;

    create sequence project_parameter_sequence;

    create sequence qualitygrid_sequence;

    create sequence qualityres_sequence;

    create sequence qualityrule_sequence;

    create sequence reference_sequence;

    create sequence rigth_sequence;

    create sequence stats_annexe_sequence;

    create sequence stats_sequence;

    create sequence stoptime_sequence;

    create sequence taskParameter_sequence;

    create sequence taskRef_sequence;

    create sequence task_sequence;

    create sequence tasksUser_sequence;

    create sequence userAccess_sequence;

    create sequence user_sequence;
