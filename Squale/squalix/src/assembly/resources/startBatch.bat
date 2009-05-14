@echo off

set SQUALIX_HOME=%cd%

cd %SQUALIX_HOME%

set DEBUG=

rem The -Xms512M has been remove for prevent the "java.io.IOException: Not enough space" during the fork on dev8ts
rem which is probably due to an empty /tmp ...
rem The java command line

rem Definition of the debug launch if -debug in first position
if -debug == %1 set DEBUG= -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044
if -debug == %1 echo "Lancement un mode debug sur le port 1044"

rem Definition of the debug launch if -debug in second position
if -debug == %2 set DEBUG= -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044
if -debug == %2 echo "Lancement un mode debug sur le port 1044"
	
rem Definition of the debug launch if -debug in third position
if -debug == %3 set DEBUG= -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044
if -debug == %3 echo "Lancement un mode debug sur le port 1044"

rem Definition of the debug launch if -debug in forth position
if -debug == %4 set DEBUG= -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044
if -debug == %4 echo "Lancement un mode debug sur le port 1044"

rem Definition of the debug launch if -debug in fifth position
if -debug == %5 set DEBUG= -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044
if -debug == %5 echo "Lancement un mode debug sur le port 1044"

		
rem the -s option is set to 1 because we are in a one server environment (first server in database)

%JAVA_HOME%/bin/java -Djava.ext.dirs=lib-ext %DEBUG% -Dsquale.home=%SQUALE_HOME% -DentityExpansionLimit=500000 -Djava.awt.headless=true -Xmx512M -Xss7M -jar  "%SQUALIX_HOME%\${project.build.finalName}.jar" "%SQUALIX_HOME%" -s 1 %*

