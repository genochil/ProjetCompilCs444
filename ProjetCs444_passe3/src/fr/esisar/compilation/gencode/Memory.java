package fr.esisar.compilation.gencode;

import java.util.HashMap;
import java.util.Map;

import fr.esisar.compilation.global.src3.Prog;
import fr.esisar.compilation.global.src3.Registre;

public class Memory {

	private static HashMap<Registre, Integer> Reg = new HashMap<Registre, Integer>();

	public static void init() {
		Reg.put(Registre.R0, 1);
		Reg.put(Registre.R1, 0);// reservé pour lire et ecrire
		Reg.put(Registre.R2, 1);
		Reg.put(Registre.R3, 1);
		Reg.put(Registre.R4, 1);
		Reg.put(Registre.R5, 1);
		Reg.put(Registre.R6, 1);
		Reg.put(Registre.R7, 1);
		Reg.put(Registre.R8, 1);
		Reg.put(Registre.R9, 1);
		Reg.put(Registre.R10, 1);
		Reg.put(Registre.R11, 1);
		Reg.put(Registre.R12, 1);
		Reg.put(Registre.R13, 1);
		Reg.put(Registre.R14, 1);
		Reg.put(Registre.R15, 1);
	}

	public static Registre allocate() {

		for (Map.Entry<Registre, Integer> entry : Reg.entrySet()) {
			if (entry.getValue() == 1) {
				Prog.ajouterComment("Registre renvoyé : " + entry.getKey());
				Reg.replace(entry.getKey(),1,0);
				return entry.getKey();

			}
		}
		return Registre.GB;
	}

	public static void liberate(Registre r) {
		Reg.replace(r, 0, 1);
		Prog.ajouterComment("Registre libéré : " + r);
	}
	public static int Nb_Reg_Free()
	{
		int nb=0;
		for (Map.Entry<Registre, Integer> entry : Reg.entrySet()) {
			if (entry.getValue() == 1) {
				nb++;
			}
		}
		return nb;
		
	}
	public static int allocate_Temp() {
		return (Variable.add_new_var());

	}

}
