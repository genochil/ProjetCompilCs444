package fr.esisar.compilation.verif;

import fr.esisar.compilation.global.src.*;

/**
 * La classe ReglesTypage permet de définir les différentes règles de typage du
 * langage JCas.
 */

public class ReglesTypage {

	/**
	 * Teste si le type t1 et le type t2 sont compatibles pour l'affectation, c'est
	 * à dire si on peut affecter un objet de t2 à un objet de type t1.
	 */

	static ResultatAffectCompatible affectCompatible(Type t1, Type t2) {
		ResultatAffectCompatible rac = new ResultatAffectCompatible();

		rac.setConv2(false);

		if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Interval) {
			rac.setOk(true);
			rac.setConv2(true);
			return rac;
		} else if (t1.getNature() == t2.getNature()) {
			if (t1.getNature() == NatureType.Array) {
				if (t1.getIndice().getNature() != t2.getIndice().getNature()
						|| t1.getIndice().getBorneInf() != t2.getIndice().getBorneInf()
						|| t1.getIndice().getBorneSup() != t2.getIndice().getBorneSup()) {
					rac.setOk(false);
					return (rac);
				} else {
					return (affectCompatible(t1.getElement(), t2.getElement()));
				}
			} else {
				rac.setOk(true);
				return (rac);
			}
		} else {
			rac.setOk(false);
			return rac;
		}
	}

	/**
	 * Teste si le type t1 et le type t2 sont compatible pour l'opération binaire
	 * représentée dans noeud.
	 */

	static ResultatBinaireCompatible binaireCompatible(Noeud noeud, Type t1, Type t2) {
		ResultatBinaireCompatible rbc = new ResultatBinaireCompatible();

		rbc.setConv1(false);
		rbc.setConv2(false);

		if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Real) {
			rbc.setConv1(true);
		} else if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Interval) {
			rbc.setConv2(true);
		}

		switch (noeud) {
		case Plus:
		case Moins:
		case Mult:
			if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Real) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Real) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Integer);
				rbc.setOk(true);
			} else {
				rbc.setOk(false);
			}
			return rbc;

		case Et:
		case Ou:
			if (t1.getNature() == NatureType.Boolean && t2.getNature() == NatureType.Boolean) {
				rbc.setTypeRes(Type.Boolean);
				rbc.setOk(true);
			} else {
				rbc.setOk(false);
			}
			return rbc;

		case Sup:
		case SupEgal:
		case Inf:
		case InfEgal:
		case Egal:
		case NonEgal:
			if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Real) {
				rbc.setTypeRes(Type.Boolean);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Real) {
				rbc.setTypeRes(Type.Boolean);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Boolean);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Boolean);
				rbc.setOk(true);
			} else {
				rbc.setOk(false);
			}
			return rbc;
		case Quotient:
		case Reste:
			if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Integer);
				rbc.setOk(true);
			} else {
				rbc.setOk(false);
			}
			return rbc;
		case DivReel:
			if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Real) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Real) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Real && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else if (t1.getNature() == NatureType.Interval && t2.getNature() == NatureType.Interval) {
				rbc.setTypeRes(Type.Real);
				rbc.setOk(true);
			} else {
				rbc.setOk(false);
			}
			return rbc;
		default:
			rbc.setOk(false);
			return rbc;

		}
	}

	/**
	 * Teste si le type t est compatible pour l'opération binaire représentée dans
	 * noeud.
	 */
	static ResultatUnaireCompatible unaireCompatible(Noeud noeud, Type t) {
		ResultatUnaireCompatible ruc = new ResultatUnaireCompatible();

		switch (noeud) {
		case Non:
			if (t.getNature() == NatureType.Boolean) {
				ruc.setTypeRes(Type.Boolean);
				ruc.setOk(true);
			} else {
				ruc.setOk(false);
			}
			return ruc;
		case PlusUnaire:
		case MoinsUnaire:
			if (t.getNature() == NatureType.Interval) {
				ruc.setTypeRes(Type.Integer);
				ruc.setOk(true);
			} else if (t.getNature() == NatureType.Real) {
				ruc.setTypeRes(Type.Real);
				ruc.setOk(true);
			} else {
				ruc.setOk(false);
			}
			return ruc;

		default:
			ruc.setOk(false);
			return ruc;
		}

	}

}
