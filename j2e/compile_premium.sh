#!/usr/bin/env bash

files="core webapp room-booker event-registry coordinator client bill-creator event-organizer-root event-organizer-with-billing"

files_with_P="event-organizer"

files_end="event-creator j2e-interface"

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

for i in ${files_end}
do
    cd ${i}
    mvn clean install -o
    cd ..
done