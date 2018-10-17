package fr.esisar.compilation.global.src3;

/**
 * Classe des instructions dont l'opération est d'arité 1.
 */

class Inst1 extends Inst {

   /** L'opérande de cette instruction. */
   private Operande op1;

   /**
    * Constructeur.
    */
   Inst1(Operation operation, Operande op1) {
      super(operation);
      this.op1 = op1; 
   }

   /**
    * L'opérande de cette opération.
    */
   public Operande getOperande1() {
      return op1; 
   }

   /**
    * Modifie l'opérande de cette opération.
    */
   public void setOperande1(Operande op1) {
      this.op1 = op1;
   }

   /**
    * Chaîne de caractères correspondant à cette instruction.
    */
   public String toString() {
      return super.toString() + " " + op1.toString();
   }

}

