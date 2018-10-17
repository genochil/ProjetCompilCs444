#!/bin/bash
# Compilation de global/src3

# Compilation

echo
echo "Compilation de global/src3 ..."
echo
cd ../../../../.. ; javac -Xlint:unchecked -d ../classes fr/esisar/compilation/global/src3/*.java

