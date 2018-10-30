package fr.esisar.compilation.gencode;

import fr.esisar.compilation.global.src.*;
import fr.esisar.compilation.global.src3.*;
import fr.esisar.compilation.verif.ErreurContext;

/**
 * Génération de code pour un programme JCas à partir d'un arbre décoré.
 */

class Generation {

	// Déclaration registres révservé
	private Registre R0 = Registre.R0;
	private Registre R1 = Registre.R1;
	private Registre R2 = Registre.R2;

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
			// ajouter valeur de l'indent  en pile
			return;
		default:
		}
	}

	public void coder_INST(Arbre a) {// Couvi
		switch (a.getNoeud()) {
		case Ecriture:
			Arbre parcours = a.getFils1();
			if (parcours.getNoeud() != Noeud.Vide) {
				coder_INST(parcours.getFils1());
				NatureType exp_nat_w = parcours.getFils2().getDecor().getType().getNature();
				switch (exp_nat_w) {
				case String:
					Prog.ajouter(
							Inst.creation1(Operation.WSTR, Operande.creationOpChaine(parcours.getFils2().getChaine())));
					break;
				case Interval:
					coder_EXP(parcours.getFils2(),R1);//D'après Diapo 19 passe 3 -- LOAD de l'ident ou Entier
					Prog.ajouter(Inst.creation0(Operation.WINT));
					break;
				case Real:
					coder_EXP(parcours.getFils2(),R1);//D'après Diapo 19 passe 3 -- LOAD de l'ident ou Réel
					Prog.ajouter(Inst.creation0(Operation.WFLOAT));
					break;
				default:
					break;
				}
			}
			break;
		case Ligne:
			Prog.ajouterComment("newligne, ligne :" + a.getNumLigne());
			Prog.ajouter(Inst.creation0(Operation.WNL));
			Prog.ajouterComment("Apres newligne, ligne :" + a.getNumLigne());
			break;
		default:
			break;
		}
	}

	public void coder_PLACE(Arbre a, Registre rc) {// Couvi, a faire en premier

	}

	/*
	 * Procédure de génération de code Génère du code pour l’expression A tel que
	 * l’expression soit --évaluée dans le registre Rc. --Précondition: le registre
	 * Rc est alloué. procedure Coder_Exp (A : Arbre; Rc : Registre)
	 */

	public void coder_EXP(Arbre a, Registre rc) { // Champey & Clémentin

	}

}
