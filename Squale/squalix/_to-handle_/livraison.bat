@echo off
REM Utiliser livraison dev8ts pour copier dans /app/SQUALE/livraison
REM apres avoir genere squalix.jar
REM utiliser :
REM >mesa Dev (ou Rct, Prd...)
REM afin de :
REM  1) lancer /app/SQUALE/migConfigBatch.sh Dev SQUALE
REM      pour remplacer les variables
REM  2) rm /app/SQUALE/dev    (ou un mv si on prefere)
REM  	mv /app/SQUALE/livraison /app/SQUALE/dev 
REM  	pour livrer la nouvelle version 
REM
REM  NB: necessite la mise à jour de .rhosts de squale pour chaque poste

rsh %1 -l squale rm -rf /app/SQUALE/livraison
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/bin/java/lib
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/bin/java/config
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/bin/java/resources
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/cc_snapshot
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/checkstyle
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/cpptest
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/cpptest/logs
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/mccabe
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/mccabe/logs
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/rsm
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/jspvolumetry
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/data/umlquality
rsh %1 -l squale mkdir -p /app/SQUALE/livraison/logs

rcp -a -r resources %1.squale:/app/SQUALE/livraison/bin/java
rcp -a -r config %1.squale:/app/SQUALE/livraison/bin/java
rcp -b ..\squaleEAR\*.jar %1.squale:/app/SQUALE/livraison/bin/java/lib
rcp -b -r lib %1.squale:/app/SQUALE/livraison/bin/java
rcp -b -r batch/* %1.squale:/app/SQUALE/livraison/bin/java

rcp -b \temp\squalix.jar %1.squale:/app/SQUALE/livraison/bin/java

rsh %1 -l squale chmod +w /app/SQUALE/livraison/bin/java/*.sh
rsh %1 -l squale dos2unix /app/SQUALE/livraison/bin/java/startBatch.sh /app/SQUALE/livraison/bin/java/startBatch.sh
rsh %1 -l squale dos2unix /app/SQUALE/livraison/bin/java/stopBatch.sh /app/SQUALE/livraison/bin/java/stopBatch.sh

rsh %1 -l squale chmod +x /app/SQUALE/livraison/bin/java/*.sh
rsh %1 -l squale chmod +x /app/SQUALE/livraison/bin/java/resources/*.sh

rsh %1 -l squale chmod 777 /app/SQUALE/livraison/bin/java/config/*



