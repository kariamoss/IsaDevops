#!/usr/bin/env bash

file="core
     webapp room-booker event-registry coordinator
     client event-organizer
     j2e-interface"

for i in $file
do
    cd $i
    mvn deploy
    cd ..
done