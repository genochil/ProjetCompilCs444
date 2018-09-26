// ----------------------------------------------------------------------------
// Testeur pour l'analyseur syntaxique
// ----------------------------------------------------------------------------

package fr.esisar.compilation.syntaxe;

import fr.esisar.compilation.global.src.Arbre;

/** 
 * Classe qui permet de tester l'analyse syntaxique.
 */
public class TestSynt {

   /** 
    * Méthode principale qui permet de tester l'analyse syntaxique et 
    * la construction de l'arbre abstrait.
    */

   public static void main(String args[]) throws Exception {

      try {
         // Appel de l'analyseur syntaxique et construction de l'arbre abstrait
         Arbre arbre = parser.analyseSyntaxique(args); 
         // Affichage de l'arbre
         arbre.afficher(0);
         // Décompilation de l'arbre
         arbre.decompiler(0);
      } catch (ErreurLexicale e) {
         // Recuperation de l'exception ErreurLexicale
         // On ne fait rien
      } catch (ErreurSyntaxe e) {
         // Recuperation de l'exception ErreurSyntaxe
         // On ne fait rien
      }
   }

}

