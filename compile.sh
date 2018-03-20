#!/usr/bin/env bash
cd j2e/webapp/src/main/resources
./refreshWsdl.sh
cd ../../../..
mvn install
cd ../dotNet
./compile.sh
cd ..