#!/usr/bin/env bash

file="core
     webapp room-booker event-registry coordinator
     client event-organizer
     event-creator
     j2e-interface"

cd root
mvn install
cd ..

for i in $file
do
    cd $i

    mvn clean install -o
    cd ..
done