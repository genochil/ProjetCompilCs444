package fr.esisar.compilation.gencode;

import fr.esisar.compilation.global.src.*;
import fr.esisar.compilation.global.src3.*;

/**
 * Génération de code pour un programme JCas à partir d'un arbre décoré.
 */

class Generation {

	/**
	 * Méthode principale de génération de code. Génère du code pour l'arbre décoré
	 * a.
	 */
	static Prog coder(Arbre a) {
		Prog.ajouterGrosComment("Programme généré par JCasc");
		
		Generation gen = new Generation();
		gen.coder_LISTE_DECL(a.getFils1());
		gen.coder_LISTE_INST(a.getFils2());

		// Fin du programme
		// L'instruction "HALT"
		Inst inst = Inst.creation0(Operation.HALT);
		// On ajoute l'instruction à la fin du programme
		Prog.ajouter(inst);

		// On retourne le programme assembleur généré
		return Prog.instance();
	}

	public void coder_LISTE_DECL(Arbre a) {
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeDecl:
			coder_LISTE_DECL(a.getFils1());
			coder_DECL(a.getFils2());
			return;
		default:
			break;
		}
	}

	public void coder_LISTE_INST(Arbre a) {
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeInst:
			coder_LISTE_INST(a.getFils1());
			coder_INST(a.getFils2());
			return;
		default:
			break;
		}
	}

	public void coder_DECL(Arbre a) {
		coder_LISTE_IDF(a.getFils1());
	}

	public void coder_LISTE_IDF(Arbre a) {
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeIdent:
			coder_LISTE_IDF(a.getFils1());
			// a.getFils2(), stocker valeur variable ?
			return;
		default:
		}
	}

	public void coder_INST(Arbre a) {// Couvi
		switch(a.getNoeud()) {
		case Ligne:
			Prog.ajouterComment("newligne, ligne :"+a.getNumLigne());
			Prog.ajouter(Inst.creation0(Operation.WNL));
			Prog.ajouterComment("Apres newligne, ligne :"+a.getNumLigne());
			break;
		default:
			break;
	}
	}

	public void coder_PLACE(Arbre a) {// Couvi, a faire en premier

	}

	public void coder_EXP(Arbre a) { // Champey & Clémentin

	}

}
