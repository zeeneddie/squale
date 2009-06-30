@echo off
set LANG=fr_FR.ISO8859-1
set SQUALE_HOME=squale.home
set CATALINA_HOME=server
set JAVA_OPTS=-Dsquale.home=%SQUALE_HOME%

REM Ensure the logs directory exists for Squale Web
if not exist %SQUALE_HOME%\SqualeWeb\logs mkdir %SQUALE_HOME%\SqualeWeb\logs

cd database
start "HSQL Database" start-db.bat

cd ..
server\bin\startup.bat

@echo on
echo "Squale started successfully"