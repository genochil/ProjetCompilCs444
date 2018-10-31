#!/bin/bash

# Compile l'ensemble du projet

cd fr/esisar/compilation/global/src ; ./compile.sh ; cd ../../../../.. || exit 1 
cd fr/esisar/compilation/global/src3 ; ./compile.sh ; cd ../../../../.. || exit 1
cd fr/esisar/compilation/syntaxe ; ./compile.sh ; cd ../../../.. || exit 1
cd fr/esisar/compilation/verif ; ./compile.sh ; cd ../../../.. || exit 1
cd fr/esisar/compilation/gencode ; ./compile.sh ; cd ../../../..

