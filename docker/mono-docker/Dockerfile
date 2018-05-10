FROM mono
MAINTAINER LucasLaMachineTuLeC

RUN apt-get update
RUN apt-get install curl

ADD ./artif.sh ./
RUN chmod +x artif.sh
RUN ./artif.sh


ENTRYPOINT mono ./server.exe /standalone
