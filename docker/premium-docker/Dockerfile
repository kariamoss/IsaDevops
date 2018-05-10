FROM ubuntu
MAINTAINER LucasLaMachine

ADD ./latest-war.sh ./
ADD ./docker.sh ./

RUN chmod +x ./docker.sh
RUN ./docker.sh premium


ENTRYPOINT JRE_HOME=./jre1.8.0_171/ && export JRE_HOME && \
  echo $AgendaAddr && echo $AgendaPort && \
 ./apache-tomee-plus-7.0.2/bin/catalina.sh run

