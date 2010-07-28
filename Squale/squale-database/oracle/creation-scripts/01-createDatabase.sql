
    drop table Analysis_Task cascade constraints;

    drop table ApplicationLastExport cascade constraints;

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

    drop table HomepageComponent cascade constraints;

    drop table Job cascade constraints;

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

    drop table QualityResult_Comment cascade constraints;

    drop table QualityRule cascade constraints;

    drop table Rule cascade constraints;

    drop table RuleCheckingTransgressionItem cascade constraints;

    drop table RuleSet cascade constraints;

    drop table Segment cascade constraints;

    drop table SegmentCategory cascade constraints;

    drop table Segment_Module cascade constraints;

    drop table Segment_Segmentation cascade constraints;

    drop table Segmentation cascade constraints;

    drop table Serveur cascade constraints;

    drop table SharedRepoStats cascade constraints;

    drop table SqualeParams cascade constraints;

    drop table SqualeReference cascade constraints;

    drop table Stats_squale_dict cascade constraints;

    drop table Stats_squale_dict_annexe cascade constraints;

    drop table StopTimeBO cascade constraints;

    drop table Tag cascade constraints;

    drop table TagCategory cascade constraints;

    drop table Tag_Component cascade constraints;

    drop table Task cascade constraints;

    drop table TaskParameter cascade constraints;

    drop table TaskRef cascade constraints;

    drop table Tasks_User cascade constraints;

    drop table Termination_Task cascade constraints;

    drop table UserAccess cascade constraints;

    drop table UserBO cascade constraints;

    drop table User_Rights cascade constraints;

    drop table Volumetry_Measures cascade constraints;

    drop table adminParams cascade constraints;

    drop table displayConf cascade constraints;

    drop sequence HomepageComponent_sequence;

    drop sequence Module_sequence;

    drop sequence RuleSet_sequence;

    drop sequence Rule_sequence;

    drop sequence TransgressionItem_sequence;

    drop sequence adminparams_sequence;

    drop sequence auditFrequency_sequence;

    drop sequence audit_sequence;

    drop sequence auditconf_sequence;

    drop sequence auditgrid_sequence;

    drop sequence component_sequence;

    drop sequence displayconf_sequence;

    drop sequence error_sequence;

    drop sequence formula_sequence;

    drop sequence job_sequence;

    drop sequence lastExport_sequence;

    drop sequence mark_sequence;

    drop sequence measure_sequence;

    drop sequence metric_sequence;

    drop sequence news_sequence;

    drop sequence profile_sequence;

    drop sequence profiledisplayconf_sequence;

    drop sequence project_parameter_sequence;

    drop sequence qualitygrid_sequence;

    drop sequence qualityres_comment_sequence;

    drop sequence qualityres_sequence;

    drop sequence qualityrule_sequence;

    drop sequence reference_sequence;

    drop sequence rigth_sequence;

    drop sequence segmentCategory_sequence;

    drop sequence segment_sequence;

    drop sequence segmentation_sequence;

    drop sequence serveur_sequence;

    drop sequence sharedRepoStats_sequence;

    drop sequence squaleParams_sequence;

    drop sequence stats_annexe_sequence;

    drop sequence stats_sequence;

    drop sequence stoptime_sequence;

    drop sequence tagCategory_sequence;

    drop sequence tag_sequence;

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

    create table ApplicationLastExport (
        LastExportId number(19,0) not null,
        ComponentId number(19,0) unique,
        LastExportDate date,
        ToExport number(1,0),
        primary key (LastExportId)
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
        Duration varchar2(10),
        END_DATE date,
        MAX_FILE_SYSTEM_SIZE number(19,0),
        BEGINNING_DATE date,
        squale_version varchar2(100),
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
        AuditFrequency number(10,0),
        ResultsStorageOptions number(10,0),
        Status number(10,0),
        PublicApplication number(1,0),
        LastUpdate date,
        EXTERNAL_DEV number(1,0),
        IN_PRODUCTION number(1,0),
        lastUser varchar2(1024),
        Serveur number(19,0),
        QualityApproachOnStart number(1,0),
        InInitialDev number(1,0),
        GlobalCost number(10,0),
        DevCost number(10,0),
        LongFileName varchar2(2048),
        ProfileBO number(19,0),
        ParametersSet number(19,0),
        QualityGrid number(19,0),
        SourceManager number(19,0),
        StartLine number(10,0),
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

    create table HomepageComponent (
        HomepageComponentId number(19,0) not null,
        ComponentName varchar2(255) not null,
        UserBO number(19,0) not null,
        ComponentPosition number(10,0) not null,
        ComponentValue varchar2(255),
        primary key (HomepageComponentId)
    );

    create table Job (
        JobId number(19,0) not null,
        JobName varchar2(100),
        JobStatus varchar2(100),
        JobDate date,
        primary key (JobId)
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
        MessageKey varchar2(255) not null,
        lang varchar2(6) not null,
        Title varchar2(4000),
        Text varchar2(4000) not null,
        primary key (MessageKey, lang)
    );

    create table Metric (
        MetricId number(19,0) not null,
        subclass varchar2(255) not null,
        Name varchar2(255),
        MeasureId number(19,0),
        Blob_val blob,
        Boolean_val number(1,0),
        Number_val float,
        String_val varchar2(4000),
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
        NewsKey varchar2(4000) not null,
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
        ProfileId number(19,0) not null,
        GridId number(19,0) not null,
        primary key (ProfileId, GridId)
    );

    create table ProjectParameter (
        ParameterId number(19,0) not null,
        subclass varchar2(255) not null,
        Value varchar2(1024),
        ListId number(19,0),
        Rank number(10,0),
        MapId number(19,0),
        IndexKey varchar2(255),
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
        CreationDate date,
        QualityRuleId number(19,0) not null,
        MeanMark float not null,
        ProjectId number(19,0) not null,
        AuditId number(19,0),
        primary key (QualityResultId)
    );

    create table QualityResult_Comment (
        QR_CommentId number(19,0) not null,
        Comments varchar2(4000),
        QualityResultId number(19,0) unique,
        primary key (QR_CommentId)
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
        TimeLimitation varchar2(6),
        Criticality number(10,0),
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
        CppTestName varchar2(255) unique,
        Language varchar2(255),
        primary key (RuleSetId)
    );

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

    create table Serveur (
        ServeurId number(19,0) not null,
        Name varchar2(255) not null unique,
        primary key (ServeurId)
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

    create table SqualeReference (
        ReferencielId number(19,0) not null,
        QualityGrid number(19,0),
        PublicApplication number(1,0),
        ApplicationName varchar2(255),
        ProjectName varchar2(255),
        ProjectLanguage varchar2(255),
        ProgrammingLanguage varchar2(255) not null,
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
        NB_FACTEURS_ACCEPTES number(10,0),
        NB_FACTEURS_ACCEPTES_RESERVES number(10,0),
        NB_FACTEURS_REFUSES number(10,0),
        NB_TOTAL_APPLI number(10,0),
        NB_APPLI_AVEC_AUDIT_EXECUTE number(10,0),
        NB_APPLI_AVEC_AUDIT_REUSSI number(10,0),
        Date_calcul date,
        ROI_EN_MHI double precision,
        NB_APPLI_A_VALIDER number(10,0),
        NB_AUDITS_ECHECS number(10,0),
        NB_AUDITS_PROGRAMME number(10,0),
        NB_AUDITS_PARTIELS number(10,0),
        NB_AUDITS_REUSSIS number(10,0),
        NB_APPLI_AVEC_AUCUN_AUDIT number(10,0),
        Serveur number(19,0),
        primary key (Id)
    );

    create table Stats_squale_dict_annexe (
        Id number(19,0) not null,
        NB_LIGNES number(10,0),
        Profil varchar2(50),
        NB_PROJETS number(10,0),
        Serveur number(19,0),
        primary key (Id)
    );

    create table StopTimeBO (
        StopTimeId number(19,0) not null,
        DayOfWeek varchar2(9) not null,
        TimeOfDay varchar2(5) not null,
        primary key (StopTimeId)
    );

    create table Tag (
        TagId number(19,0) not null,
        Name varchar2(255) not null unique,
        Description varchar2(1024) not null,
        TagCategory number(19,0),
        primary key (TagId)
    );

    create table TagCategory (
        TagCategoryId number(19,0) not null,
        Name varchar2(255) not null unique,
        Description varchar2(1024) not null,
        primary key (TagCategoryId)
    );

    create table Tag_Component (
        ComponentId number(19,0) not null,
        TagId number(19,0) not null,
        primary key (ComponentId, TagId)
    );

    create table Task (
        TaskId number(19,0) not null,
        Name varchar2(255) not null unique,
        Class varchar2(2048) not null,
        Configurable number(1,0) not null,
        Standard number(1,0),
        Mandatory number(1,0),
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
        export_IDE number(1,0),
        language varchar2(255),
        MilestoneAudit number(1,0),
        NormalAudit number(1,0),
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
        ApplicationId number(19,0),
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

    create table adminParams (
        AdminParamsId number(19,0) not null,
        paramKey varchar2(255) not null,
        paramaValue varchar2(255) not null,
        primary key (AdminParamsId)
    );

    create table displayConf (
        ConfId number(19,0) not null,
        subclass varchar2(255) not null,
        X_TRE varchar2(400),
        Y_TRE varchar2(400),
        X_POS number(19,0),
        Y_POS number(19,0),
        componentType varchar2(255),
        primary key (ConfId)
    );

    alter table Analysis_Task 
        add constraint FK91CAC9089ACA29CA 
        foreign key (TaskRefId) 
        references TaskRef;

    alter table Analysis_Task 
        add constraint FK91CAC908E93A2E5E 
        foreign key (TasksUserId) 
        references Tasks_User;

    alter table ApplicationLastExport 
        add constraint FK1E3E973AB660AF72 
        foreign key (ComponentId) 
        references Component;

    alter table AuditDisplayConfBO 
        add constraint FKBF5515D8CAD1505B 
        foreign key (AuditId) 
        references AuditBO
        on delete cascade;

    alter table AuditDisplayConfBO 
        add constraint FKBF5515D8DA15C497 
        foreign key (ProjectId) 
        references Component
        on delete cascade;

    alter table AuditDisplayConfBO 
        add constraint FKBF5515D8DD868E9C 
        foreign key (ConfId) 
        references displayConf;

    alter table AuditGridBO 
        add constraint FK71AB79AECAD1505B 
        foreign key (AuditId) 
        references AuditBO
        on delete cascade;

    alter table AuditGridBO 
        add constraint FK71AB79AEDA15C497 
        foreign key (ProjectId) 
        references Component
        on delete cascade;

    alter table AuditGridBO 
        add constraint FK71AB79AE49C5C1F2 
        foreign key (QualityGridId) 
        references QualityGrid;

    alter table Component 
        add constraint FK24013CDDEF730ACB 
        foreign key (Serveur) 
        references Serveur;

    alter table Component 
        add constraint FK24013CDDCF3F6BD7 
        foreign key (SourceManager) 
        references Tasks_User;

    alter table Component 
        add constraint FK24013CDDECE51913 
        foreign key (ProjectId) 
        references Component
        on delete cascade;

    alter table Component 
        add constraint FK24013CDDB2C13533 
        foreign key (ParametersSet) 
        references ProjectParameter;

    alter table Component 
        add constraint FK24013CDDBEC27BED 
        foreign key (ProfileBO) 
        references Tasks_User;

    alter table Component 
        add constraint FK24013CDD499FD217 
        foreign key (QualityGrid) 
        references QualityGrid;

    alter table Component 
        add constraint FK24013CDDE6E62BC9 
        foreign key (Parent) 
        references Component
        on delete cascade;

    alter table Components_Audits 
        add constraint FK5D3B0141CAD1505B 
        foreign key (AuditId) 
        references AuditBO
        on delete cascade;

    alter table Components_Audits 
        add constraint FK5D3B01419164C9D 
        foreign key (ComponentId) 
        references Component
        on delete cascade;

    alter table CriteriumPractice_Rule 
        add constraint FK8ADDE8664749294 
        foreign key (CriteriumRuleId) 
        references QualityRule;

    alter table CriteriumPractice_Rule 
        add constraint FK8ADDE8615B7FE16 
        foreign key (PracticeRuleId) 
        references QualityRule;

    alter table Error 
        add constraint FK401E1E8CAD1505B 
        foreign key (AuditId) 
        references AuditBO
        on delete cascade;

    alter table Error 
        add constraint FK401E1E8DA15C497 
        foreign key (ProjectId) 
        references Component
        on delete cascade;

    alter table FactorCriterium_Rule 
        add constraint FK897340301C18583E 
        foreign key (FactorRuleId) 
        references QualityRule;

    alter table FactorCriterium_Rule 
        add constraint FK8973403064749294 
        foreign key (CriteriumRuleId) 
        references QualityRule;

    alter table FactorRef 
        add constraint FK2854B0A4A4AEA807 
        foreign key (ReferencielId) 
        references SqualeReference
        on delete cascade;

    alter table FactorRef 
        add constraint FK2854B0A497209814 
        foreign key (Rule) 
        references QualityRule
        on delete cascade;

    alter table Formula_Conditions 
        add constraint FKB3141771EB17C0F9 
        foreign key (FormulaId) 
        references Formula;

    alter table Formula_Measures 
        add constraint FK7A19C1CEA4104C92 
        foreign key (FormulaId) 
        references Formula;

    alter table GridFactor_Rule 
        add constraint FKBC5A70C61C18583E 
        foreign key (FactorRuleId) 
        references QualityRule;

    alter table GridFactor_Rule 
        add constraint FKBC5A70C649C5C1F2 
        foreign key (QualityGridId) 
        references QualityGrid;

    alter table HomepageComponent 
        add constraint FK8D93B88F22FEA8A7 
        foreign key (UserBO) 
        references UserBO;

    alter table Mark 
        add constraint FK247AED4AA85BD7 
        foreign key (PracticeResultId) 
        references QualityResult
        on delete cascade;

    alter table Mark 
        add constraint FK247AED9164C9D 
        foreign key (ComponentId) 
        references Component
        on delete cascade;

    alter table Measure 
        add constraint FK9B263D3ECAD1505B 
        foreign key (AuditId) 
        references AuditBO
        on delete cascade;

    alter table Measure 
        add constraint FK9B263D3E6C1EEACE 
        foreign key (RuleSetId) 
        references RuleSet;

    alter table Measure 
        add constraint FK9B263D3E9164C9D 
        foreign key (ComponentId) 
        references Component
        on delete cascade;

    alter table Metric 
        add constraint FK892AE1D029D429E5 
        foreign key (MeasureId) 
        references Measure
        on delete cascade;

    alter table Module 
        add constraint FK89B0928C7729BC88 
        foreign key (RuleId) 
        references Rule
        on delete cascade;

    alter table PracticeResult_Repartition 
        add constraint FK84B2F9904AA85BD7 
        foreign key (PracticeResultId) 
        references QualityResult
        on delete cascade;

    alter table Profile_DisplayConfBO 
        add constraint FKC122A97DEA5281F2 
        foreign key (Profile_ConfId) 
        references displayConf;

    alter table Profile_DisplayConfBO 
        add constraint FKC122A97DBEC27CBB 
        foreign key (ProfileId) 
        references Tasks_User;

    alter table Profile_Rights 
        add constraint FK62F82D6D21CAA303 
        foreign key (AtomicRightsId) 
        references AtomicRights;

    alter table Profile_Rights 
        add constraint FK62F82D6DC21CFAE3 
        foreign key (ProfileId) 
        references ProfileBO;

    alter table Profiles_Grids 
        add constraint FK9949B158212FFC33 
        foreign key (GridId) 
        references QualityGrid;

    alter table Profiles_Grids 
        add constraint FK9949B158BEC27CBB 
        foreign key (ProfileId) 
        references Tasks_User;

    alter table ProjectParameter 
        add constraint FK6D7538B020F36050 
        foreign key (ListId) 
        references ProjectParameter;

    alter table ProjectParameter 
        add constraint FK6D7538B0CF7E4672 
        foreign key (MapId) 
        references ProjectParameter;

    alter table QualityResult 
        add constraint FKE9E7A9DCCAD1505B 
        foreign key (AuditId) 
        references AuditBO
        on delete cascade;

    alter table QualityResult 
        add constraint FKE9E7A9DCDA15C497 
        foreign key (ProjectId) 
        references Component
        on delete cascade;

    alter table QualityResult 
        add constraint FKE9E7A9DC6FA7AE5E 
        foreign key (QualityRuleId) 
        references QualityRule;

    alter table QualityResult_Comment 
        add constraint FKD36C3ADCCCF6BB41 
        foreign key (QualityResultId) 
        references QualityResult
        on delete cascade;

    alter table QualityRule 
        add constraint FK420ADC7BE9F00F7 
        foreign key (Formula) 
        references Formula;

    alter table Rule 
        add constraint FK270B1C6C1EEACE 
        foreign key (RuleSetId) 
        references RuleSet
        on delete cascade;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D4973CA94EE6 
        foreign key (RuleId) 
        references Rule;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D4973979AE76D 
        foreign key (MeasureId) 
        references Measure
        on delete cascade;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D49739164C9D 
        foreign key (ComponentId) 
        references Component
        on delete cascade;

    alter table RuleCheckingTransgressionItem 
        add constraint FKDF0D49736B5A5124 
        foreign key (ComponentInvolvedId) 
        references Component;

    alter table RuleSet 
        add constraint FKBF8713A6DA15C497 
        foreign key (ProjectId) 
        references Component
        on delete cascade;

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

    alter table SqualeReference 
        add constraint FK32FD7E08499FD217 
        foreign key (QualityGrid) 
        references QualityGrid
        on delete cascade;

    alter table Stats_squale_dict 
        add constraint FK9B3A9E52EF730ACB 
        foreign key (Serveur) 
        references Serveur;

    alter table Stats_squale_dict_annexe 
        add constraint FKBC8FB71EEF730ACB 
        foreign key (Serveur) 
        references Serveur;

    alter table Tag 
        add constraint FK1477AA86C98F7 
        foreign key (TagCategory) 
        references TagCategory;

    alter table Tag_Component 
        add constraint FKE093EE589164C9D 
        foreign key (ComponentId) 
        references Component
        on delete cascade;

    alter table Tag_Component 
        add constraint FKE093EE58FD9106F6 
        foreign key (TagId) 
        references Tag
        on delete cascade;

    alter table TaskParameter 
        add constraint FK16AD33849ACA29CA 
        foreign key (TaskRefId) 
        references TaskRef
        on delete cascade;

    alter table TaskRef 
        add constraint FK797F8AE76E45E0C 
        foreign key (TaskId) 
        references Task
        on delete cascade;

    alter table Termination_Task 
        add constraint FKC739A2209ACA29CA 
        foreign key (TaskRefId) 
        references TaskRef;

    alter table Termination_Task 
        add constraint FKC739A220E93A2E5E 
        foreign key (TasksUserId) 
        references Tasks_User;

    alter table UserAccess 
        add constraint FKB60A252FB4DCCF05 
        foreign key (ApplicationId) 
        references Component
        on delete cascade;

    alter table UserBO 
        add constraint FK97901978C21CFAE3 
        foreign key (ProfileId) 
        references ProfileBO;

    alter table User_Rights 
        add constraint FK21885CCB22FEA975 
        foreign key (UserId) 
        references UserBO;

    alter table User_Rights 
        add constraint FK21885CCBC21CFAE3 
        foreign key (ProfileId) 
        references ProfileBO;

    alter table User_Rights 
        add constraint FK21885CCBB4DCCF05 
        foreign key (ApplicationId) 
        references Component
        on delete cascade;

    alter table Volumetry_Measures 
        add constraint FK92AE693393A162FA 
        foreign key (VolumetryId) 
        references displayConf
        on delete cascade;

    create sequence HomepageComponent_sequence;

    create sequence Module_sequence;

    create sequence RuleSet_sequence;

    create sequence Rule_sequence;

    create sequence TransgressionItem_sequence;

    create sequence adminparams_sequence;

    create sequence auditFrequency_sequence;

    create sequence audit_sequence;

    create sequence auditconf_sequence;

    create sequence auditgrid_sequence;

    create sequence component_sequence;

    create sequence displayconf_sequence;

    create sequence error_sequence;

    create sequence formula_sequence;

    create sequence job_sequence;

    create sequence lastExport_sequence;

    create sequence mark_sequence;

    create sequence measure_sequence;

    create sequence metric_sequence;

    create sequence news_sequence;

    create sequence profile_sequence;

    create sequence profiledisplayconf_sequence;

    create sequence project_parameter_sequence;

    create sequence qualitygrid_sequence;

    create sequence qualityres_comment_sequence;

    create sequence qualityres_sequence;

    create sequence qualityrule_sequence;

    create sequence reference_sequence;

    create sequence rigth_sequence;

    create sequence segmentCategory_sequence;

    create sequence segment_sequence;

    create sequence segmentation_sequence;

    create sequence serveur_sequence;

    create sequence sharedRepoStats_sequence;

    create sequence squaleParams_sequence;

    create sequence stats_annexe_sequence;

    create sequence stats_sequence;

    create sequence stoptime_sequence;

    create sequence tagCategory_sequence;

    create sequence tag_sequence;

    create sequence taskParameter_sequence;

    create sequence taskRef_sequence;

    create sequence task_sequence;

    create sequence tasksUser_sequence;

    create sequence userAccess_sequence;

    create sequence user_sequence;
