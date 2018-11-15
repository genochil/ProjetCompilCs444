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
			// ajouter valeur de l'indent en pile
			return;
		default:
		}
	}

	public void coder_PLACE(Arbre a, Registre rc) {// Couvi, a faire en premier

	}

	public void coder_INST(Arbre a) {// Couvi
		switch (a.getNoeud()) {
		case Ecriture:
			coder_Ecriture(a);
			break;
		case Ligne:
			Prog.ajouterComment("newligne, ligne :" + a.getNumLigne());
			Prog.ajouter(Inst.creation0(Operation.WNL));
			Prog.ajouterComment("Apres newligne, ligne :" + a.getNumLigne());
			break;
		case Nop:
			return;
		case Affect:
			coder_Affect(a);
			return;
		case Pour:
			coder_Pour(a);
			return;
		case TantQue:
			coder_TantQue(a);
			return;
		case Si:
			coder_Si(a);
			return;
		case Lecture:
			coder_Lecture(a);
			return;
		default:
			break;
		}
	}

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

	private void coder_Lecture(Arbre a) {
		// TODO Auto-generated method stub

	}

	private void coder_Si(Arbre a) {
		/*
		 * Le code ci dessous permet de coder un Si en assembleur qui est representé par : 
		 * 
		 * 		CMP R0
		 * 		BNE false
		 * 		--code du corps du si--
		 * 		BRA fin
		 * faux :
		 * 		 --code du else--
		 * fin :
		 * 		--suite du code--
		 * 
		 */
		Etiq not_equal = Etiq.nouvelle("False");
		Etiq fin = Etiq.nouvelle("FinSi");
		coder_EXP(a, R0); // on met dans R0, le résultat de la condition du Si, on renvoie 1 si elle est
							// vraie, 0 sinon
		Inst.creation2(Operation.CMP, Operande.creationOpEntier(1), Operande.opDirect(R0));// on test si la condition
																							// est vrai avec CMP
		Inst.creation1(Operation.BNE, Operande.creationOpEtiq(not_equal));// Si c'est NE alors on branch à l'étiquette
																			// not_equal ( on fait un saut)
		coder_LISTE_INST(a.getFils2()); // on code le "corps" du si

		Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(fin)));// Si on a executé le corps du Si
																					// alors on doit jump a la fin et ne
																					// pas executer le potentiel else,
																					// on écrit donc un branch qui jump
																					// a l'étiquette "fin", qui
																					// representela fin du Si

		Prog.ajouter(not_equal);// Une fois le code du corps ecrit, on ecrit notre etiquette sur laquelle on
								// jump si la condition est fausse, donc si la condition est fausse, on saute
								// bien le code du Si
		coder_LISTE_INST(a.getFils3());// On coder le corps du Else (qu'il soit vide ou non)
		Prog.ajouter(fin);// On ecrit la fin de notre si via l'étiquette crée plus haut
	}
	/*Coder_Inst(Noeud_Tantque(C, I)) =
		declare
		E_Cond : Etiq := Nouvelle_Etiq;
		E_Début : Etiq := Nouvelle_Etiq;
		begin
		Générer(BRA, E_Cond);
		Générer_Etiq(E_Début);
		Coder_Inst(I);
		Générer_Etiq(E_Cond);
		Coder_Cond(C, True, E_Début)
		end
		;*/
	private void coder_TantQue(Arbre a) {
		// TODO Auto-generated method stub
		Etiq E_cond = Etiq.nouvelle("E_cond");
		Etiq E_debut = Etiq.nouvelle("E_debut");
		Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(E_cond)));
		Prog.ajouter(E_debut);
		coder_INST(a.getFils2());
		Prog.ajouter(E_cond);
		coder_CMP_BNE(a.getFils1(),1,E_debut);
		
		
	}

	private void coder_Pour(Arbre a) {
		// TODO Auto-generated method stub

	}

	private void coder_Affect(Arbre a) {
		// TODO Auto-generated method stub

	}
	/*Fonction permettant de coder une condition 
	 * Avec a l'arbre utilisé
	 * Val la valeur attendue de la comparaison : 1 - True, 0 - False
	 * etiq : l'etiquette sur laquelle le branchement est effectué
	 * */
	private void coder_CMP_BNE(Arbre a,int val, Etiq etiq)
	{
		coder_EXP(a,R0);
		Inst.creation2(Operation.CMP, Operande.creationOpEntier(val), Operande.opDirect(R0));// on test si la condition
		// est vrai ou fausse avec CMP
		Inst.creation1(Operation.BNE, Operande.creationOpEtiq(etiq));// Si c'est NE alors on branch à l'étiquette
		// not_equal ( on fait un saut)
	}

	/*
	 * Procédure de génération de code Génère du code pour l’expression A tel que
	 * l’expression soit --évaluée dans le registre Rc. --Précondition: le registre
	 * Rc est alloué. procedure Coder_Exp (A : Arbre; Rc : Registre)
	 */

	
	public void coder_EXP(Arbre a, Registre rc) { // Champey & Clémentin
		
		Noeud n = a.getNoeud();
		// Si a est une feuille de l'arbre
		if(n==Noeud.Vide || n==Noeud.Chaine || n==Noeud.Entier || n==Noeud.Reel || n==Noeud.Ident)
		{
			Prog.ajouterComment("LOAD, ligne :" + a.getNumLigne());
			coder_EXP_feuille(a, rc, Operation.LOAD);
			Prog.ajouterComment("fin LOAD, ligne :" + a.getNumLigne());
			return;
		}
		
		n = a.getFils2().getNoeud();
		// Si a est une opération et que le fils droit est une feuille de l'arbre
		if(n==Noeud.Vide || n==Noeud.Chaine || n==Noeud.Entier || n==Noeud.Reel || n==Noeud.Ident)
		{
			coder_EXP(a.getFils1(), rc);
			switch(a.getNoeud())
			{
			// Opérations arithmétiques à deux fils
			case Plus:
				Prog.ajouterComment("PLUS, ligne :" + a.getNumLigne());
				coder_EXP_feuille(a.getFils2(), rc, Operation.ADD);
				Prog.ajouterComment("fin PLUS, ligne :" + a.getNumLigne());
				break;
			case Moins:
				Prog.ajouterComment("MOINS, ligne :" + a.getNumLigne());
				coder_EXP_feuille(a.getFils2(), rc, Operation.SUB);
				Prog.ajouterComment("fin MOINS, ligne :" + a.getNumLigne());
				break;
			case Mult:
				Prog.ajouterComment("MULT, ligne :" + a.getNumLigne());
				coder_EXP_feuille(a.getFils2(), rc, Operation.MUL);
				Prog.ajouterComment("fin MULT, ligne :" + a.getNumLigne());
				break;
			case DivReel:
				Prog.ajouterComment("DIVREEL, ligne :" + a.getNumLigne());
				coder_EXP_feuille(a.getFils2(), rc, Operation.DIV);
				Prog.ajouterComment("fin DIVREEL, ligne :" + a.getNumLigne());
				break;
			case Reste:
				Prog.ajouterComment("RESTE, ligne :" + a.getNumLigne());
				coder_EXP_feuille(a.getFils2(), rc, Operation.MOD);
				Prog.ajouterComment("fin RESTE, ligne :" + a.getNumLigne());
				break;
			case Quotient:
				Prog.ajouterComment("QUOTIENT, ligne :" + a.getNumLigne());
				coder_EXP_feuille(a.getFils2(), rc, Operation.DIV);
				Prog.ajouterComment("fin QUOTIENT, ligne :" + a.getNumLigne());
				break;
			
			// Opérations arithmétiques à un fils
			case PlusUnaire:
				Prog.ajouterComment("PLUSUNAIRE, ligne :" + a.getNumLigne());
				coder_EXP(a.getFils1(), rc);
				Prog.ajouterComment("fin PLUSUNAIRE, ligne :" + a.getNumLigne());
				break;
			case MoinsUnaire:
				Prog.ajouterComment("MOINSUNAIRE, ligne :" + a.getNumLigne());
				coder_EXP(a.getFils1(), rc);
				Prog.ajouter(Inst.creation2(Operation.OPP, Operande.opDirect(rc), Operande.opDirect(rc)));
				Prog.ajouterComment("fin MOINSUNAIRE, ligne :" + a.getNumLigne());
				break;
			case Conversion:
				
			// Opérations logiques à deux fils
			case Et:
				Prog.ajouterComment("ET, ligne :" + a.getNumLigne());
				coder_ET(a, rc);
				Prog.ajouterComment("fin ET, ligne :" + a.getNumLigne());
				return;
			case Ou:
				Prog.ajouterComment("OU, ligne :" + a.getNumLigne());
				coder_OU(a, rc);
				Prog.ajouterComment("fin OU, ligne :" + a.getNumLigne());
				return;
			case Egal:
			case NonEgal:
			case Sup:
			case SupEgal:
			case Inf:
			case InfEgal:
				
			// Opérations logiques à un fils
			case Non:
			
			default:
				break;
			}
		}

	}

	public void coder_EXP_feuille(Arbre a, Registre rc, Operation operation)
	{
		switch (a.getNoeud())
		{
			case Vide:
				return;
			case Chaine:
				Prog.ajouter(Inst.creation2(operation, Operande.creationOpChaine(a.getChaine()), Operande.opDirect(rc)));
				return;
			case Entier:
				Prog.ajouter(Inst.creation2(operation, Operande.creationOpEntier(a.getEntier()), Operande.opDirect(rc)));
				return;
			case Reel:
				Prog.ajouter(Inst.creation2(operation, Operande.creationOpReel(a.getReel()), Operande.opDirect(rc)));
				return;
			case Ident:
				switch(a.getChaine().toLowerCase())
				{
					case "max_int":
						Prog.ajouter(Inst.creation2(operation, Operande.creationOpEntier(java.lang.Integer.MAX_VALUE), Operande.opDirect(rc)));
						return;
					case "true":
						Prog.ajouter(Inst.creation2(operation, Operande.creationOpEntier(1), Operande.opDirect(rc)));
						return;
					case "false":
						Prog.ajouter(Inst.creation2(operation, Operande.creationOpEntier(0), Operande.opDirect(rc)));
						return;
					default:
						/*
						 * A COMPLETER 
						 */
						return;
				}
			default:
				// cas ne pouvant pas être atteint
				return;
				
		}
	}
	
	public void coder_ET(Arbre a, Registre rc)
	{
		Etiq e1 = Etiq.nouvelle("finET");
		Etiq e2 = Etiq.nouvelle("returnFalse");
		// RC contient la valeur du fils1
		
		/* CMP RC, #0      */Prog.ajouter(Inst.creation2(Operation.CMP, Operande.creationOpEntier(0), Operande.opDirect(rc)));
		/* BEQ returnFalse */Prog.ajouter(Inst.creation1(Operation.BEQ, Operande.creationOpEtiq(e2)));
		
		coder_EXP(a.getFils2(), rc); // On met la valeur du fils2 dans RC
		
		/* CMP RC, #0      */Prog.ajouter(Inst.creation2(Operation.CMP, Operande.creationOpEntier(0), Operande.opDirect(rc)));
		/* SNE RC          */Prog.ajouter(Inst.creation1(Operation.SNE, Operande.opDirect(rc)));
		/* BRA finET       */Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(e1)));
		
		/* returnFalse:    */Prog.ajouter(e2);
		/* LOAD #0, RC     */Prog.ajouter(Inst.creation2(Operation.LOAD, Operande.creationOpEntier(0), Operande.opDirect(rc)));
		
		/* finET:          */Prog.ajouter(e1);
		
		return;
	}
	
	public void coder_OU(Arbre a, Registre rc)
	{
		Etiq e1 = Etiq.nouvelle("finOU");
		Etiq e2 = Etiq.nouvelle("returnTrue");
		// RC contient la valeur du fils1
		
		/* CMP RC, #0      */Prog.ajouter(Inst.creation2(Operation.CMP, Operande.creationOpEntier(0), Operande.opDirect(rc)));
		/* BNE returnTrue */Prog.ajouter(Inst.creation1(Operation.BNE, Operande.creationOpEtiq(e2)));
		
		coder_EXP(a.getFils2(), rc); // On met la valeur du fils2 dans RC
		
		/* CMP RC, #0      */Prog.ajouter(Inst.creation2(Operation.CMP, Operande.creationOpEntier(0), Operande.opDirect(rc)));
		/* BNE returnTrue  */Prog.ajouter(Inst.creation1(Operation.BNE, Operande.creationOpEtiq(e2)));
		/* LOAD #0, RC     */Prog.ajouter(Inst.creation1(Operation.LOAD, Operande.creationOpEtiq(e1)));
		/* BRA finOU       */Prog.ajouter(Inst.creation1(Operation.BRA, Operande.creationOpEtiq(e1)));
		/* returnTrue:     */Prog.ajouter(e2);
		/* LOAD #1, RC     */Prog.ajouter(Inst.creation2(Operation.LOAD, Operande.creationOpEntier(1), Operande.opDirect(rc)));
		
		/*finOU:           */Prog.ajouter(e1);
		
		return;
	}
}
