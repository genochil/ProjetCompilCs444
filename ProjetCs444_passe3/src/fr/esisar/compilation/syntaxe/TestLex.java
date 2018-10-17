// ----------------------------------------------------------------------------
// Testeur pour l'analyseur lexical
// ----------------------------------------------------------------------------

package fr.esisar.compilation.syntaxe;

import java.io.InputStream;

import java_cup.runtime.Symbol;
import fr.esisar.compilation.global.src.ArgsFichier;

/**
 * La classe TestLex permet de tester l'analyseur lexical défini
 * dans la classe Lexical. 
 */
public class TestLex {

   /** 
    * Fonction principale de la classe TestLex, qui permet de lire 
    * des unités lexicales sur l'entrée standard. 
    * L'exception java.io.IOException est levée en cas d'erreur 
    * d'entrées/sorties.
    * L'exception ErreurLexicale est levée en cas d'erreur lexicale.
    * Ces deux exception sont levées par la fonction next_token 
    * de la classe Lexical (fonction principale permettant de lire des
    * unites lexicales).
    */

   public static void main(String args[]) throws java.io.IOException {

      try {

         // L'entrée de TestLex
         InputStream fichierCas = ArgsFichier.ouvrir(args);
      
         // Création d'un analyseur lexical 
         Lexical analyseur = new Lexical(fichierCas);

         // Un token
         Symbol t ; 

         // Lecture de la premiere unite lexicale
         t = analyseur.next_token() ; 

         // Boucle principale 
         // En fin de fichier, la fonction next_token() renvoie un Symbol
         // dont la "valeur" est sym.EOF
      
         while (t.sym != sym.EOF) {
            System.out.println(Lexical.toString(t));
            t = analyseur.next_token() ;
         }

         System.out.println() ;
   
      } catch (ErreurLexicale e) {
          // Recuperation de l'exception ErreurLexicale
          // On ne fait rien
      }
   }

}

