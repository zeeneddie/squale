#!/bin/bash
#logFile=/dev/null
#logFile=/app/SQUALE/squalix/log/mccabe.log
params=$*
ulimit -n 1024
export LM_LICENSE_FILE=7190@sun8tp,7190@rmax8tp,7190@od1tp
#/OUTILS/McCabe/current/bin/cli $params >> $logFile 2>&1
/OUTILS/McCabe/current/bin/cli $params
returnValue=`echo $?`
exit $returnValue
