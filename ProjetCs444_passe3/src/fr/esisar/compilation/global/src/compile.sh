#!/bin/bash
# Compilation de global/src 

# Compilation

echo
echo "Compilation de global/src ..."
echo
cd ../../../../.. ; javac -Xlint:unchecked -d ../classes fr/esisar/compilation/global/src/*.java

