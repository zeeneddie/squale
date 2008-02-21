-- Ajout de la colonne précisant si l'utilisateur est abonné ou non aux mails automatiques
alter table UserBO add unsubscribed number(1) default 0 not null;