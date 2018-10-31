package fr.esisar.compilation.global.src3;

/**
 * La classe des opérandes qui breprésentent des registres 
 * (opérandes de nature OpDirect).
 */

class OperandeDirect extends Operande {

   private Registre reg;

   /**
    * Constructeur.
    */
   OperandeDirect(Registre reg) {
      super(NatureOperande.OpDirect);
      this.reg = reg;
   }

   /**
    * Le registre qui correspond à cet opérande.
    */
   public Registre getRegistre() {
      return reg;
   }

   /**
    * Modifie le registre qui correspond à cet opérande.
    */
   public void setRegistre(Registre reg) {
      this.reg = reg;
   }

   /**
    * La chaîne de caractères qui correspond à cet opérande.
    */
   public String toString() {
      return reg.toString() ; 
   }

}


