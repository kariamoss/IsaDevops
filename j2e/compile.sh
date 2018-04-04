#!/usr/bin/env bash

file="core
     webapp room-booker event-registry coordinator
     client event-organizer
     event-creator
     j2e-interface"

mvn deploy

for i in $file
do
    cd $i
    mvn clean install
    cd ..
done