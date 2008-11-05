-- Table ATOMICRIGHTS
Insert into AtomicRights
   (ATOMICRIGHTSID, NAME)
 Values
   (1, 'atomicright.type.project_creation');
Insert into AtomicRights
   (ATOMICRIGHTSID, NAME)
 Values
   (2, 'atomicright.type.project_administration');
Insert into AtomicRights
   (ATOMICRIGHTSID, NAME)
 Values
   (3, 'atomicright.type.project_quality_result');
Insert into AtomicRights
   (ATOMICRIGHTSID, NAME)
 Values
   (4, 'atomicright.type.project_component_result');
Insert into AtomicRights
   (ATOMICRIGHTSID, NAME)
 Values
   (5, 'atomicright.type.portal_administration');
Insert into AtomicRights
   (ATOMICRIGHTSID, NAME)
 Values
   (6, 'atomicright.type.documentation');
COMMIT;



-- Table ProfileBO
Insert into ProfileBO
   (PROFILEID, NAME)
 Values
   (1, 'bo.profile.name.admin');
Insert into ProfileBO
   (PROFILEID, NAME)
 Values
   (2, 'bo.profile.name.manager');
Insert into ProfileBO
   (PROFILEID, NAME)
 Values
   (3, 'bo.profile.name.reader');
Insert into ProfileBO
   (PROFILEID, NAME)
 Values
   (4, 'bo.profile.name.default');
COMMIT;



-- Table Profile_rights
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (1, 'bo.profile.action.readwrite', 5);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (1, 'bo.profile.action.readwrite', 3);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (1, 'bo.profile.action.readwrite', 2);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (1, 'bo.profile.action.readwrite', 4);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (1, 'bo.profile.action.readwrite', 1);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (1, 'bo.profile.action.readwrite', 6);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (2, 'bo.profile.action.readwrite', 5);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (2, 'bo.profile.action.readwrite', 3);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (2, 'bo.profile.action.readonly', 2);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (2, 'bo.profile.action.readwrite', 4);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (2, 'bo.profile.action.readwrite', 1);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (2, 'bo.profile.action.readwrite', 6);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (3, 'bo.profile.action.readonly', 5);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (3, 'bo.profile.action.readwrite', 3);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (3, 'bo.profile.action.none', 2);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (3, 'bo.profile.action.readwrite', 4);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (3, 'bo.profile.action.readonly', 1);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (3, 'bo.profile.action.readwrite', 6);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (4, 'bo.profile.action.none', 5);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (4, 'bo.profile.action.none', 3);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (4, 'bo.profile.action.none', 2);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (4, 'bo.profile.action.none', 4);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (4, 'bo.profile.action.none', 1);
Insert into Profile_Rights
   (PROFILEID, RIGHTS_VALUE, ATOMICRIGHTSID)
 Values
   (4, 'bo.profile.action.readwrite', 6);
COMMIT;



-- Table Serveur
Insert into Serveur VALUES(1, 'squaleSrv'); 
COMMIT;
-- Dépend de l'environnement...
-- Ces valeurs servent lors de la configuration d'une application car le serveur est un champs obligatoire!


-- Table USERBO
Insert into UserBO
   (USERID, MATRICULE, PASSWORD,PROFILEID )
 Values
   (1, 'squaleadmin','admin',1);

Insert into UserBO
   (USERID, MATRICULE, PASSWORD,PROFILEID )
Values
   (2, 'squaleusera','aa',4);

Insert into UserBO
   (USERID, MATRICULE, PASSWORD,PROFILEID)
 Values
   (3, 'squaleuserb','bb',4);
   
Insert into UserBO
   (USERID, MATRICULE, PASSWORD,PROFILEID)
 Values
   (4, 'squaleuserc','cc',4);
commit;