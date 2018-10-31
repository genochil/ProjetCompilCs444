package fr.esisar.compilation.global.src3;

/**
 * La classe des opérandes indirects, qui correspondent à un adressage indirect 
 * avec déplacement (opérande de nature OpIndirect).
 */

class OperandeIndirect extends Operande {

   private int deplacement; // Le déplacement de cet opérande
   private Registre regBase; // Le registre de base de cet opérande

   /**
    * Constructeur.
    */
   OperandeIndirect(int deplacement, Registre regBase) {
      super(NatureOperande.OpIndirect);
      this.deplacement = deplacement;
      this.regBase = regBase;
   }

   /**
    * Le déplacement de cet opérande indirect.
    */
   public int getDeplacement() {
      return deplacement;
   }

   /**
    * Le registre de base de cet opérande indirect.
    */
   public Registre getRegistreBase() {
      return regBase;
   }

   /**
    * Modifie le déplacement de cet opérande indirect.
    */
   public void setDeplacement(int deplacement) {
      this.deplacement = deplacement;
   }

   /**
    * Modifie le registre de base de cet opérande indirect.
    */
   public void setRegistreBase(Registre regBase) {
      this.regBase = regBase;
   }

   /**
    * La chaîne qui correspond à cet opérande.
    */
   public String toString() {
      return deplacement + "(" + regBase + ")";
   }



}

