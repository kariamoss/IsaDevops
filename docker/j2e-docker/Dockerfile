FROM ubuntu
MAINTAINER LucasLaMachine

ADD latest-war.sh ./

RUN apt-get update
RUN apt-get install -y curl
RUN apt-get install -y wget

RUN curl -uadmin:TeoTroFor06 -O http://207.154.199.162:8081/artifactory/env/jre-8u171-linux-x64.tar.gz
RUN tar -xzf jre-8u171-linux-x64.tar.gz
RUN rm jre-8u171-linux-x64.tar.gz

RUN curl -uadmin:TeoTroFor06 -O http://207.154.199.162:8081/artifactory/env/apache-tomee-7.0.2-plume.tar.gz
RUN tar -xzf apache-tomee-7.0.2-plume.tar.gz
RUN rm apache-tomee-7.0.2-plume.tar.gz


RUN chmod +x ./latest-war.sh
RUN ./latest-war.sh

ENTRYPOINT JRE_HOME=./jre1.8.0_171/ && export JRE_HOME && ./latest-war.sh && \
 ./apache-tomee-plume-7.0.2/bin/catalina.sh run