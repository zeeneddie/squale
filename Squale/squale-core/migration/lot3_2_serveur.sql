-- script migration base Squale v3.1 -> v3.2 

-- TABLE SERVEUR
--drop table Serveur cascade constraints;

create table Serveur (
   ServeurId number(19,0) not null,
   Name varchar2(256) not null unique,
   primary key (ServeurId)
);

-- ordre à respecter impérativement
insert into Serveur values (1,'Valbonne (qvidssx1)');
insert into Serveur values (2,'Valbonne (dev8ts)');
insert into Serveur values (3,'Toulouse');
insert into Serveur values (4,'Vilgenis');


-- TABLE COMPONENT
alter table Component add Serveur number(19,0) ;

alter table Component add constraint FK_COMPONENT_SERVEUR foreign key (Serveur) references Serveur (ServeurId);

-- mise à jour de la colonne Serveur de la table 
update Component set Serveur=2 where Site='qvi';
update Component set Serveur=3 where Site='tls';
update Component set Serveur=4 where Site='qvg';
update Component set Serveur=1 where Site='ssx';


-- TABLE STATS_SQUALE_DICT
alter table Stats_Squale_Dict add Serveur number(19,0) ;

alter table Stats_Squale_Dict add constraint FK_STATS_SERVEUR foreign key (Serveur) references Serveur (ServeurId);

-- mise à jour de la colonne Serveur de la table 
update Stats_Squale_Dict set Serveur=2 where Site='qvi';
update Stats_Squale_Dict set Serveur=3 where Site='tls';
update Stats_Squale_Dict set Serveur=4 where Site='qvg';
update Stats_Squale_Dict set Serveur=1 where Site='ssx';

-- clé primaire Site à supprimer, rétablir 'id' en  clé primaire

-- TABLE STATS_SQUALE_DICT_ANNEXE
alter table Stats_Squale_Dict_Annexe add Serveur number(19,0) ;

alter table Stats_Squale_Dict_Annexe add constraint FK_STATS_ANNEXE_SERVEUR foreign key (Serveur) references Serveur (ServeurId);

-- mise à jour de la colonne Serveur de la table 
update Stats_Squale_Dict_Annexe set Serveur=2 where Site='qvi';
update Stats_Squale_Dict_Annexe set Serveur=3 where Site='tls';
update Stats_Squale_Dict_Annexe set Serveur=4 where Site='qvg';
update Stats_Squale_Dict_Annexe set Serveur=1 where Site='ssx';

-- Contraintes, Suppression des colonnes Sites
alter table stats_squale_dict_annexe drop constraint fk_annexe_principale;
alter table stats_squale_dict drop constraint pk_stats_squale_dict;
alter table stats_squale_dict add primary key (id);

alter table component drop column site;
alter table stats_squale_dict drop column site;
alter table Stats_Squale_Dict_Annexe drop column site;



