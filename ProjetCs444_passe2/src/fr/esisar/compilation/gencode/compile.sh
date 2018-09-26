#!/bin/bash
# Compilation de la passe 3

echo
echo "Compilation passe 3 ..."
echo
cd ../../../.. ; javac -Xlint:unchecked -d ../classes -cp .:../lib/java-cup-11a-runtime.jar fr/esisar/compilation/gencode/*.java

