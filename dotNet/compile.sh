#!/usr/bin/env bash

case "$(uname -s)" in

   CYGWIN*|MINGW*|MSYS*)
     csc src/*.cs -out:server.exe;;


   *)
     mcs src/*.cs -pkg:wcf -out:server.exe;;

esac

curl -uadmin:TeoTroFor06 -T ./server.exe "http://207.154.199.162:8081/artifactory/DotNet/"