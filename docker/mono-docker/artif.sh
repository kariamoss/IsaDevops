#!/usr/bin/env bash

server="http://207.154.199.162:8081/artifactory/"
repo="DotNet"
username="admin"
pass="TeoTroFor06"


echo -u$username:$pass -O $server$repo/server.exe
curl -u$username:$pass -O $server$repo/server.exe