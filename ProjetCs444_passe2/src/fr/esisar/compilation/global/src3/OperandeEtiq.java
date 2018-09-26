package fr.esisar.compilation.global.src3;

/**
 * Classe des opérandes étiquette (de nature OpEtiq).
 */

class OperandeEtiq extends Operande { 

   private Etiq valEtiq; // L'étiquette qui correspond à cet opérande étiquette

   /**
    * Constructeur.
    */
   OperandeEtiq(Etiq valEtiq) {
      super(NatureOperande.OpEtiq);
      this.valEtiq = valEtiq;
   }

   /**
    * L'étiquette qui correspond à cet opérande étiquette.
    */
   public Etiq getEtiq() {
      return valEtiq;
   }

   /**
    * Modifie l'étiquette qui correspond à cet opérande étiquette.
    */
   public void setEtiq(Etiq valEtiq) {
      this.valEtiq = valEtiq;
   }

   /**
    * La chaîne qui correspond à cet opérande.
    */
   public String toString() {
      return valEtiq.toString();
   }

   

}


