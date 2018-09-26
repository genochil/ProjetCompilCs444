package fr.esisar.compilation.global.src;

/**
 * Classe des arbres qui ont entre 1 et 3 fils.
 */

class ArbreFils extends Arbre {

   private Arbre[] tableauFils; // Le tableau des fils de l'arbre

   /**
    * Constructeur pour un noeud d'arité 1.
    */
   ArbreFils(Noeud noeud, Arbre fils1, int numLigne) {
      super(noeud, numLigne, 1);
      tableauFils = new Arbre[1];
      tableauFils[0] = fils1;
   }

   /**
    * Constructeur pour un noeud d'arité 2.
    */
   ArbreFils(Noeud noeud, Arbre fils1, Arbre fils2, int numLigne) {
      super(noeud, numLigne, 2);
      tableauFils = new Arbre[2];
      tableauFils[0] = fils1;
      tableauFils[1] = fils2;
   }

   /**
    * Constructeur pour un noeud d'arité 3.
    */
   ArbreFils(Noeud noeud, Arbre fils1, Arbre fils2, Arbre fils3, int numLigne) {
      super(noeud, numLigne, 3);
      tableauFils = new Arbre[3];
      tableauFils[0] = fils1;
      tableauFils[1] = fils2;
      tableauFils[2] = fils3;
   }

   /**
    * Accès au n-ième fils de l'arbre.
    */
   public Arbre getFils(int n) {
      if (n <= 0 || n > 3) {
         throw new ErreurArbre(
            "Accès au fils no " + n + " impossible");
      } else if (n <= 0 || n > tableauFils.length) {
         throw new ErreurArbre(
            "Accès au fils no " + n + " impossible sur " + getNoeud());
      }
      return tableauFils[n - 1];
   }

   /**
    * Modification du n-ième fils de l'arbre.
    */
   public void setFils(int n, Arbre fils) {
      if (n <= 0 || n > 3) {
         throw new ErreurArbre(
            "Modification du fils no " + n + " impossible");
      } else if (n <= 0 || n > tableauFils.length) {
         throw new ErreurArbre(
            "Modification du fils no " + n + " impossible sur " + getNoeud());
      }
      tableauFils[n - 1] = fils;
   }

}
