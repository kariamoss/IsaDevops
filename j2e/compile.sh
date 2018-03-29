#!/usr/bin/env bash

file="core
        client event-creator event-organizer event-registry j2e-interface room-booker webapp"

for i in $file
do
    cd $i
    mvn deploy
    cd ..
done