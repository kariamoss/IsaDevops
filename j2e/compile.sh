#!/usr/bin/env bash

file="core
        webapp client event-creator event-organizer event-registry coordinator j2e-interface room-booker"

for i in $file
do
    cd $i
    mvn deploy
    cd ..
done