package fr.esisar.compilation.global.src;

/**
 * Classe des arbres qui représentent des identificateurs et des chaînes 
 * de caractères.
 */
class ArbreChaine extends Arbre {

   // La chaîne pour les Noeud.Chaine et les Noeud.Ident
   private String valChaine; 

   /**
    * Constructeur.
    */
   ArbreChaine(Noeud noeud, String valChaine, int numLigne) {
      super(noeud, numLigne);
      this.valChaine = valChaine;
   }

   /**
    * La chaîne de caractères qui correspond à cet arbre.
    */
   public String getChaine() {
      return valChaine;
   }

   /**
    * Modifie la chaîne de caractères qui correspond à cet arbre.
    */
   public void setChaine(String valChaine) {
      this.valChaine = valChaine;
   }

}

