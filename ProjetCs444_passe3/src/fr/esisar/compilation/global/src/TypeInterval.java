package fr.esisar.compilation.global.src;

/**
 * Classe des types intervalles.
 */

public class TypeInterval extends Type {

   private int borneInf; // La borne inférieure de ce type intervalle
   private int borneSup; // La borne supérieure de ce type intervalle

   /**
    * Constructeur de type intervalle.
    */
   TypeInterval(int borneInf, int borneSup) {
      super(NatureType.Interval);
      this.borneInf = borneInf;
      this.borneSup = borneSup;
   }
    
   /**
    * La borne inférieure de ce type. 
    */

   public int getBorneInf() {
      return borneInf;
   }

   /**
    * La borne supérieure de ce type. 
    */

   public int getBorneSup() {
      return borneSup;
   }

   /**
    * Modifie la borne inférieure de ce type.
    */

   public void setBorneInf(int borneInf) {
      this.borneInf = borneInf;
   }

   /**
    * Modifie la borne supérieure de ce type.
    */

   public void setBorneSup(int borneSup) {
      this.borneSup = borneSup;
   }


}

