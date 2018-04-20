FROM ubuntu
MAINTAINER LucasLaMachine

RUN apt-get update
RUN apt-get install -y wget

RUN wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jre-8u161-linux-x64.tar.gz
RUN tar -xzf jre-8u161-linux-x64.tar.gz
RUN rm jre-8u161-linux-x64.tar.gz

RUN wget http://repo.maven.apache.org/maven2/org/apache/tomee/apache-tomee/7.0.2/apache-tomee-7.0.2-plume.tar.gz
RUN tar -xzf apache-tomee-7.0.2-plume.tar.gz
RUN rm apache-tomee-7.0.2-plume.tar.gz

ENTRYPOINT curl http://207.154.199.162:8081/artifactory/libs-snapshot-local/polyevent/j2e-interface/1.0-SNAPSHOT/j2e-interface-1.0-SNAPSHOT.war -o j2e-interface-1.0-SNAPSHOT.war & \
 mv ./j2e-interface-1.0-SNAPSHOT.war ./apache-tomee-plume-7.0.2/webapps & \
 JRE_HOME=./jre1.8.0_161/ && export JRE_HOME && ./apache-tomee-plume-7.0.2/bin/catalina.sh run
