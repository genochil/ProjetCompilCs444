// ----------------------------------------------------------------------------
// Testeur pour la passe de vérifications contextuelles
// ----------------------------------------------------------------------------

package fr.esisar.compilation.verif;

import fr.esisar.compilation.global.src.*;
import fr.esisar.compilation.syntaxe.*;

/** 
 * Classe qui permet de tester la passe de vérifications contextuelles.
 */

public class TestVerif {


   /**
    * Méthode de test de la passe de vérifications contextuelles.
    */
   public static void main(String args[]) throws Exception {

      try {
         // Appel de l'analyseur syntaxique et récupération de l'arbre résultat
         Arbre arbre = parser.analyseSyntaxique(args);
         arbre.afficher(0);
         // Décompilation de l'arbre
         arbre.decompiler(0);
         // On construit un verificateur de passe 2
         Verif passe2 = new Verif();
         passe2.verifierDecorer(arbre); 
         arbre.afficher(1);
         // Décompilation de l'arbre
         arbre.decompiler(1);
         
      } catch (ErreurLexicale e) {
         // Recuperation de l'exception ErreurLexicale
         // On ne fait rien
      } catch (ErreurSyntaxe e) {
         // Recuperation de l'exception ErreurSyntaxe
         // On ne fait rien
      } catch (ErreurVerif e) {
         // Recuperation de l'exception ErreurVerif
         // On ne fait rien
      }
   }

}

