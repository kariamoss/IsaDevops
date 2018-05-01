@echo off

cd root
call mvn install
cd ..

for %%D in (core,webapp,room-booker,event-registry,coordinator,client,event-organizer-root,event-organizer-free,event-organizer,event-creator,j2e-interface) do (
    cd %%D
    call mvn clean install -o
    cd ..
)