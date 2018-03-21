#!/usr/bin/env bash

set -e
set -o pipefail

basedir="/Users/ringo/BP_Ether/myEtherProject/src/main"

fileName="HealthCheck"

    cd $basedir

    echo "Generating contract bindings"
    web3j solidity generate \
        ${basedir}/resources/${fileName}.bin \
        ${basedir}/resources/${fileName}.abi \
        -p org.web3j.sample.contracts.generated \
        -o ../../java/ > /dev/null
    echo "Complete"
