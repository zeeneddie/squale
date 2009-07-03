#!/bin/sh
export INSTALL_FOLDER=`pwd`
export SQUALE_HOME=$INSTALL_FOLDER/squale.home
export CATALINA_HOME=$INSTALL_FOLDER/server

# Stop Tomcat
server/bin/shutdown.sh

# wait for some seconds (for Tomcat to close properly the connection to the DB)
# ( --> should improve the script and wait for Tomcat PID to disappear)
sleep 5

# Stop Squalix
squalixpid=`pgrep -f "Djava.ext.dirs=lib-ext -Dsquale.home=$SQUALE_HOME"`
[ ! "$squalixpid" = "" ] || fatal_error "Found no process that looks like Squalix"
[ `expr "$squalixpid" : '.*@.*'` -eq 0 ] || fatal_error "Multiples processus with the following PID: $squalixpid"
kill $squalixpid

# And finally stop HSQL DB
cd database
./stop-db.sh

echo "Squale shutdown successfully"
