#!/usr/bin/env bash
cd j2e
mvn install
cd ../dotNet
./compile.sh
cd ..