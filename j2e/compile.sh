#!/usr/bin/env bash


files="core
     webapp room-booker booking-receiver event-registry coordinator bill-creator
     client event-organizer-root event-organizer-free event-organizer-with-billing event-organizer
     event-creator jsf
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