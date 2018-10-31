package fr.esisar.compilation.global.src3;

/**
 * Classe des opérandes chaîne (de nature OpChaine).
 */

class OperandeChaine extends Operande { 

   private String valChaine; // La chaîne qui correspond à cet opérande chaîne

   /**
    * Constructeur.
    */
   OperandeChaine(String valChaine) {
      super(NatureOperande.OpChaine);
      this.valChaine = valChaine;
   }

   /**
    * La chaîne qui correspond à cet opérande chaîne.
    */
   public String getChaine() {
      return valChaine;
   }

   /**
    * Modifie la chaîne qui correspond à cet opérande chaîne.
    */
   public void setChaine(String valChaine) {
      this.valChaine = valChaine;
   }

   /**
    * La chaîne qui correspond à cet opérande chaîne, dans le format 
    * de la machine abstraite.
    * Exemple : a"b -> "a""b".
    */
   public String toString() {
      String res = "\"";
      for (int i = 0; i < valChaine.length(); i++) {
         char c = valChaine.charAt(i);
         if (c == '"') {
            res = res + "\"\""; 
         } else {
            res = res + c; 
         }
      }
      res = res + "\"";
      return res;
   }

}


