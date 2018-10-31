package fr.esisar.compilation.global.src3;

/**
 * La classe des opérandes indexés, qui correspondent à un adressage indirect 
 * indexé avec déplacement (opérande de nature OpIndexe).
 */

class OperandeIndexe extends Operande {

   private int deplacement; // Le déplacement 
   private Registre regBase; // Le registre de base
   private Registre regIndex; // Le registre d'index

   /**
    * Constructeur.
    */
   OperandeIndexe(int deplacement, Registre regBase, Registre regIndex) {
      super(NatureOperande.OpIndexe);
      this.deplacement = deplacement;
      this.regBase = regBase;
      this.regIndex = regIndex;
   }

   /**
    * Le déplacement de cet opérande indexé.
    */
   public int getDeplacement() {
      return deplacement;
   }

   /**
    * Le registre de base de cet opérande indexé.
    */
   public Registre getRegistreBase() {
      return regBase;
   }

   /**
    * Le registre d'index de cet opérande indexé.
    */
   public Registre getRegistreIndex() {
      return regIndex;
   }

   /**
    * Modifie le déplacement de cet opérande indexé.
    */
   public void setDeplacement(int deplacement) {
      this.deplacement = deplacement;
   }

   /**
    * Modifie le registre de base de cet opérande indexé.
    */
   public void setRegistreBase(Registre regBase) {
      this.regBase = regBase;
   }

   /**
    * Modifie le registre d'index de cet opérande indexé.
    */
   public void setRegistreIndex(Registre regIndex) {
      this.regIndex = regIndex;
   }

   /**
    * La chaîne qui correspond à cet opérande.
    */
   public String toString() {
      return deplacement + "(" + regBase + ", " + regIndex + ")"; 
   }


}

