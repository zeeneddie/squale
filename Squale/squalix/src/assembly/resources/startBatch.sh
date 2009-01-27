#!/bin/sh
ulimit -n 1024

# Definition of the serveur id
# The value of this variable should be the id given to this server in the table server of the database.

case "`hostname`" in 
	dev8ts)
		hostId=2
		;;
	qvgdev03)
		hostId=4
		;;
	tlsdev02)
		hostId=3
		;;
	qvidssx1)
		hostId=1
		;;
	*)
		hostId=1
		;;
esac

# Definition of JAVA_HOME
#JAVA_HOME=${START-BATCH-JAVA-HOME}
#export JAVA_HOME

LANG=fr
export LANG

# Definition of the debug launch if -debug in first position
if [ "-debug" = "$1" ]; then
 DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"
 echo "Lancement un mode debug sur le port 1044"
fi

# Definition of the debug launch if -debug in second position
if [ "-debug" = "$2" ]; then
 DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"
 echo "Lancement un mode debug sur le port 1044"
fi

# Definition of the debug launch if -debug in third position
if [ "-debug" = "$3" ]; then
 DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"
 echo "Lancement un mode debug sur le port 1044"
fi

# Definition of the debug launch if -debug in forth position
if [ "-debug" = "$4" ]; then
 DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"
 echo "Lancement un mode debug sur le port 1044"
fi

# Definition of the debug launch if -debug in fifth position
if [ "-debug" = "$5" ]; then
 DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"
 echo "Lancement un mode debug sur le port 1044"
fi

SQUALIX_HOME=`dirname $0`
cd ${SQUALIX_HOME}
# The -Xms512M has been remove for prevent the "java.io.IOException: Not enough space" during the fork on dev8ts
# which is probably due to an empty /tmp ...
# The java command line
${JAVA_HOME}/bin/java -Dsquale.home=$SQUALE_HOME -DentityExpansionLimit=500000 -Djava.awt.headless=true $DEBUG -Xmx512M -Xss7M -jar  ${SQUALIX_HOME}/${project.build.finalName}.jar ${SQUALIX_HOME} -s $hostId "$1" "$2" "$3" "$4" "$5"
