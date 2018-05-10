#!/usr/bin/env bash

cd ./j2e-docker/
./build.sh
cd ..

cd ./premium-docker/
./build.sh
cd ..

cd ./mono-docker/
./build.sh