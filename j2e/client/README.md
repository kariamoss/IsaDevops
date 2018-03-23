To run the application :

First, run at the root of the project :\
`compile.sh /`

Then, move to `<project-root>/j2e/j2e-interface/`, and run :\
`mvn tomee:run`

Then, in a new terminal at `<project-root>/dotNet/`, run :\
`./compile.sh`\
`./server.exe`

Then, in a third terminal, move to `<project-root>/j2e/client/`, and run :\
`mvn exec:java`

You now, in this terminal, have acces to the command line interface.\
Available commands are :\
`createEvent <eventName> <participantNumber> <coordinatorEmail>`\
`exit`