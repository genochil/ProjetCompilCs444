package fr.esisar.compilation.global.src3;

/**
 * Classe des instructions dont l'opération est d'arité 2.
 */

class Inst2 extends Inst1 {

   /** Le deuxième opérande de cette instruction. */
   private Operande op2;

   /**
    * Constructeur.
    */
   Inst2(Operation operation, Operande op1, Operande op2) {
      super(operation, op1);
      this.op2 = op2; 
   }

   /**
    * Le deuxième opérande de cette opération.
    */
   public Operande getOperande2() {
      return op2; 
   }

   /**
    * Modifie le deuxième opérande de cette opération.
    */
   public void setOperande2(Operande op2) {
      this.op2 = op2;
   }

   /**
    * Chaîne de caractères correspondant à cette instruction.
    */
   public String toString() {
      return super.toString() + ", " + op2.toString(); 
   }
}

