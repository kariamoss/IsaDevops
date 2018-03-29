#!/usr/bin/env bash

file="client core event-creator event-organizer event-registry j2e-interface room-booker webapp"

for i in $file
do
    cd $i
    mvn install
    cd ..
done