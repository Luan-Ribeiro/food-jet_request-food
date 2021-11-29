#!/bin/sh

if [ "$#" -ne 4 ]; then
    echo "Illegal number of parameters"
    echo "USAGE [./health-check.sh <host> <errorCode> <successCode> <msgServiceName>]"
fi

host=$1
curlExitCode=$2 #exit status code from curl. we start with error, to enter the loop
successCode=$3
serviceName=$4

echo "##########################################################################################"
echo "Starting ${serviceName} health-check..."
echo "##########################################################################################"

count=1
maxAttempts=40
sleepInterval=1
while [ $curlExitCode -ne $successCode ] # curlExitCode != successCode => its the status from when the service starts responding
  do
    if [ "$count" -gt "$maxAttempts" ] # prints a msg and exit the script if (count > maxAttempts)
      then echo "Reached the maximum of ${maxAttempts} attempts, exiting test script" && exit 1
    fi

    echo ""
    if [ "$count" -ne 1 ] #skip this message on first attempt (count != 1)
      then echo "Service ${host} not available, waiting ${sleepInterval}s to try again"
    fi

    sleep $sleepInterval #waiting

    echo "Attempt ${count}/${maxAttempts}"
    echo "######################################/CURL-MESSAGE/######################################"

    curl --fail $host #trying to reach the service
    curlExitCode=$? #taking new exit code from curl command above
    count=$((count + 1)) #incrementing count

    echo ""
    echo "##########################################################################################"
  done
