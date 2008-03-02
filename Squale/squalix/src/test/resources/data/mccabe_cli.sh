#!/bin/bash
logFile=/dev/null
#logFile=/app/SQUALE/junit_unix_executor/log_pure_mccabe.log
params=$*
ulimit -n 1024
export LM_LICENSE_FILE=7190@sun8tp,7190@rmax8tp,7190@od1tp
/OUTILS/McCabe/current/bin/cli $params > $logFile 2>&1
returnValue=`echo $?`
exit $returnValue
