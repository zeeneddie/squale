#!/bin/bash
#logFile=/dev/null
#logFile=/app/SQUALE/squalix/log/mccabe.log
params=$*
ulimit -n 1024
# TODO : specify the server name for the licence
export LM_LICENSE_FILE=${MCCABE-LICENSE_SERVER}
${MCCABE-BIN-FOLDER}/cli $params
returnValue=`echo $?`
exit $returnValue
