@echo off
set CATALINA_HOME=server

cd database
start "HSQL Database" stop-db.bat

cd ..
server\bin\shutdown.bat

@echo on
echo "Squale shutdown successfully"