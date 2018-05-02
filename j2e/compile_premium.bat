@echo off


for %%D in (event-organizer,event-creator,j2e-interface) do (
    cd %%D
    call mvn clean install -o -P premium
    cd ..
)