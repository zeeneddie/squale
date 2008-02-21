-- On ajoute la map eclipse à tous les projets qui possède une liste de projets RSA ou WSAD
-- ie: "wsad" ou "rsa"
-- Par défaut les projets compilerons avec Eclipse.

insert into squale.projectparameter (parameterid, subclass, mapId, key) (select PROJECT_PARAMETER_SEQUENCE.nextval, 'Map', mapid, 'eclipseCompilation' from squale.projectparameter where key='wsad' or key='rsa');
commit;