#!/usr/bin/env bash

files="core webapp room-booker booking-receiver event-registry coordinator client bill-creator event-organizer-root event-organizer-free event-organizer-with-billing event-organizer event-creator jsf"

files_with_P="j2e-interface"

cd root
mvn install
cd ..

for i in ${files}
do
    cd ${i}
    mvn clean install -o
    cd ..
done

for i in ${files_with_P}
do
    cd ${i}
    mvn clean install -o -P premium
    cd ..
done