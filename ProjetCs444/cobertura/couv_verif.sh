#!/bin/bash

# -----------------------------------------------------------------------------
# Ce script permet l'évaluation de la couverture du paquetage 
# fr.esisar.compilation.verif par un jeu de tests.
#
# Script à mettre dans le répertoire ProjetCompil/cobertura
#
# Usage : 
#   ./couv_verif.sh chemin/fichiers_tests 
#
# Exemple d'utilisation : 
#   ./couv_verif.sh ../test/verif/*.cas
#
# Les résultats de couverture sont dans 
#    ./report/index.html
# -----------------------------------------------------------------------------

/bin/rm -f couverture.ser

PROJET=..
SOURCE=$PROJET/src
CLASSES=$PROJET/classes

BASEDIR=cobertura-2.1.1

echo "Instrumentation du code ..."
echo

$BASEDIR/cobertura-instrument.sh --destination code_instru --datafile couverture.ser --auxClasspath $CLASSES $CLASSES/fr/esisar/compilation/verif 

echo
echo "Execution des tests ..."

for fich in $* 
do

    echo "Fichier : $fich"


    java -cp $BASEDIR/cobertura-2.1.1.jar:$BASEDIR/lib/asm-5.0.1.jar:$BASEDIR/lib/asm-analysis-5.0.1.jar:$BASEDIR/lib/asm-tree-5.0.1.jar:$BASEDIR/lib/asm-commons-5.0.1.jar:$BASEDIR/lib/asm-util-5.0.1.jar:$BASEDIR/lib/slf4j-api-1.7.5.jar:$BASEDIR/lib/logback-core-1.0.13.jar:$BASEDIR/lib/logback-classic-1.0.13.jar:$BASEDIR/lib/oro-2.0.8.jar:code_instru:$CLASSES:$PROJET/lib/java-cup-11a-runtime.jar:$CLASSPATH -Dnet.sourceforge.cobertura.datafile=couverture.ser fr.esisar.compilation.verif.TestVerif $fich
done

echo
echo "Génération du rapport ..."
echo

cobertura-2.1.1/cobertura-report.sh --format html --datafile couverture.ser --destination report $SOURCE


