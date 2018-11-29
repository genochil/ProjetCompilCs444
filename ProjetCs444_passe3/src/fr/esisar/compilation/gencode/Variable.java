package fr.esisar.compilation.gencode;

import java.util.HashMap;
import java.util.Map;


import fr.esisar.compilation.global.src.*;
import fr.esisar.compilation.global.src3.*;
import fr.esisar.compilation.verif.ErreurContext;


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
			int size_array = 1;
			Type array_type=a.getDecor().getType();
			
			while(array_type.getNature().equals(NatureType.Array))
			{
				size_array *=array_type.getIndice().getBorneSup()-array_type.getIndice().getBorneInf()+1;
				array_type=array_type.getElement();
			}
			taille += size_array;
			break;
		default:
			break;
		}
	}
	
	public static int add_new_var()
	{
		Prog.ajouterComment("Debut allocation Pile ");
		taille++;
		Etiq etiq = Etiq.lEtiq("debordement.1");
		Prog.ajouter(Inst.creation1(Operation.TSTO, Operande.creationOpEntier(taille)));
		Prog.ajouter(Inst.creation1(Operation.BOV, Operande.creationOpEtiq(etiq)));
		Prog.ajouter(Inst.creation1(Operation.ADDSP, Operande.creationOpEntier(1)));
		Prog.ajouterComment("Fin allocation Pile");
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
	
	public static void free(int pos) {
		if (pos == taille) {
			taille--;
			Prog.ajouter(Inst.creation1(Operation.SUBSP, Operande.creationOpEntier(1)));
		}
}

}
