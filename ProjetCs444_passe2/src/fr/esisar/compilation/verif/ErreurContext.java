/**
 * Type énuméré pour les erreurs contextuelles.
 * Ce type énuméré définit toutes les erreurs contextuelles possibles et 
 * permet l'affichage des messages d'erreurs pour la passe 2.
 */

package fr.esisar.compilation.verif;

public enum ErreurContext {

	/**
	 * Cas de déclaration multiple d'un identificateur
	 **/
	DoubleDeclarationId,

	/**
	 * Cas d'un identificateur non déclaré
	 **/
	MissingDeclaration,

	/**
	 * Cas d'une recherche d'un identificateur de type avec un defn associé de
	 * mauvaise nature
	 **/
	WrongNatureId,

	/**
	 * Cas où l'index indiqué pour un Array n'appartient pas à l'intervalle de définition
	 **/
	WrongArrayIndex,
	
	/**
	 * Cas où l'index indiqué n'est pas du bon type
	 **/
	WrongIndexType,

	/**
	 * Cas où les types des variables dans l'opération ne sont pas compatibles entre eux
	 */
	TypesIncompatibles,

	/**
	 * Cas d'indexation d'un type non Array
	 */
	NotArrayIndexation;

	/**
	 * Méthode permettant de lever une exception en précisant l'erreur (type et
	 * ligne)
	 * 
	 * Pour l'utiliser :
	 * ErreurContext err = ErreurContext.Nomdelerreur; err.leverErreurContext(String
	 * detail, int numLigne);
	 **/
	void leverErreurContext(String s, int numLigne) throws ErreurVerif {
		System.err.println("Erreur contextuelle : ");
		switch (this) {
		case DoubleDeclarationId:
			System.err.print("Identificateur " + s + " déjà déclaré ou est reservé ");
			break;
		case MissingDeclaration:
			System.err.print("Identificateur " + s + " non déclaré");
			break;
		case WrongNatureId:
			System.err.print("Mauvaise nature d'identificateur: " + s);
			break;
		case TypesIncompatibles:
			System.err.print("Types non compatibles " + s + " ");
			break;
		case WrongArrayIndex:
			System.err.print("L'index n'appartient pas à l'interval de définition de l'Array: " + s);
			break;
		case WrongIndexType:
			System.err.print("L'index n'est pas du bon type: " + s);
			break;
		case NotArrayIndexation:
			System.err.print("Impossible d'indexer un type non Array");
			break;
		default:
			System.err.print("Erreur inconnue");
		}
		System.err.println(" ... ligne " + numLigne);
		throw new ErreurVerif();
	}

}
