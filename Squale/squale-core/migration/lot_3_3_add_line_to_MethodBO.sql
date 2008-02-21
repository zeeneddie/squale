-- Numéro de ligne pour les méthodes afin de la récupérer pour le plugin Eclipse
alter table Component add StartLine number(10,0) default 0;
commit;