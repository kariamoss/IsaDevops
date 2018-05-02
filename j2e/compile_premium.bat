@echo off

cd root
call mvn install
cd ..

for %%D in (core,webapp,room-booker,event-registry,coordinator,client,bill-creator,event-organizer-root,event-organizer-with-billing) do (
    cd %%D
    call mvn clean install -o
    cd ..
)

for %%D in (event-organizer) do (
    cd %%D
    call mvn clean install -o -P premium
    cd ..
)

for %%D in (event-creator,j2e-interface) do (
    cd %%D
    call mvn clean install -o
    cd ..
)