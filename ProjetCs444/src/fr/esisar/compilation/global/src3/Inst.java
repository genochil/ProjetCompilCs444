package fr.esisar.compilation.global.src3;

/**
 * Classe des instructions de la machine abstraite.
 */

public class Inst {

   private Operation operation;


   // ------------------------------------------------------------------------
   // Constructeurs
   // ------------------------------------------------------------------------

   /**
    * Constructeur d'instruction dont l'opération est d'arité 0.
    * Précondition : operation.getArite() == 0.
    */

   Inst(Operation operation) {
      this.operation = operation;
   }

   /**
    * Constructeur d'instruction dont l'opération est d'arité 0.
    * Précondition : operation.getArite() == 0.
    */

   public static Inst creation0(Operation operation) {
      if (operation.getArite() != 0) {
         throw new ErreurInst("Operation d'arité 0 attendue");
      }
      return new Inst(operation);
   }

   /**
    * Constructeur d'instruction dont l'opération est d'arité 1.
    * Précondition : operation.getArite() == 1 et, si l'opération est 
    * un branchement, l'opérande est de nature étiquette 
    * (NatureOperande.OpEtiq).
    */

   public static Inst creation1(Operation operation, Operande op1) {
      if (operation.getArite() != 1) {
         throw new ErreurInst("Operation d'arité 1 attendue");
      }
      if (operation.getEtiq() && op1.getNature() != NatureOperande.OpEtiq) {
         throw new ErreurInst("Operande étiquette attendu");
      }
      return new Inst1(operation, op1);
   }

   /**
    * Constructeur d'instruction dont l'opération est d'arité 2.
    * Précondition : operation.getArite() == 2.
    */

   public static Inst creation2
         (Operation operation, Operande op1, Operande op2) {
      if (operation.getArite() != 2) {
         throw new ErreurInst("Operation d'arité 2 attendue");
      }
      return new Inst2(operation, op1, op2);
   }


   // ------------------------------------------------------------------------
   // Sélecteurs
   // ------------------------------------------------------------------------

   /**
    * L'opération de cette instruction.
    */
   public Operation getOperation() {
      return operation; 
   }

   /**
    * Le premier opérande de cette instruction.
    * Précondition : l'opération de cette instruction est d'arité 
    * supérieure ou égale à 1.
    */
   public Operande getOperande1() {
      throw new ErreurInst("Cette instruction n'a pas d'opérande");
   }

   /**
    * Le deuxième opérande de cette instruction.
    * Précondition : l'opération de cette instruction est d'arité 2.
    */
   public Operande getOperande2() {
      throw new ErreurInst("Cette instruction n'a pas de deuxième opérande");
   }


   // ------------------------------------------------------------------------
   // Mutateurs
   // ------------------------------------------------------------------------

   /**
    * Modifie le premier opérande de cette instruction.
    * Précondition : l'opération de cette instruction est d'arité 
    * supérieure ou égale à 1.
    */
   public void setOperande1(Operande op1) {
      throw new ErreurInst("Cette instruction n'a pas d'opérande");
   }

   /**
    * Modifie le deuxième opérande de cette instruction.
    * Précondition : l'opération de cette instruction est d'arité 2.
    */
   public void setOperande2(Operande op2) {
      throw new ErreurInst("Cette instruction n'a pas de deuxième opérande");
   }

   // ------------------------------------------------------------------------
   // Autres opérations
   // ------------------------------------------------------------------------

   /**
    * Chaîne de caractères correspondant à cette instruction.
    */
   public String toString() {
      return operation.toString();
   }

}

