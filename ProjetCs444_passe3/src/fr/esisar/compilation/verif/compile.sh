#!/bin/bash
# Compilation de la passe 2

# Compilation

echo
echo "Compilation passe 2 ..."
echo
cd ../../../.. ; javac -Xlint:unchecked -d ../classes -cp .:../lib/java-cup-11a-runtime.jar fr/esisar/compilation/verif/*.java

