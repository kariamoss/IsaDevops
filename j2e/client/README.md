To run the application :

In a terminal, at the path `<project-root>/j2e/j2e-interface/`, run :\
`mvn tomee:run`

Then, in a new terminal at `<project-root>/dotNet/`, run :\
`./compile.sh`\
`./server.exe`

Finally, in a third terminal, run at the root of the project :\
`compile.sh /`

Then move to `<project-root>/j2e/client/`, and run :\
`mvn exec:java`

You now, in this terminal, have acces to the command line interface.\
Available commands are :\
`createEvent <eventName> <participantNumber> <coordinatorEmail>`\
`exit`