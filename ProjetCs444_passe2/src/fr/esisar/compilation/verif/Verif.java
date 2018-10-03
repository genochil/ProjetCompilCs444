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
	private void verifier_LISTE_INST(Arbre a) throws ErreurVerif,ErreurInterneVerif {
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeInst:
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
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + exp_type_tq + "non valide, type attendu : Boolean",
						a.getFils1().getNumLigne());
			}
			return;
		case Si:
			verifier_EXP(a.getFils1());
			Type exp_type_si = a.getFils1().getDecor().getDefn().getType();
			if (!(exp_type_si.equals(Type.Boolean))) {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + exp_type_si + "non valide, type attendu : Boolean",
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
					ErreurContext erreur = ErreurContext.TypesIncompatibles;
					erreur.leverErreurContext(
							"Type " + exp_type_w + "non valide, type attendu : Interval, Real ou String",
							a.getFils1().getNumLigne());
				}
			}
			return;
		case Lecture:
			verifier_PLACE(a.getFils1());
			Type exp_type_r = a.getFils1().getDecor().getDefn().getType();
			if (!(exp_type_r instanceof TypeInterval) && !(exp_type_r.equals(Type.Real))) {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + exp_type_r + "non valide, type attendu : Interval ou Real",
						a.getFils1().getNumLigne());
			}
			return;
		case Ligne:
			return;
		default:
			throw new ErreurInterneVerif("inst : " + a.getNumLigne());
		}

	}

	private void verifier_PLACE(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Ident:
			verifier_IDF(a.getFils1(), NatureDefn.Var);
			return;
		case Index:
			verifier_PLACE(a.getFils1());
			Type place_type_index = a.getFils1().getDecor().getDefn().getType();
			if (!(place_type_index instanceof TypeArray)) {
				ErreurContext erreur = ErreurContext.IndexTypeIncorrect;
				erreur.leverErreurContext("", a.getFils1().getNumLigne());
			}
			verifier_EXP(a.getFils2());
			Type exp_type_index = a.getFils1().getDecor().getDefn().getType();
			if (!(exp_type_index instanceof TypeInterval)) {
				ErreurContext erreur = ErreurContext.IndexTypeIncorrect;
				erreur.leverErreurContext("Type :" + exp_type_index + " incorrect, type Interval attendu",
						a.getFils2().getNumLigne());
			}
			return;
		default:
			throw new ErreurInterneVerif("place : " + a.getNumLigne());
		}

	}

	private void verifier_IDF(Arbre a, NatureDefn var) {
		// TODO Auto-generated method stub
		Defn inEnv = env.chercher(a.getChaine().toLowerCase());
		// On recherche la définition liée a notre arbre afin de vérifier si cette
		// dernière est comprise dans l'environnement définit plus haut
		if (inEnv == null) {
			// erreureurContext identinconnu
		}
		a.setDecor(new Decor(inEnv, inEnv.getType()));
		// On redéfinit le decor de notre arbre a selon la définition récupéré
		// précédement
		NatureDefn nature = a.getDecor().getDefn().getNature(); // on récupère la nature de notre nouveau Decor
		if (!nature.equals(var)) {
			// erreureurContext MauvaiseNature
		}

	}

	private void verifier_LISTE_EXP(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeExp:
			verifier_LISTE_EXP(a.getFils1());
			verifier_EXP(a.getFils2());
			return;

		default:
			throw new ErreurInterneVerif("Liste_EXP : " + a.getNumLigne());
		}

	}

	// ------------------------------------------------------------------------
	// COMPLETER les operations de vérifications et de décoration pour toutes
	// les constructions d'arbres
	// ------------------------------------------------------------------------

	private void verifier_PAS(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Decrement:
		case Increment:
			verifier_IDF(a.getFils1(), NatureDefn.Var);
			Type type_idf = a.getFils1().getDecor().getType();
			if (!(type_idf instanceof TypeInterval)) {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + type_idf + "non valide, type attendu : Interval",
						a.getFils1().getNumLigne());
			}
			verifier_EXP(a.getFils2());
			Type type_exp1 = a.getFils2().getDecor().getType();
			if (!(type_exp1 instanceof TypeInterval)) {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + type_exp1 + "non valide, type attendu : Interval",
						a.getFils2().getNumLigne());
			}
			verifier_EXP(a.getFils3());
			Type type_exp2 = a.getFils3().getDecor().getType();
			if (!(type_exp2 instanceof TypeInterval)) {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + type_exp2 + "non valide, type attendu : Interval",
						a.getFils3().getNumLigne());
			}
			return;
		default:
			throw new ErreurInterneVerif("Pas : " + a.getNumLigne());
		}

	}

	private void verifier_CONSTANTE(Arbre a) {
		switch (a.getNoeud()) {
		case MoinsUnaire:
		case PlusUnaire:
			verifier_CONSTANTE(a.getFils1());
			a.setDecor(new Decor(a.getFils1().getDecor().getType()));
			return;
		case Entier: // CONST_ENT
			a.setDecor(new Decor(Type.Integer));
		case Ident:
			verifier_IDF(a, NatureDefn.ConstInteger);
			return;
		default:
			throw new ErreurInterneVerif("Constante : " + a.getNumLigne());

		}
	}

	private void verifier_EXP(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Et:
		case Ou:
		case Egal:
		case InfEgal:
		case SupEgal:
		case NonEgal:
		case Inf:
		case Sup:
		case Plus:
		case Moins:
		case Mult:
		case DivReel:
		case Reste:
		case Quotient:
			verifier_EXP(a.getFils1());
			verifier_EXP(a.getFils2());
			Type t1 = a.getFils1().getDecor().getType();
			Type t2 = a.getFils2().getDecor().getType();
			ResultatBinaireCompatible rbc = ReglesTypage.binaireCompatible(a.getNoeud(), t1, t2);
			if (rbc.getOk()) {
				if (rbc.getConv1()) {
					// arbreAbstrait
				}
				if (rbc.getConv2()) {
					// arbreAbstrait
				}
				a.setDecor(new Decor(rbc.getTypeRes()));
			} else {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type :" + t1.toString() + "," + t2.toString() + "non compatibles",
						a.getNumLigne());
			}

			return;

		// facteur
		case Chaine:
		case Ident:
		case Index:
		case Entier:
		case Reel:
			verifier_FACTEUR(a);
			return;
		case PlusUnaire:
		case MoinsUnaire:
		case Non:
			verifier_EXP(a.getFils1());
			Type t = a.getFils1().getDecor().getType();
			ResultatUnaireCompatible ruc = ReglesTypage.unaireCompatible(a.getNoeud(), t);
			if (ruc.getOk()) {
				a.setDecor(new Decor(ruc.getTypeRes()));
			} else {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type : " + t.toString() + " non compatible", a.getNumLigne());

			}

			return;
		default:
			throw new ErreurInterneVerif("Exp : " + a.getNumLigne());
		}
	}

	/**************************************************************************
	 * DECL
	 **************************************************************************/

	private void verifier_DECL(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		Type type_decl = verifier_TYPE(a.getFils2());
		verifier_LISTE_IDF(a.getFils1(), type_decl);

	}

	private void verifier_LISTE_IDF(Arbre a, Type type) {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeIdent:
			verifier_LISTE_IDF(a.getFils1(), type);
			Boolean decl_existe = env.enrichir(a.getFils1().getChaine().toLowerCase(), Defn.creationVar(type));
			// On tente d'ajouter la déclaration dans l'environnment, si elle existe déja
			// enrichir() retourne true
			if (decl_existe) {
				// error_redefinition_var
			}
			a.getFils2().setDecor(new Decor(Defn.creationVar(type), type));
			verifier_IDF(a.getFils2(), NatureDefn.Var);
			return;
		default:
			throw new ErreurInterneVerif("Liste_idf : " + a.getNumLigne());
		}
	}

	private Type verifier_TYPE(Arbre a) {
		switch (a.getNoeud()) {
		case Ident:
			verifier_IDF(a, NatureDefn.Type);
			return env.chercher(a.getChaine().toLowerCase()).getType();
		case Intervalle:
			Type type_interval = verifier_INTERVALLE(a);
			return type_interval;
		case Tableau:
			Type type_array = verifier_TABLEAU(a);
			return type_array;
		default:
			throw new ErreurInterneVerif("Type : " + a.getNumLigne());
		}
	}

	private Type verifier_TABLEAU(Arbre a) {
		Type typeIndice = verifier_INTERVALLE(a.getFils1());
		Type typeElement = verifier_TYPE(a.getFils2());
		Type tab = Type.creationArray(typeIndice, typeElement);
		a.setDecor(new Decor(tab));
		return tab;
	}

	private Type verifier_INTERVALLE(Arbre a) {
		// min = fils1
		verifier_CONSTANTE(a.getFils1());
		verifier_CONSTANTE(a.getFils2());
		int borneInf;
		if (a.getFils1().getNoeud().equals(Noeud.MoinsUnaire)) {
			borneInf = -a.getFils1().getFils1().getEntier();
		} else if (a.getFils1().getNoeud().equals(Noeud.PlusUnaire)) {
			borneInf = a.getFils1().getFils1().getEntier();
		} else {
			borneInf = a.getFils1().getEntier();
		}
		// max = fils2
		int borneSup;
		if (a.getFils2().getNoeud().equals(Noeud.MoinsUnaire)) {
			borneSup = -a.getFils2().getFils2().getEntier();
		} else if (a.getFils2().getNoeud().equals(Noeud.PlusUnaire)) {
			borneSup = a.getFils2().getFils2().getEntier();
		} else {
			borneSup = a.getFils2().getEntier();
		}
		Type inter = Type.creationInterval(borneInf, borneSup);
		a.setDecor(new Decor(inter));
		return inter;
	}

	private void verifier_FACTEUR(Arbre a) throws ErreurVerif {
		switch (a.getNoeud()) {
		case Reel:
			a.setDecor(new Decor(Type.Real));
			return;
		case Chaine:
			a.setDecor(new Decor(Type.String));
			return;
		case Entier:
			a.setDecor(new Decor(Type.Integer));
			return;
		case Index:
			verifier_PLACE(a);
			return;
		case Ident:
			verifier_PLACE(a);
			// ....?

		default:
			throw new ErreurInterneVerif("Facteur : " + a.getNumLigne());
		}
	}
}
