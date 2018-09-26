package fr.esisar.compilation.gencode;

import fr.esisar.compilation.global.src.*;
import fr.esisar.compilation.global.src3.*;
import fr.esisar.compilation.syntaxe.*;
import fr.esisar.compilation.verif.*;

import java.io.File;
import java.io.PrintStream;

/**
 * Classe principale du compilateur JCas.
 */

public class JCasc {

/**
 * Programme principal du compilateur JCas. 
 */

   public static void main(String args[]) throws Exception {

      try {
         // Passe 1
         // Appel de l'analyseur syntaxique et récupération de l'arbre résultat
	 Arbre arbre = parser.analyseSyntaxique(args);

	 // Passe 2
         // Analyse contextuelle et décoration de l'arbre abstrait
	 Verif passe2 = new Verif();
         passe2.verifierDecorer(arbre); 
	         
	 // Passe 3
         // Génération de code 
         // prog est le programme assembleur généré
         Prog prog = Generation.coder(arbre); 

         // Ecriture du programme généré dans le fichier assembleur
         String fichierAss = ArgsFichier.sortie(args);
         PrintStream ps = System.out;
         if (fichierAss != null) {
            System.setOut(new PrintStream(new File(fichierAss))); 
            prog.afficher();
            System.setOut(ps);
            System.out.println(
               "-- Programme correctement compilé dans " + fichierAss); 
         }
	         
      } catch (ErreurLexicale e) {
	   // Récuperation de l'exception ErreurLexicale
	   // On ne fait rien
      } catch (ErreurSyntaxe e) {
	   // Récuperation de l'exception ErreurSyntaxe
	   // On ne fait rien
      } catch (ErreurVerif e) {
	   // Récuperation de l'exception ErreurVerif
	   // On ne fait rien
      }
   }
}
