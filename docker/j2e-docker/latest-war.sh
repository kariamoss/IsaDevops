#!/usr/bin/env bash
# Artifactory location
server=http://207.154.199.162:8081/artifactory
repo=libs-snapshot-local

# Maven artifact location
name=j2e-interface
artifact=polyevent/${name}
path=$server/$repo/$artifact
version=`curl -s $path/maven-metadata.xml | grep latest | sed "s/.*<latest>\([^<]*\)<\/latest>.*/\1/"`
build=`curl -s $path/$version/maven-metadata.xml | grep '<value>' | head -1 | sed "s/.*<value>\([^<]*\)<\/value>.*/\1/"`
war=$name-$build.war
url=$path/$version/$war

# Download
echo $url
wget -q $url
mv $war ./j2e-interface-1.0-SNAPSHOT.war
mv ./j2e-interface-1.0-SNAPSHOT.war ./apache-tomee-plume-7.0.2/webapps
