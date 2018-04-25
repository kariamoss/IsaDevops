FROM ubuntu
MAINTAINER LucasLaMachine

RUN apt-get update
RUN apt-get install -y curl

RUN curl -uadmin:TeoTroFor06 -O http://207.154.199.162:8081/artifactory/env/jre-8u171-linux-x64.tar.gz
RUN tar -xzf jre-8u171-linux-x64.tar.gz
RUN rm jre-8u171-linux-x64.tar.gz

RUN curl -uadmin:TeoTroFor06 -O http://207.154.199.162:8081/artifactory/env/apache-tomee-7.0.2-plume.tar.gz
RUN tar -xzf apache-tomee-7.0.2-plume.tar.gz
RUN rm apache-tomee-7.0.2-plume.tar.gz

ENTRYPOINT curl http://207.154.199.162:8081/artifactory/libs-snapshot-local/polyevent/j2e-interface/1.0-SNAPSHOT/j2e-interface-1.0-SNAPSHOT.war -o j2e-interface-1.0-SNAPSHOT.war && \
 mv ./j2e-interface-1.0-SNAPSHOT.war ./apache-tomee-plume-7.0.2/webapps && \
 JRE_HOME=./jre1.8.0_171/ && export JRE_HOME && \
 ./apache-tomee-plume-7.0.2/bin/catalina.sh run