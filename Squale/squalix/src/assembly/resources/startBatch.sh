#!/bin/sh
ulimit -n 1024

case "`hostname`" in 
	dev8ts)
		type=2
		;;
	qvgdev03)
		type=4
		;;
	tlsdev02)
		type=3
		;;
	qvidssx1)
		type=1
		;;
esac

JAVA_HOME=/OUTILS/Java/1.4.2_03
export JAVA_HOME

LANG=fr
export LANG
if [ "$1" = "-debug" ]; then
 DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"
 echo "Lancement un mode debug sur le port 1044"
fi
SQUALIX_HOME=`dirname $0`
cd ${SQUALIX_HOME}
# On enleve le -Xms512M pour éviter les "java.io.IOException: Not enough space" lors des fork sur dev8ts
# a priori du a un /tmp plein...
${JAVA_HOME}/bin/java -DentityExpansionLimit=500000 -Djava.awt.headless=true $DEBUG -Xmx512M -Xss7M -jar  ${SQUALIX_HOME}/squalix.jar ${SQUALIX_HOME} -s $type
