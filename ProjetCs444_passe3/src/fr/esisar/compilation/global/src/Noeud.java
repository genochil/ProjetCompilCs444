package fr.esisar.compilation.global.src;

/**
 * Le type des noeuds d'un arbre.
 */
public enum Noeud {
   Affect(2, 1),
   Chaine(0),
   Conversion(1, 1),
   Decl(2),
   Decrement(3),
   DivReel(2, 1),
   Ecriture(1),
   Egal(2, 1),
   Entier(0),
   Et(2),
   Ident(0),
   Increment(3),
   Index(2),
   Inf(2, 1),
   InfEgal(2, 1),
   Intervalle(2),
   Lecture(1),
   Ligne(0),
   ListeDecl(2),
   ListeIdent(2),
   ListeInst(2),
   ListeExp(2),
   Moins(2, 1),
   MoinsUnaire(1),
   Mult(2, 1),
   Non(1),
   NonEgal(2, 1),
   Nop(0),
   Ou(2),
   Plus(2, 1),
   PlusUnaire(1),
   Pour(2),
   Programme(2),
   Quotient(2, 1),
   Reel(0),
   Reste(2, 1),
   Si(3),
   Sup(2),
   SupEgal(2, 1),
   Tableau(2),
   TantQue(2),
   Vide(0);

   // Arité de ce noeud
   public final int arite;

   // Nombre d'espaces initiaux dans l'affichage du noeud
   final int nbEspaces;

   /**
    * Constructeur de Noeud d'arité spécifiée.
    * nbEspaces est initialisé à 0.
    */
   Noeud(int arite) {
      this.arite = arite;
      this.nbEspaces = 0;
   }

   /**
    * Constructeur de Noeud d'arité spécifiée, et avec un nombre d'espaces 
    * initiaux lors de l'affichage spécifié.
    */
   Noeud(int arite, int nbEspaces) {
      this.arite = arite;
      this.nbEspaces = nbEspaces;
   }

   /**
    * Affichage d'un noeud.
    */
   public String toString() {
      return "Noeud." + super.toString();
   }
}

