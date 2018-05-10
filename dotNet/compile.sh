#!/usr/bin/env bash

server="http://207.154.199.162:8081/artifactory/"
repo="DotNet"
username="admin"
pass="TeoTroFor06"

case "$(uname -s)" in

   CYGWIN*|MINGW*|MSYS*)
     csc src/*.cs -out:server.exe;;


   *)
     mcs src/*.cs -pkg:wcf -out:server.exe;;

esac

curl -u$username:$pass -T ./server.exe $server$repo