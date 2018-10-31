#!/bin/bash
# Test de la lexicographie

for fich in *.cas
do
   
    echo "---------------------------------------------------------------------"
    echo "Fichier : $fich"
    echo "---------------------------------------------------------------------"
    cd ../../classes ; java -cp .:../lib/java-cup-11a-runtime.jar fr.esisar.compilation.syntaxe.TestLex ../test/lexico/$fich ; cd ../test/lexico
    echo "---------------------------------------------------------------------"
    echo "Appuyer sur Return"
    read r
done

