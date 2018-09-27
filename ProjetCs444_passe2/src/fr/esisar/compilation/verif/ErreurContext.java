/**
 * Type énuméré pour les erreurs contextuelles.
 * Ce type énuméré définit toutes les erreurs contextuelles possibles et 
 * permet l'affichage des messages d'erreurs pour la passe 2.
 */

// -------------------------------------------------------------------------
// A COMPLETER, avec les différents types d'erreur et les messages d'erreurs 
// correspondants
// -------------------------------------------------------------------------

package fr.esisar.compilation.verif;

public enum ErreurContext {
   
   ErreurNonRepertoriee,
	TypesIncompatibles;

   void leverErreurContext(String s, int numLigne) throws ErreurVerif {
      System.err.println("Erreur contextuelle : ");
      switch (this) {
      case TypesIncompatibles:
    	  System.err.print("Types incompatibles : "+s);
         default:
            System.err.print("non repertoriee");
      }
      System.err.println(" ... ligne " + numLigne);
      throw new ErreurVerif();
   }

}


