#!/bin/sh
export LANG=fr_FR.ISO8859-1
export INSTALL_FOLDER=`pwd`
export SQUALE_HOME=$INSTALL_FOLDER/squale.home
export CATALINA_HOME=$INSTALL_FOLDER/server
export JAVA_OPTS=-Dsquale.home=$SQUALE_HOME

# Ensure some logs directory exist
# 	- For Tomcat
if [ ! -d $CATALINA_HOME/logs ] 
then
	mkdir $CATALINA_HOME/logs
fi
# 	- For Squale Web
if [ ! -d $SQUALE_HOME/SqualeWeb/logs ] 
then
	mkdir $SQUALE_HOME/SqualeWeb/logs
fi

# First, start HSQL
cd $INSTALL_FOLDER/database
./start-db.sh &

# Then, start Squalix
cd $SQUALE_HOME/Squalix/bin
./startBatch.sh -cron &

# And finally, start Tomcat
cd $INSTALL_FOLDER
server/bin/startup.sh &

echo "Squale started successfully"
