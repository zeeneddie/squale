-- #####################################################
-- For Language display customization
-- This migration script HAS NOT BEEN TESTED

-- Table Tasks_User
alter table Tasks_User add language varchar(255) NULL;

-- Table SqualeReference & ajout de la colonne ProgrammingLanguage
alter table SqualeReference add programminglanguage varchar(255) NULL;

-- Peuplement de la colonne 'ProgrammingLanguage' en fonction de la valeur de la colonne 'ProjectLanguage'
update SqualeReference set programminglanguage = 'cobol' where ProjectLanguage = 'cobol';
update SqualeReference set programminglanguage = 'java' where ProjectLanguage = 'java';
update SqualeReference set programminglanguage = 'java' where ProjectLanguage = 'java compiled';
update SqualeReference set programminglanguage = 'java' where ProjectLanguage = 'j2ee';
update SqualeReference set programminglanguage = 'java' where ProjectLanguage = 'j2ee compiled';
update SqualeReference set programminglanguage = 'cpp' where ProjectLanguage = 'cpp';