Squalix 
===========================================
Crée le .jar a partir de squalix.jardesc

L'architecture de squalix en livraison est la suivante :

+ (repertoire d'install)
|  squalix.jar
+---config
|       clearcase-config.xml
|       compiling-config.xml
|       csv-config.xml
|       hibernate.cfg.xml
|       log-trace-config.xml
|       mccabe-config.xml
|       meta-config.xml
|       providers-config.xml
|       rapports.RPT
|       squalix-config.xml
+---resources
|       mccabe_cli.sh
+---log
|	(... fichier de log ...)
+--- workspace
|       (... Workspace McCabe)

Il faut donc livrer le contenu de config (fichier de conf des taches Squalix) et de resources
