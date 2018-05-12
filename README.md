# Poly’Event

# Build

Le script de build compile, deploie sur les serveurs artifactory les artifacts produits et construit les images 
docker. 

Il est possible de paramétrer les dépots où les produits de la compilation sont poussés : 

maven : ./j2e/root/pom.xml lignes 35 36 37

dotNet : ./dotNet/compile.sj lignes 3 4 5 6

docker : ./docker/docker.sh lignes 3 4 5 6 et ./docker/mono-docker/artif.sh

En tout nous utilisons 4 repos artifactory

DotNet : artefact dotNet

libs-snapshot-local : maven free

premium : maven premium

env : fichiers nécéssaires au build des images docker

Pour construire les images dockers, ils nous faut une JVM et un serveur Tomee qui sont préconfiguré sur artifactory

Si la construction des images docker echoue i lest toujours possible de pull les images avec le script ./docker/pull.sh 