package fr.esisar.compilation.global.src3;

/**
 * Classe des opérandes réels (de nature OpReel).
 */

class OperandeReel extends Operande { 

   private float valReel; // Le réel qui correspond à cet opérande réel

   /**
    * Constructeur.
    */
   OperandeReel(float valReel) {
      super(NatureOperande.OpReel);
      this.valReel = valReel;
   }

   /**
    * Le réel qui correspond à cet opérande réel.
    */
   public float getReel() {
      return valReel;
   }

   /**
    * Modifie le réel qui correspond à cet opérande réel.
    */
   public void setReel(float valReel) {
      this.valReel = valReel;
   }

   /**
    * La chaîne qui correspond à cet opérande.
    */
   public String toString() {
      return "#" + valReel;
   }


}


