#!/bin/bash
#logFile=/dev/null
#logFile=/app/SQUALE/squalix/log/mccabe.log
params=$*
ulimit -n 1024
# TODO : specify the server name for the licence
export LM_LICENSE_FILE=7190@squaleSrv
${MCCABE-BIN-FOLDER}/cli $params
returnValue=`echo $?`
exit $returnValue
