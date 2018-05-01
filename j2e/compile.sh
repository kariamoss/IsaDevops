#!/usr/bin/env bash

files="core
     webapp room-booker event-registry coordinator
     client event-organizer-root event-organizer-free event-organizer
     event-creator
     j2e-interface"

cd root
mvn install
cd ..

for i in ${files}
do
    cd ${i}
    mvn clean install -o
    cd ..
done