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
	private void verifier_LISTE_INST(Arbre a) throws ErreurVerif, ErreurInterneVerif {
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
			verifier_Affect(a);
			return;
		case Pour:
			verifier_Pour(a);
			return;
		case TantQue:
			verifier_TantQue(a);
			return;
		case Si:
			verifier_Si(a);
			return;
		case Ecriture:
			verifier_Ecriture(a);
			return;
		case Lecture:
			verifier_Lecture(a);
			return;
		case Ligne:
			return;
		default:
			throw new ErreurInterneVerif("inst : " + a.getNumLigne());
		}

	}

	private void verifier_Affect(Arbre a) throws ErreurVerif {
		verifier_PLACE(a.getFils1());
		verifier_EXP(a.getFils2());

		Type t1 = a.getFils1().getDecor().getType();
		Type t2 = a.getFils2().getDecor().getType();
		ResultatAffectCompatible rac = ReglesTypage.affectCompatible(t1, t2);
		if (rac.getOk()) {
			if (rac.getConv2()) {
				Arbre arb = Arbre.creation1(Noeud.Conversion, a.getFils2(), a.getFils2().getNumLigne());
				a.setFils2(arb);
				a.getFils2().setDecor(new Decor(Type.Real));
			}
			a.setDecor(new Decor(t1));
		} else {
			ErreurContext err = ErreurContext.TypesIncompatibles;
			err.leverErreurContext("Type :" + t1.toString() + "," + t2.toString() + "non compatibles", a.getNumLigne());
		}
	}

	private void verifier_Pour(Arbre a) throws ErreurVerif {
		System.out.println("a : "+a.getFils1().getFils1().getChaine());
		verifier_PAS(a.getFils1());
		verifier_LISTE_INST(a.getFils2());
	}

	private void verifier_Si(Arbre a) throws ErreurVerif {
		verifier_EXP(a.getFils1());
		Type exp_type_si = a.getFils1().getDecor().getType();
		if (!(exp_type_si.equals(Type.Boolean))) {
			ErreurContext erreur = ErreurContext.TypesIncompatibles;
			erreur.leverErreurContext("Type " + exp_type_si + "non valide, type attendu : Boolean",
					a.getFils1().getNumLigne());
		}
		verifier_LISTE_INST(a.getFils2());
		verifier_LISTE_INST(a.getFils3());
	}

	private void verifier_TantQue(Arbre a) throws ErreurVerif {
		verifier_EXP(a.getFils1());
		
		Type exp_type_tq = a.getFils1().getDecor().getType();
		if (!(exp_type_tq.equals(Type.Boolean))) {
			ErreurContext erreur = ErreurContext.TypesIncompatibles;
			erreur.leverErreurContext("Type " + exp_type_tq + "non valide, type attendu : Boolean",
					a.getFils1().getNumLigne());
		}
		verifier_LISTE_INST(a.getFils2());
	}

	private void verifier_Ecriture(Arbre a) throws ErreurVerif {
		verifier_LISTE_EXP(a.getFils1());
		Arbre parcours = a.getFils1();
		while (parcours.getNoeud() != Noeud.Vide) {
			Type exp_type_w = parcours.getFils2().getDecor().getType();
			if (!(exp_type_w instanceof TypeInterval) && !(exp_type_w.equals(Type.String))
					&& !(exp_type_w.equals(Type.Real))) {
				ErreurContext erreur = ErreurContext.TypesIncompatibles;
				erreur.leverErreurContext("Type " + exp_type_w + "non valide, type attendu : Interval, Real ou String",
						parcours.getNumLigne());
			}
			parcours = parcours.getFils1();
		}
	}

	private void verifier_Lecture(Arbre a) throws ErreurVerif {
		verifier_PLACE(a.getFils1());
		Type exp_type_r = a.getFils1().getDecor().getType();
		if (!(exp_type_r instanceof TypeInterval) && !(exp_type_r.equals(Type.Real))) {
			ErreurContext erreur = ErreurContext.TypesIncompatibles;
			erreur.leverErreurContext("Type " + exp_type_r + "non valide, type attendu : Interval ou Real",
					a.getFils1().getNumLigne());
		}
	}

	/**************************************************************************
	 * PLACE
	 **************************************************************************/

	private void verifier_PLACE(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Ident:
			verifier_IDF(a, NatureDefn.Var);
			return;
		case Index:
			verifier_Index(a);
			return;
		default:
			throw new ErreurInterneVerif("place : " + a.getNumLigne());
		}

	}

	private void verifier_Index(Arbre a) throws ErreurVerif {
		verifier_PLACE(a.getFils1());
		
		Type place_type_index = a.getFils1().getDecor().getType();
		if (!(place_type_index instanceof TypeArray)) {
			ErreurContext erreur = ErreurContext.NotArrayIndexation;
			erreur.leverErreurContext("", a.getFils1().getNumLigne());
		}
		System.out.println("Type : "+place_type_index);
		Type array_type = ((TypeArray)(a.getFils1().getDecor().getType())).getElement();
		a.setDecor(new Decor(array_type)) ;
		
		verifier_EXP(a.getFils2());
		Type exp_type_index = a.getFils2().getDecor().getType();
		if (!(exp_type_index instanceof TypeInterval)) {
			ErreurContext erreur = ErreurContext.WrongArrayIndex;
			erreur.leverErreurContext("Type :" + exp_type_index + " incorrect, type Interval attendu",
					a.getFils2().getNumLigne());
		}
	}

	/**************************************************************************
	 * IDF
	 * 
	 * @throws ErreurVerif
	 **************************************************************************/

	private void verifier_IDF(Arbre a, NatureDefn var) throws ErreurVerif {
		// TODO Auto-generated method stub
		Defn inEnv = env.chercher(a.getChaine().toLowerCase());
		// On recherche la définition liée a notre arbre afin de vérifier si cette
		// dernière est comprise dans l'environnement définit plus haut
		if (inEnv == null) {
			ErreurContext erreur = ErreurContext.MissingDeclaration;
			erreur.leverErreurContext(a.getChaine(), a.getNumLigne());
			return;
		}
		a.setDecor(new Decor(inEnv, inEnv.getType()));
		// On redéfinit le decor de notre arbre a selon la définition récupéré
		// précédement
		NatureDefn nature = a.getDecor().getDefn().getNature(); // on récupère la nature de notre nouveau Decor
		if (!nature.equals(var)) {
			ErreurContext erreur = ErreurContext.WrongNatureId;
			erreur.leverErreurContext(a.getChaine(), a.getNumLigne());
		}

	}

	/**************************************************************************
	 * LISTE_EXP
	 **************************************************************************/

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

	/**************************************************************************
	 * PAS
	 **************************************************************************/
	private void verifier_PAS(Arbre a) throws ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Decrement:
		case Increment:
			verifier_Decrement_Increment(a);
			return;
		default:
			throw new ErreurInterneVerif("Pas : " + a.getNumLigne());
		}

	}

	private void verifier_Decrement_Increment(Arbre a) throws ErreurVerif {
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
	}

	/**************************************************************************
	 * CONSTANTE
	 * 
	 * @throws ErreurVerif
	 **************************************************************************/

	private void verifier_CONSTANTE(Arbre a) throws ErreurVerif {
		switch (a.getNoeud()) {
		case MoinsUnaire:
			verifier_CONSTANTE(a.getFils1());
			a.setDecor(new Decor(a.getFils1().getDecor().getType()));
			return;
		case PlusUnaire:
			verifier_CONSTANTE(a.getFils1());
			a.setDecor(new Decor(a.getFils1().getDecor().getType()));
			return;
		case Entier: 
			a.setDecor(new Decor(Type.Integer));
			return;
		case Ident:
			verifier_IDF(a, NatureDefn.ConstInteger);
			return;
		default:
			throw new ErreurInterneVerif("Constante : " + a.getNumLigne());

		}
	}

	/**************************************************************************
	 * EXP
	 **************************************************************************/

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
			verifier_EXP_Binaire(a);
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
			verifier_EXP_Unaire(a);
			return;
		default:
			throw new ErreurInterneVerif("Exp : " + a.getNumLigne());
		}
	}

	private void verifier_EXP_Binaire(Arbre a) throws ErreurVerif {
		verifier_EXP(a.getFils1());
		verifier_EXP(a.getFils2());
		Type t1 = a.getFils1().getDecor().getType();
		Type t2 = a.getFils2().getDecor().getType();
		ResultatBinaireCompatible rbc = ReglesTypage.binaireCompatible(a.getNoeud(), t1, t2);
		if (rbc.getOk()) {
			if (rbc.getConv1()) {
				Arbre arb = Arbre.creation1(Noeud.Conversion, a.getFils1(), a.getFils1().getNumLigne());
				a.setFils1(arb);
				a.getFils1().setDecor(new Decor(Type.Real));
			} else if (rbc.getConv2()) {
				Arbre arb = Arbre.creation1(Noeud.Conversion, a.getFils2(), a.getFils2().getNumLigne());
				a.setFils2(arb);
				a.getFils2().setDecor(new Decor(Type.Real));
			}
			a.setDecor(new Decor(rbc.getTypeRes()));
		} else {
			ErreurContext erreur = ErreurContext.TypesIncompatibles;
			erreur.leverErreurContext("Type :" + t1.toString() + "," + t2.toString() + "non compatibles",
					a.getNumLigne());
		}
	}

	private void verifier_EXP_Unaire(Arbre a) throws ErreurVerif {
		verifier_EXP(a.getFils1());
		Type t = a.getFils1().getDecor().getType();
		ResultatUnaireCompatible ruc = ReglesTypage.unaireCompatible(a.getNoeud(), t);
		if (ruc.getOk()) {
			a.setDecor(new Decor(ruc.getTypeRes()));
		} else {
			ErreurContext erreur = ErreurContext.TypesIncompatibles;
			erreur.leverErreurContext("Type : " + t.toString() + " non compatible", a.getNumLigne());

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

	/**************************************************************************
	 * LISTE_IDF
	 * 
	 * @throws ErreurVerif
	 **************************************************************************/

	private void verifier_LISTE_IDF(Arbre a, Type type) throws ErreurInterneVerif, ErreurVerif {
		// TODO Auto-generated method stub
		switch (a.getNoeud()) {
		case Vide:
			return;
		case ListeIdent:
			verifier_Liste_Ident(a, type);
			return;
		default:
			throw new ErreurInterneVerif("Liste_idf : " + a.getNumLigne());
		}
	}

	private void verifier_Liste_Ident(Arbre a, Type type) throws ErreurVerif {
		verifier_LISTE_IDF(a.getFils1(), type);
		Boolean decl_existe = env.enrichir(a.getFils2().getChaine().toLowerCase(), Defn.creationVar(type));
		// On tente d'ajouter la déclaration dans l'environnment, si elle existe déja
		// enrichir() retourne true
		if (decl_existe) {
			ErreurContext erreur = ErreurContext.DoubleDeclarationId;
			erreur.leverErreurContext(a.getFils2().getChaine(), a.getFils2().getNumLigne());

		}
		a.getFils2().setDecor(new Decor(Defn.creationVar(type), type));
		verifier_IDF(a.getFils2(), NatureDefn.Var);
	}

	/**************************************************************************
	 * TYPE
	 * 
	 * @throws ErreurVerif
	 **************************************************************************/

	private Type verifier_TYPE(Arbre a) throws ErreurVerif {
		switch (a.getNoeud()) {
		case Ident:
			verifier_IDF(a, NatureDefn.Type);
			Defn temp = env.chercher(a.getChaine().toLowerCase());
			return temp.getType();
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

	/**************************************************************************
	 * TABLEAU
	 * 
	 * @throws ErreurVerif
	 **************************************************************************/

	private Type verifier_TABLEAU(Arbre a) throws ErreurVerif {
		Type typeIndice = verifier_INTERVALLE(a.getFils1());
		Type typeElement = verifier_TYPE(a.getFils2());
		Type tab = Type.creationArray(typeIndice, typeElement);
		a.setDecor(new Decor(tab));
		return tab;
	}

	/**************************************************************************
	 * INTERVALLE
	 * 
	 * @throws ErreurVerif
	 **************************************************************************/

	private Type verifier_INTERVALLE(Arbre a) throws ErreurVerif {
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
			borneSup = -a.getFils2().getFils1().getEntier();
		} else if (a.getFils2().getNoeud().equals(Noeud.PlusUnaire)) {
			borneSup = a.getFils2().getFils1().getEntier();
		} else {
			borneSup = a.getFils2().getEntier();
		}
		Type inter = Type.creationInterval(borneInf, borneSup);
		a.setDecor(new Decor(inter));
		return inter;
	}

	/**************************************************************************
	 * FACTEUR
	 **************************************************************************/

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
			Defn Ident_definition = env.chercher(a.getChaine().toLowerCase());
			if (Ident_definition == null) {
				ErreurContext erreur = ErreurContext.MissingDeclaration;
				erreur.leverErreurContext(a.getChaine(), a.getNumLigne());

			}
			NatureDefn nature = Ident_definition.getNature();
			if (nature != NatureDefn.Var && nature != NatureDefn.ConstInteger && nature != NatureDefn.ConstBoolean) {
				ErreurContext erreur = ErreurContext.WrongNatureId;
				erreur.leverErreurContext(
						"Nature Inconrrect : " + nature + " Attendue : Var, ConstInteger ou ConstBoolean",
						a.getNumLigne());

			}
			a.setDecor(new Decor(Ident_definition, Ident_definition.getType()));
			return;
		default:
			throw new ErreurInterneVerif("Facteur : " + a.getNumLigne());
		}
	}
}
