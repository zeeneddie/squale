-- Script global pour la migration du lot 3.3
-- Les scripts son appelés dans l'ordre de leur création

spool migration.txt

set echo on

@lot3_3_alter_ProjectProfile.sql

@lot_3_3_createUserAccess.sql

@lot3_3_create_AuditFrequency.sql

@lot_3_3_displayConfigurations.sql

@lot_3_3_create_profile_grid_link.sql

@lot_3_3_alter_user.sql

@lot_3_3_add_line_to_MethodBO.sql

spool off