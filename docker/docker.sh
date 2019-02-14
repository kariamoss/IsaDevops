#!/usr/bin/env bash

artifactory="http://207.154.199.162:8081/artifactory/"
envRepo="env"
normalRepo="libs-snapshot-local"
premiumRepo="premium"

if [[ "$1" == "normal" ]]; then
    repo=$normalRepo
elif [[ "$1" == "premium" ]] ;then
    repo=$premiumRepo
fi
echo $repo


apt-get update
apt-get install -y curl
apt-get install -y wget

curl -uadmin:TeoTroFor06 -O $artifactory$envRepo/jre-8u171-linux-x64.tar.gz
tar -xzf jre-8u171-linux-x64.tar.gz
rm jre-8u171-linux-x64.tar.gz

curl -uadmin:TeoTroFor06 -O $artifactory$envRepo/apache-tomee-plus-7.0.2.tar.gz
tar -xzf apache-tomee-plus-7.0.2.tar.gz
rm apache-tomee-plus-7.0.2.tar.gz

chmod +x ./latest-war.sh
./latest-war.sh $repo