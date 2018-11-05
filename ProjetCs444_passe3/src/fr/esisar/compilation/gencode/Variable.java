package fr.esisar.compilation.gencode;

import java.util.HashMap;
import java.util.Map;

import fr.esisar.compilation.global.src.Arbre;

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
