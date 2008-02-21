-- Ajout des deux colonnes nécessaires à l'export IDE
-- Nom du fichier
alter table RuleCheckingTransgressionItem add Path varchar2(3000);
-- Numéro de ligne
alter table RuleCheckingTransgressionItem add Line number(10,0) default 0 not null;