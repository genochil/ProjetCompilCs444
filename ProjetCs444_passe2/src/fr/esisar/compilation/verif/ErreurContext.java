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

	ErreurNonRepertoriee, IndexationIncorrect, TypesIncompatibles, IndexTypeIncorrect;

	void leverErreurContext(String s, int numLigne) throws ErreurVerif {
		System.err.println("Erreur contextuelle : ");
		switch (this) {
		case TypesIncompatibles:
			System.err.print("Types incompatibles : " + s);
		case IndexationIncorrect:
			System.err.print("Indexation possible uniquement avec un Type Array ");
		case IndexTypeIncorrect:
			System.err.print("Type d'index incorrect : ");
		default:
			System.err.print("non repertoriee");
		}
		System.err.println(" ... ligne " + numLigne);
		throw new ErreurVerif();
	}

}
