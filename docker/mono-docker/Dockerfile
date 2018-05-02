FROM mono
MAINTAINER LucasLaMachineTuLeC

RUN apt-get update
RUN apt-get install curl

RUN curl -uadmin:TeoTroFor06 -O "http://207.154.199.162:8081/artifactory/DotNet/server.exe"


ENTRYPOINT mono ./server.exe /standalone
