@echo off

cd root
call mvn install
cd ..

for %%D in (core,webapp,room-booker,booking-receiver,event-registry,coordinator,client,bill-creator,event-organizer-root,event-organizer-free,event-organizer-with-billing,event-organizer,event-creator,jsf) do (
    cd %%D
    call mvn clean install -o
    cd ..
)

for %%D in (j2e-interface) do (
    cd %%D
    call mvn clean install -o -P premium
    cd ..
)