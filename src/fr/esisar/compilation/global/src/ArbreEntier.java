package fr.esisar.compilation.global.src;

/**
 * La classe des arbres qui représentent des entiers.
 */

class ArbreEntier extends Arbre {
   private int valEntier;    // L'entier pour les Noeud.Entier

   /**
    * Constructeur.
    */
   ArbreEntier(int valEntier, int numLigne) {
      super(Noeud.Entier, numLigne);
      this.valEntier = valEntier;
   }

   /**
    * L'entier qui correspond à cet arbre.
    */
   public int getEntier() {
      return valEntier;
   }

   /**
    * Modifie l'entier qui correspond à cet arbre.
    */
   public void setEntier(int valEntier) {
      this.valEntier = valEntier;
   }
}

