package fr.esisar.compilation.global.src3;

/**
 * Classe des opérandes entiers (de nature OpEntier).
 */

class OperandeEntier extends Operande { 

   private int valEntier; // L'entier qui correspond à cet opérande entier

   /**
    * Constructeur.
    */
   OperandeEntier(int valEntier) {
      super(NatureOperande.OpEntier);
      this.valEntier = valEntier;
   }

   /**
    * L'entier qui correspond à cet opérande entier.
    */
   public int getEntier() {
      return valEntier;
   }

   /**
    * Modifie l'entier qui correspond à cet opérande entier.
    */
   public void setEntier(int valEntier) {
      this.valEntier = valEntier;
   }

   /**
    * La chaîne de caractères qui correspond à cet opérande. 
    */
   public String toString() {
      return "#" + valEntier;
   }


}


