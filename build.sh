#!/usr/bin/env bash

cd j2e
./compile.sh
./compile_premium.sh
cd ../dotNet
./compile.sh
cd ..

cd docker
./buildAll.sh