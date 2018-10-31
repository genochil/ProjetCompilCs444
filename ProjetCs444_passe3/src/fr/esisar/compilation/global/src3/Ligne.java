package fr.esisar.compilation.global.src3;

/**
 * Classe des lignes de code de la machine abstraite.
 * Une ligne de code est composée des éléments suivants : 
 * <ul>
 * <li> une étiquette (type Etiq) ; </li>
 * <li> une instruction (type Inst) ; </li>
 * <li> un commentaire (type String). </li>
 * </ul>
 * Chaque élément peut avoir la valeur null.
 */

public class Ligne {
   
   private Etiq etiq; // L'étiquette de cette ligne
   private Inst inst; // L'instruction de cette ligne
   private String comment; // Le commentaire de cette ligne

   /**
    * Constructeur.
    */
   public Ligne(Etiq etiq, Inst inst, String comment) {
      this.etiq = etiq;
      this.inst = inst;
      this.comment = comment;
   }

   /**
    * L'étiquette de cette ligne.
    */
   public Etiq getEtiq() {
      return etiq;
   }

   /**
    * L'instruction de cette ligne.
    */
   public Inst getInst() {
      return inst;
   }

   /**
    * Le commentaire de cette ligne.
    */
   public String getComment() {
      return comment;
   }

   /**
    * Modifie l'étiquette de cette ligne.
    */
   public void setEtiq(Etiq etiq) {
      this.etiq = etiq;
   }

   /**
    * Modifie l'instruction de cette ligne.
    */
   public void setInst(Inst inst) {
      this.inst = inst;
   }

   /**
    * Modifie le commentaire de cette ligne.
    */
   public void setComment() {
      this.comment = comment;
   }

   /**
    * La chaîne de caractères qui correspond à cette ligne.
    */
   public String toString() {
      String res = "";
      if (etiq != null) {
         res += etiq + ": ";
         if (inst != null) {
            res += "\n"; 
         }
      }
      if (inst != null) {
         res += "   " + inst + " ";
      }
      if (comment != null) {
         res += "; " + comment;
      }
      res += "\n";
      return res;
   }
}

