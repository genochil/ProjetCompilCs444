package fr.esisar.compilation.gencode;

import java.util.HashMap;
import java.util.Map;

import fr.esisar.compilation.global.src.Arbre;
import fr.esisar.compilation.global.src3.Inst;
import fr.esisar.compilation.global.src3.Operande;
import fr.esisar.compilation.global.src3.Operation;
import fr.esisar.compilation.global.src3.Prog;

public class Variable {

	private static int taille = 0;
	private static Map<String, Integer> variable = new HashMap<String, Integer>();

	public static void add_var(Arbre a) {
		switch (a.getDecor().getType().getNature()) {
		case Boolean:
		case Interval:
		case Real:
			taille++;
			variable.put(a.getChaine().toLowerCase(), taille);
			break;
		case Array:
			variable.put(a.getChaine().toLowerCase(), taille + 1);
			int size_array = 0;
			// calculer taille du tableau
			taille += size_array;
			break;
		default:
			break;
		}
	}
	
	public static int add_new_var()
	{
		taille++;
		Prog.ajouter(Inst.creation1(Operation.ADDSP, Operande.creationOpEntier(1)));
		Prog.ajouter(Inst.creation1(Operation.TSTO, Operande.creationOpEntier(taille)));
		//Prog.ajouter(Inst.creation1(Operation.BOV, Operande.creationOpEtiq())));
		return taille;
	}

	// renvoie la position de la variable : GB + X
	public static int get_var(String var) {
		return variable.get(var);
	}

	public static int getTaille() {
		return taille;
	}

	public static Map<String, Integer> getVariable() {
		return variable;
	}

}
