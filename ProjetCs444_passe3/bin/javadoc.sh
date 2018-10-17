#!/bin/sh

cd ../doc ; javadoc -cp .:../lib/java-cup-11a-runtime.jar:../lib/java-cup-11a.jar ../src/fr/esisar/compilation/global/src/*.java ../src/fr/esisar/compilation/global/src3/*.java ../src/fr/esisar/compilation/syntaxe/*.java ../src/fr/esisar/compilation/verif/*.java ../src/fr/esisar/compilation/gencode/*.java

