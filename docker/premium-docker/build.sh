#!/usr/bin/env bash


cp ../docker.sh ./
cp ../latest-war.sh ./

sudo docker build -t lucassalut/j2e_premium_polyevent ./

rm docker.sh latest-war.sh