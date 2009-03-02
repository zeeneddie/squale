-- ###########################################################
-- For Language display customization for SQUALE version 4.1.1

-- Table Tasks_User & Ajout de la colonne Language
ALTER TABLE Tasks_User ADD language varchar2(255) NULL;

-- Table SqualeReference & ajout de la colonne ProgrammingLanguage
ALTER TABLE squaleReference ADD programminglanguage varchar2(255) NULL;

-- Peuplement de la colonne 'ProgrammingLanguage' en fonction de la valeur de la colonne 'ProjectLanguage'
UPDATE squaleReference SET programminglanguage = 'cobol' WHERE projectlanguage = 'cobol';
UPDATE squaleReference SET programminglanguage = 'java' WHERE projectlanguage = 'java';
UPDATE squaleReference SET programminglanguage = 'java' WHERE projectlanguage = 'java compiled';
UPDATE squaleReference SET programminglanguage = 'java' WHERE projectlanguage = 'j2ee';
UPDATE squaleReference SET programminglanguage = 'java' WHERE projectlanguage = 'j2ee compiled';
UPDATE squaleReference SET programminglanguage = 'cpp' WHERE projectlanguage = 'cpp';
COMMIT;