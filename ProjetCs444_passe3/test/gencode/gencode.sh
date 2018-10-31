#!/bin/bash
# Test de la passe 2

for fich in *.cas
do
   
    echo "---------------------------------------------------------------------"
    echo "Fichier : $fich"
    echo "---------------------------------------------------------------------"
    cd ../../classes ; java -cp .:../lib/java-cup-11a-runtime.jar fr.esisar.compilation.gencode/JCasc ../test/gencode/$fich ; cd ../test/gencode
    echo "---------------------------------------------------------------------"
    echo "Appuyer sur Return"
    read r
done

