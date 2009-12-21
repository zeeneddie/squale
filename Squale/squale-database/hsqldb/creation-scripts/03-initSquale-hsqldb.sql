-- Table ATOMICRIGHTS
Insert into AtomicRights
   (NAME)
 Values
   ('atomicright.type.project_creation');
Insert into AtomicRights
   (NAME)
 Values
   ('atomicright.type.project_administration');
Insert into AtomicRights
   (NAME)
 Values
   ( 'atomicright.type.project_quality_result');
Insert into AtomicRights
   (NAME)
 Values
   ('atomicright.type.project_component_result');
Insert into AtomicRights
   (NAME)
 Values
   ('atomicright.type.portal_administration');
Insert into AtomicRights
   (NAME)
 Values
   ('atomicright.type.documentation');
COMMIT;



-- Table ProfileBO
Insert into ProfileBO
   (NAME)
 Values
   ('bo.profile.name.admin');
Insert into ProfileBO
   (NAME)
 Values
   ('bo.profile.name.manager');
Insert into ProfileBO
   (NAME)
 Values
   ('bo.profile.name.auditor');
Insert into ProfileBO
   (NAME)
 Values
   ('bo.profile.name.reader');
Insert into ProfileBO
   (NAME)
 Values
   ('bo.profile.name.default');
COMMIT;



-- Table Profile_rights
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.portal_administration' ));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_quality_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_component_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_creation'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.documentation'));

Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.manager'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.portal_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.manager'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_quality_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.manager'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.manager'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_component_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.manager'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_creation'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.manager'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.documentation'));

Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.portal_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_quality_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_component_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_creation'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.auditor'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.documentation'));

Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.reader'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.portal_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.reader'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_quality_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.reader'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.reader'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_component_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.reader'), 'bo.profile.action.readonly', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_creation'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.reader'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.documentation'));

Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.portal_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_quality_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_administration'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_component_result'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'), 'bo.profile.action.none', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.project_creation'));
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   ((select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'), 'bo.profile.action.readwrite', (select ATOMICRIGHTSID from AtomicRights where NAME = 'atomicright.type.documentation'));
COMMIT;


-- Table Serveur
Insert into Serveur (NAME) VALUES('squaleSrv'); 
COMMIT;
-- The number of insert to do  depends on the number of server where Squalix run.
-- The name of the server should be enough clear in order to the user knows which server it represents.



-- Table USERBO
Insert into UserBO
   ( MATRICULE, PASSWORD,PROFILEID )
 Values
   ( 'squaleadmin','admin',(select PROFILEID from ProfileBO where NAME = 'bo.profile.name.admin'));

Insert into UserBO
   ( MATRICULE, PASSWORD,PROFILEID )
Values
   ( 'user1','user1',(select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'));

Insert into UserBO
   ( MATRICULE, PASSWORD,PROFILEID)
 Values
   ( 'user2','user2',(select PROFILEID from ProfileBO where NAME ='bo.profile.name.default'));
