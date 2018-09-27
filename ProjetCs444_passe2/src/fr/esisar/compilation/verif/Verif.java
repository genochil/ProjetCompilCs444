package fr.esisar.compilation.verif;

import fr.esisar.compilation.global.src.*;

/**
 * Cette classe permet de réaliser la vérification et la décoration de l'arbre
 * abstrait d'un programme.
 */
public class Verif {

	private Environ env; // L'environnement des identificateurs

	/**
	 * Constructeur.
	 */
	public Verif() {
		env = new Environ();
	}

	/**
	 * Vérifie les contraintes contextuelles du programme correspondant à l'arbre
	 * abstrait a, qui est décoré et enrichi. Les contraintes contextuelles sont
	 * décrites dans Context.txt. En cas d'erreur contextuelle, un message d'erreur
	 * est affiché et l'exception ErreurVerif est levée.
	 */
	public void verifierDecorer(Arbre a) throws ErreurVerif {
		verifier_PROGRAMME(a);
	}

	/**
	 * Initialisation de l'environnement avec les identificateurs prédéfinis.
	 */
	private void initialiserEnv() {
		Defn def;
		// integer
		def = Defn.creationType(Type.Integer);
		def.setGenre(Genre.PredefInteger);
		env.enrichir("integer", def);
		// boolean
		def = Defn.creationType(Type.Boolean);
		def.setGenre(Genre.PredefBoolean);
		env.enrichir("boolean", def);
		// true
		def = Defn.creationConstBoolean(true);
		def.setGenre(Genre.PredefBoolean);
		env.enrichir("true", def);
		// false
		def = Defn.creationConstBoolean(false);
		def.setGenre(Genre.PredefBoolean);
		env.enrichir("false", def);
		// string
		def = Defn.creationType(Type.String);
		env.enrichir("string", def);
		// real
		def = Defn.creationType(Type.Real);
		def.setGenre(Genre.PredefReal);
		env.enrichir("real", def);
		// max_int
		def = Defn.creationConstInteger(java.lang.Integer.MAX_VALUE);
		def.setGenre(Genre.PredefInteger);
		env.enrichir("max_int", def);
	}

	/**************************************************************************
	 * PROGRAMME
	 **************************************************************************/
	private void verifier_PROGRAMME(Arbre a) throws ErreurVerif {
		initialiserEnv();
		verifier_LISTE_DECL(a.getFils1());
		verifier_LISTE_INST(a.getFils2());
	}

	/**************************************************************************
	 * LISTE_DECL
	 **************************************************************************/
	private void verifier_LISTE_DECL(Arbre a) throws ErreurVerif {
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeDecl:
			verifier_LISTE_DECL(a.getFils1());
			verifier_DECL(a.getFils2());
			return;
		default:
			throw new ErreurInterneVerif("Liste decl : " + a.getNumLigne());
		}
	}

	/**************************************************************************
	 * LISTE_INST
	 **************************************************************************/
	private void verifier_LISTE_INST(Arbre a) throws ErreurVerif {
		switch (a.getNoeud()) {
		case Vide:
			;
		case ListeDecl:
			verifier_LISTE_INST(a.getFils1());
			verifier_INST(a.getFils2());
			return;
		default:
			throw new ErreurInterneVerif("Liste inst : " + a.getNumLigne());
		}
	}

	private void verifier_INST(Arbre a) throws ErreurVerif {
		switch (a.getNoeud()) {
		case Nop:
			return;
		case Affect:
			verifier_PLACE(a.getFils1());
			verifier_EXP(a.getFils2());
			// NOEUD CONVERSION A FAIRE !
			return;
		case Pour:
			verifier_PAS(a.getFils1());
			verifier_LISTE_INST(a.getFils2());
			return;
		case TantQue:
			verifier_EXP(a.getFils1());
			verifier_LISTE_INST(a.getFils2());
			Type exp_type_tq = a.getFils1().getDecor().getDefn().getType();
			if (!(exp_type_tq.equals(Type.Boolean))) {
				ErreurContext err = ErreurContext.TypesIncompatibles;
				err.leverErreurContext("Type " + exp_type_tq + "non valide, type attendu : Boolean",
						a.getFils1().getNumLigne());
			}
			return;
		case Si:
			verifier_EXP(a.getFils1());
			Type exp_type_si = a.getFils1().getDecor().getDefn().getType();
			if (!(exp_type_si.equals(Type.Boolean))) {
				ErreurContext err = ErreurContext.TypesIncompatibles;
				err.leverErreurContext("Type " + exp_type_si + "non valide, type attendu : Boolean",
						a.getFils1().getNumLigne());
			}
			verifier_LISTE_INST(a.getFils2());
			verifier_LISTE_INST(a.getFils3());
			return;
		case Ecriture:
			verifier_LISTE_EXP(a.getFils1());
			if (a.getFils1().getNoeud() != Noeud.Vide) {
				Type exp_type_w = a.getFils1().getDecor().getDefn().getType();
				if (!(exp_type_w instanceof TypeInterval) && !(exp_type_w.equals(Type.Real))
						&& !(exp_type_w.equals(Type.String))) {
					ErreurContext err = ErreurContext.TypesIncompatibles;
					err.leverErreurContext("Type " + exp_type_w + "non valide, type attendu : Interval, Real ou String",
							a.getFils1().getNumLigne());
				}
			}
			return;
		case Lecture:
			verifier_LISTE_PLACE(a.getFils1());
			Type exp_type_r = a.getFils1().getDecor().getDefn().getType();
			if (!(exp_type_r instanceof TypeInterval) && !(exp_type_r.equals(Type.Real))) {
				ErreurContext err = ErreurContext.TypesIncompatibles;
				err.leverErreurContext("Type " + exp_type_r + "non valide, type attendu : Interval ou Real",
						a.getFils1().getNumLigne());
			}
			return;
		case Ligne:
			return;
		default:
			throw new ErreurInterneVerif("inst : " + a.getNumLigne());
		}

	}

	private void verifier_LISTE_PLACE(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch(a.getNoeud())
		{
		case Ident :
			verifier_IDF(a.getFils1(),NatureDefn.Var);	
			return;
		case Index:
			verifier_PLACE(a.getFils1());
			Type place_type_index = a.getFils1().getDecor().getDefn().getType();
			if(!(place_type_index instanceof TypeArray))
			{
				ErreurContext err= ErreurContext.IndexTypeIncorrect;
				err.leverErreurContext("", a.getFils1().getNumLigne());
			}
			verifier_EXP(a.getFils2());
			Type exp_type_index = a.getFils1().getDecor().getDefn().getType();
			if(!(exp_type_index instanceof TypeInterval))
			{
				ErreurContext err= ErreurContext.IndexTypeIncorrect;
				err.leverErreurContext("Type :"+exp_type_index+" incorrect, type Interval attendu", a.getFils2().getNumLigne());
			}
			return;
		default:
			throw new ErreurInterneVerif("place : " + a.getNumLigne());
		}

	}

	private void verifier_IDF(Arbre fils1, NatureDefn var) {
		// TODO Auto-generated method stub
		
	}


	private void verifier_LISTE_EXP(Arbre a) {
		// TODO Auto-generated method stub

	}

	// ------------------------------------------------------------------------
	// COMPLETER les operations de vérifications et de décoration pour toutes
	// les constructions d'arbres
	// ------------------------------------------------------------------------

	private void verifier_PAS(Arbre fils1) {
		// TODO Auto-generated method stub

	}

	private void verifier_EXP(Arbre fils2) {
		// TODO Auto-generated method stub

	}

	private void verifier_PLACE(Arbre fils1) {
		// TODO Auto-generated method stub

	}

	/**************************************************************************
	 * DECL
	 **************************************************************************/

	private void verifier_DECL(Arbre fils2) throws ErreurVerif {
		// TODO Auto-generated method stub

	}

}