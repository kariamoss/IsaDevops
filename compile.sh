#!/usr/bin/env bash
cd j2e/webapp/src/main/resources
./refreshWsdl.sh
cd ../../../..
./compile.sh
cd ../dotNet
./compile.sh
cd ..