#!/bin/bash
# Test de la syntaxe

for fich in *.cas
do
   
    echo "---------------------------------------------------------------------"
    echo "Fichier : $fich"
    echo "---------------------------------------------------------------------"
    cd ../../classes ; java -cp .:../lib/java-cup-11a-runtime.jar fr.esisar.compilation.syntaxe.TestSynt ../test/syntaxe/$fich ; cd ../test/syntaxe
    echo "---------------------------------------------------------------------"
    echo "Appuyer sur Return"
    read r
done

