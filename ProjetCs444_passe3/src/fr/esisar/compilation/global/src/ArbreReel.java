package fr.esisar.compilation.global.src;

/**
 * La classe des arbres qui représentent des réels.
 */

class ArbreReel extends Arbre {
   private float valReel;    // L'entier pour les Noeud.Reel

   /**
    * Constructeur.
    */
   ArbreReel(float valReel, int numLigne) {
      super(Noeud.Reel, numLigne);
      this.valReel = valReel;
   }

   /**
    * Le réel qui correspond à cet arbre.
    */
   public float getReel() {
      return valReel;
   }

   /**
    * Modifie le réel qui correspond à cet arbre.
    */
   public void setReel(int valReel) {
      this.valReel = valReel;
   }
}

