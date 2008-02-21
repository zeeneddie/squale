-- Script global pour la migration.
-- Les scripts son appelés dans l'ordre de leur création

spool migration.txt

set echo on

@lot3_2_alter_bubble_conf.sql

@lot3_2_serveur.sql

@lot3_2_alter_rulecheckingItem.sql

@lot3_2_alter_auditbo_for_squale_version.sql

@lot3_2_alter_ProjectProfile_for_exportIDE.sql

spool off