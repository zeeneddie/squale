-- #####################################################
-- For Language display customization
-- This migration script HAS NOT BEEN TESTED

-- Table Tasks_User
ALTER TABLE Tasks_User 
	ADD language varchar(255) NULL;

-- Table SqualeReference & ajout de la colonne ProgrammingLanguage
ALTER TABLE squaleReference 
	ADD programminglanguage varchar(255) NULL;

-- Peuplement de la colonne 'ProgrammingLanguage' en fonction de la valeur de la colonne 'ProjectLanguage'
UPDATE squaleReference 
	SET programminglanguage = 'cobol' WHERE projectlanguage = 'cobol';
UPDATE squaleReference 
	SET programminglanguage = 'java' WHERE projectlanguage = 'java';
UPDATE squaleReference
	SET programminglanguage = 'java' WHERE projectlanguage = 'java compiled';
UPDATE squaleReference
	SET programminglanguage = 'java' WHERE projectlanguage = 'j2ee';
UPDATE squaleReference
	SET programminglanguage = 'java' WHERE projectlanguage = 'j2ee compiled';
UPDATE squaleReference
	SET programminglanguage = 'cpp' WHERE projectlanguage = 'cpp';