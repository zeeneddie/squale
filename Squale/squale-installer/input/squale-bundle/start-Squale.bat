@echo off
set LANG=fr_FR.ISO8859-1
set INSTALL_FOLDER=%cd%
set SQUALE_HOME=%cd%\squale.home
set CATALINA_HOME=%cd%\server
set JAVA_OPTS=-Dsquale.home="%SQUALE_HOME%"

REM Ensure the logs directory exists for Squale Web
if not exist "%SQUALE_HOME%\SqualeWeb\logs" mkdir "%SQUALE_HOME%\SqualeWeb\logs"

REM First, start HSQL
cd %INSTALL_FOLDER%\database
start "HSQL Database" start-db.bat

REM Then, start Squalix
cd %SQUALE_HOME%\Squalix\bin
start "Squalix" .\startBatch.bat -cron

REM And finally, start Tomcat
cd %INSTALL_FOLDER%
server\bin\startup.bat

@echo on
echo "Squale started successfully"