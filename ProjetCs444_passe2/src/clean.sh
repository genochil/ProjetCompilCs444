#!/bin/bash

# Efface tous les fichiers générés du projet

cd fr/esisar/compilation/global/src ; ./clean.sh ; cd ../../../../.. || exit 1
cd fr/esisar/compilation/global/src3 ; ./clean.sh ; cd ../../../../.. || exit 1
cd fr/esisar/compilation/syntaxe ; ./clean.sh ; cd ../../../.. || exit 1
cd fr/esisar/compilation/verif ; ./clean.sh ; cd ../../../.. || exit 1
cd fr/esisar/compilation/gencode ; ./clean.sh ; cd ../../../..

