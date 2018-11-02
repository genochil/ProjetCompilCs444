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
		Prog.ajouter(Inst.creation1(Operation.ADDSP, Operande.creationOpEntier(1)));	
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
			Variable.add_var(a.getFils2());//Ajoute la variable dans notre HashMap, on add le duo : Nom_var, Emplacement
			return;
		default:
		}
	}

	public Operande coder_PLACE(Arbre a) {
		return Operande.creationOpEntier(15);// Couvi, a faire en premier

	}

	public void coder_INST(Arbre a) {// Couvi
		switch (a.getNoeud()) {
		case Ecriture:
			Prog.ajouterComment("Ecriture, ligne :" + a.getNumLigne());
			coder_Ecriture(a);
			Prog.ajouterComment("Fin Ecriture, ligne :" + a.getNumLigne());
			break;
		case Ligne:
			Prog.ajouterComment("NewLigne, ligne :" + a.getNumLigne());
			Prog.ajouter(Inst.creation0(Operation.WNL));
			Prog.ajouterComment("Fin NewLigne, ligne :" + a.getNumLigne());
			break;
		case Nop:
			return;
		case Affect:
			Prog.ajouterComment("Affect, ligne :" + a.getNumLigne());
			coder_Affect(a);
			Prog.ajouterComment("Fin Affect, ligne :" + a.getNumLigne());
			return;
		case Pour:
			Prog.ajouterComment("Pour, ligne :" + a.getNumLigne());
			coder_Pour(a);
			Prog.ajouterComment("Fin Pour, ligne :" + a.getNumLigne());
			return;
		case TantQue:
			Prog.ajouterComment("TantQue, ligne :" + a.getNumLigne());
			coder_TantQue(a);
			Prog.ajouterComment("Fin TantQue, ligne :" + a.getNumLigne());
			return;
		case Si:
			Prog.ajouterComment("Si, ligne :" + a.getNumLigne());
			coder_Si(a);
			Prog.ajouterComment("Fin si, ligne :" + a.getNumLigne());
			return;
		case Lecture:
			Prog.ajouterComment("Lecture, ligne :" + a.getNumLigne());
			coder_Lecture(a);
			Prog.ajouterComment("Fin Lecture, ligne :" + a.getNumLigne());
			return;
		default:
			break;
		}
	}

	// fait
	private void coder_Ecriture(Arbre a) {
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
				coder_EXP(parcours.getFils2(), R1);// D'après Diapo 19 passe 3 ,
				// On met dans R1 la valeur de l'identifiant correspondant au Noeud passé en
				// paramètre, ici parcours.getFils2()
				Prog.ajouter(Inst.creation0(Operation.WINT));
				break;
			case Real:
				coder_EXP(parcours.getFils2(), R1);// D'après Diapo 19 passe 3, même chose qu'avec Interval
				Prog.ajouter(Inst.creation0(Operation.WFLOAT));
				break;
			default:
				break;
			}
		}
	}
	/*Pas sur du tout !! */
	private void coder_Lecture(Arbre a) {
		// TODO Auto-generated method stub
		/*
		Quand on fait un read, on stocke la valeur dans un tableau ou un ident déclaré initialement
		*/
		Operande index =coder_PLACE(a.getFils1()); // Ecrire dans index la position en memoire(pile) de ident/tableau contenu dans
										// a.getFils1, cad ce qui à été déclaré avant le read
		
		switch (a.getFils1().getDecor().getType().getNature()) {
		case Interval:
			Prog.ajouter(Inst.creation0(Operation.RINT));
			debord_Interval(a.getFils1(), R0); // verifie qu'il n'y a pas de debordement d'intervalle
			break;
		case Real:
			Prog.ajouter(Inst.creation0(Operation.RFLOAT));
			break;
			
		default:
			break;
		}
		Prog.ajouter(
				Inst.creation2(Operation.STORE, Operande.opDirect(R1), Operande.creationOpIndexe(0, Registre.GB, index.getRegistre())));
		// On store la valeur contenue dans R1 qui est la valeur renvoyé par RINT dans la pile à
		// l'emplacement déterminé par R0

	}

	private void debord_Interval(Arbre a, Registre rc) {
		// coder en JAVA ou dans le code assembleur ?
	}

	// fait
	private void coder_Si(Arbre a) {
		/**
		 * Le code ci dessous permet de coder un Si en assembleur qui est representé par
		 * :
		 * 
		 * CMP R0 BNE false --code du corps du si-- BRA fin faux : --code du else-- fin:
		 * --suite du code--
		 * 
		 */
		Etiq E_sinon = Etiq.nouvelle("False");
		Etiq E_fin = Etiq.nouvelle("FinSi");
		coder_CMP_BNE(a.getFils1(), 1, E_sinon); // not_equal ( on fait un saut)
		coder_LISTE_INST(a.getFils2()); // on code le "corps" du si

		Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(E_fin)));// Si on a executé le corps du Si
																					// alors on doit jump a la fin et ne
																					// pas executer le potentiel else,
																					// on écrit donc un branch qui jump
																					// a l'étiquette "fin", qui
																					// representela fin du Si

		Prog.ajouter(E_sinon);// Une fois le code du corps ecrit, on ecrit notre etiquette sur laquelle on
								// jump si la condition est fausse, donc si la condition est fausse, on saute
								// bien le code du Si
		coder_LISTE_INST(a.getFils3());// On coder le corps du Else (qu'il soit vide ou non)
		Prog.ajouter(E_fin);// On ecrit la fin de notre si via l'étiquette crée plus haut
	}

	// fait
	private void coder_TantQue(Arbre a) {
		/*
		 * Coder_Inst(Noeud_Tantque(C, I)) = declare E_Cond : Etiq := Nouvelle_Etiq;
		 * E_Début : Etiq := Nouvelle_Etiq; begin Générer(BRA, E_Cond);
		 * Générer_Etiq(E_Début); Coder_Inst(I); Générer_Etiq(E_Cond); Coder_Cond(C,
		 * True, E_Début) end ;
		 */
		// TODO Auto-generated method stub
		Etiq E_cond = Etiq.nouvelle("E_cond");
		Etiq E_debut = Etiq.nouvelle("E_debut");
		Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(E_cond)));
		Prog.ajouter(E_debut);
		coder_LISTE_INST(a.getFils2());
		Prog.ajouter(E_cond);
		coder_CMP_BNE(a.getFils1(), 1, E_debut);

	}

	private void coder_Pour(Arbre a) {
		/*
		 * int x = ...; for (unsigned i = 0; i < 5; i ++) { x = x + 4; }
		 * 
		 * movl $0, %ecx for: cmpl $5, %ecx jae fin_for addl $4, %eax addl $1, %ecx jmp
		 * for fin_for:
		 */
		// TODO Auto-generated method stub

		boolean Increment = (a.getFils1().getNoeud().equals(Noeud.Increment));
		Etiq boucle_for = Etiq.nouvelle("for");
		Etiq fin_boucle_for = Etiq.nouvelle("fin_for");
		int val_compteur = 0;
		int val_fin_compteur = 0;
		// Recupere valeur fin compteur : a.getFils1.getFils3
		coder_EXP(a.getFils1().getFils3(), R0);

		/* Ecrire dans val_fin_compteur la valeur dans R0 */

		// Recupere valeur debut compteur : a.getFils1.getFils2
		coder_EXP(a.getFils1().getFils2(), R0); // on met la valeur du debut de compteur dans R0

		Prog.ajouter(boucle_for);

		Prog.ajouter(Inst.creation2(Operation.CMP, Operande.creationOpEntier(val_fin_compteur), Operande.opDirect(R0)));
		if (Increment) {
			Prog.ajouter(Inst.creation1(Operation.BGE, Operande.creationOpEtiq(fin_boucle_for)));
		} else {
			Prog.ajouter(Inst.creation1(Operation.BLE, Operande.creationOpEtiq(fin_boucle_for)));
		}

		coder_LISTE_INST(a.getFils2());

		// On incremente ou décremente la valeur du registre
		if (Increment) {
			Prog.ajouter(Inst.creation2(Operation.ADD, Operande.creationOpEntier(1), Operande.opDirect(R0)));
		} else {
			Prog.ajouter(Inst.creation2(Operation.SUB, Operande.creationOpEntier(1), Operande.opDirect(R0)));
		}
		Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(boucle_for)));
	}

	private void coder_Affect(Arbre a) {
		// TODO Auto-generated method stub
		coder_PLACE(a.getFils1());//Emplacement de la variable dans R0
		coder_EXP(a.getFils2(),R1);//valeur de l'affect dans R1
		
	}

	/**
	 * Procédure de génération de code Génère du code pour l’expression A tel que
	 * l’expression soit --évaluée dans le registre Rc. --Précondition: le registre
	 * Rc est alloué. procedure Coder_Exp (A : Arbre; Rc : Registre)
	 */

	public void coder_EXP(Arbre a, Registre rc) { // Champey & Clémentin

	}

	/**
	 * coder_CMP_BNE : Fonction permettant de coder une condition Avec a l'arbre
	 * utilisé Val la valeur attendue de la comparaison : 1 - True, 0 - False etiq
	 * :l'etiquette sur laquelle le branchement est effectué
	 */
	private void coder_CMP_BNE(Arbre a, int val, Etiq etiq) {
		coder_EXP(a, R0);
		Inst.creation2(Operation.CMP, Operande.creationOpEntier(val), Operande.opDirect(R0));// on test si la condition
		// est vrai ou fausse avec CMP
		Inst.creation1(Operation.BNE, Operande.creationOpEtiq(etiq));// Si c'est NE alors on branch à l'étiquette
		// etiq ( on fait un saut)
	}

}
