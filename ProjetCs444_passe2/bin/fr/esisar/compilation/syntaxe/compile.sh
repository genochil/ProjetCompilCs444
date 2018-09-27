#!/bin/bash
# Compilation de la passe 1

# JFlex
echo
echo "JFlex ..."
echo
java -jar ../../../../../lib/JFlex.jar ../../../../../input/lexical.flex ; mv ../../../../../input/Lexical.java . || exit 1 


# Cup
echo
echo "Cup ..."
echo
java -jar ../../../../../lib/java-cup-11a.jar ../../../../../input/syntaxe.cup || exit 1

# Compilation

echo
echo "Compilation passe 1 ..."
echo
cd ../../../.. ; javac -Xlint:unchecked -d ../classes -cp .:../lib/java-cup-11a-runtime.jar fr/esisar/compilation/syntaxe/*.java

