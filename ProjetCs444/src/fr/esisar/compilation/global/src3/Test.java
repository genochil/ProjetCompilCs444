
package fr.esisar.compilation.global.src3;

class Test {

   public static void main(String[] args) {
      // Test registre
      for (Registre r : Registre.values()) {
         System.out.print(r + ", "); 
      }
      System.out.println();

      // Test opérande
      String s = "a\"b";
      Operande op1 = Operande.creationOpChaine(s);
      System.out.println(op1.getChaine() + " -> " + op1);
      Operande op2 = Operande.opDirect(Registre.R0); 
      System.out.println(op2.getRegistre() + " -> " + op2); 
      Operande op3 = Operande.creationOpEntier(-3);
      System.out.println(op3.getEntier() + " -> " + op3); 
      Operande op4 = Operande.creationOpEntier(576879867);
      System.out.println(op4.getEntier() + " -> " + op4);
      Operande op5 = Operande.creationOpEtiq(Etiq.lEtiq("toto"));
      System.out.println(op5.getEtiq() + " -> " + op5); 
      Operande op6 = Operande.creationOpIndexe(5, Registre.R0, Registre.GB); 
      System.out.println(op6.getDeplacement() + " " + op6.getRegistreBase() +
         " " + op6.getRegistreIndex() + " -> " + op6);
      Operande op7 = Operande.creationOpIndirect(-6, Registre.LB); 
      System.out.println(op7.getDeplacement() + " " + op7.getRegistreBase() + 
         " -> " + op7); 
      Operande op8 = Operande.creationOpReel(1.234e-6f); 
      System.out.println(op8.getReel() + " -> " + op8); 

      System.out.println(); 
      Operande op9 = Operande.R0; 
      System.out.print(op9 + " "); 
      op9 = Operande.R15; 
      System.out.print(op9 + " ");
      op9 = Operande.GB;
      System.out.print(op9 + " "); 
      op9 = Operande.LB; 
      System.out.println(op9);
      op9 = Operande.opDirect(Registre.R0); 
      System.out.print(op9 + " "); 
      op9 = Operande.opDirect(Registre.R15); 
      System.out.print(op9 + " "); 
      op9 = Operande.opDirect(Registre.GB); 
      System.out.print(op9 + " "); 
      op9 = Operande.opDirect(Registre.LB); 
      System.out.println(op9 + " "); 



      // Test opération
      for (Operation op : Operation.values()) {
         System.out.println(op + "(" + op.getArite() + ", " + op.getEtiq() + 
            ")"); 
      }

      // Test instruction
      Inst inst1 = Inst.creation0(Operation.HALT); 
      System.out.println(inst1);
      Inst inst2 = Inst.creation1(Operation.BRA, op5);
      System.out.println(inst2);
      Inst inst3 = Inst.creation1(Operation.WSTR, op1);
      System.out.println(inst3);
      Inst inst4 = Inst.creation2(Operation.LOAD, op6, op2);
      System.out.println(inst4);

      // Test ligne
      Ligne lig1 = new Ligne(null, null, "---------------");
      System.out.print(lig1);
      Ligne lig2 = new Ligne(null, inst1, null); 
      System.out.print(lig2);
      Ligne lig3 = new Ligne(Etiq.nouvelle("toto"), null, null);
      System.out.print(lig3);
      Ligne lig4 = new Ligne(Etiq.nouvelle("toto"), null, "et alors ?"); 
      System.out.print(lig4);
      Ligne lig5 = new Ligne(Etiq.nouvelle("toto"), inst3, null);
      System.out.print(lig5);
      Ligne lig6 = new Ligne(Etiq.nouvelle("toto"), inst4, "instruction"); 
      System.out.print(lig6);

      // Test programme
      // Prog p = Prog.ass();
      Prog.ajouterGrosComment("a"); 
      Prog.ajouter(lig1); 
      Prog.ajouterGrosComment("ab");
      Prog.ajouter(inst1);
      Prog.ajouterGrosComment("abc"); 
      Prog.ajouter(Etiq.nouvelle("toto")); 
      Prog.ajouterGrosComment("abcd"); 
      Prog.ajouterComment("");
      Prog.ajouterComment("");
      Prog.ajouterComment("a");
      Prog.ajouterComment("ab");
      Prog.ajouterComment("abc");
      Prog.ajouterComment("abcd");
      Prog.ajouterComment("1234567890");
      Prog.ajouterComment("12345678901");
      Prog.ajouterComment("123456789012");
      Prog.ajouterComment("1234567890123456789012345678901234567890123456789012345678901234567");
      Prog.ajouterComment("12345678901234567890123456789012345678901234567890123456789012345678");
      Prog.ajouterComment("123456789012345678901234567890123456789012345678901234567890123456789");
      Prog.ajouterComment("1234567890123456789012345678901234567890123456789012345678901234567890");
      Prog.ajouterComment("12345678901234567890123456789012345678901234567890123456789012345678901");
      Prog.ajouterComment("123456789012345678901234567890123456789012345678901234567890123456789012");

      Prog.afficher();

   }
}

