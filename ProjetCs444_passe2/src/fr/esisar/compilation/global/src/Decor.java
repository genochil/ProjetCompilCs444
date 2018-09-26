package fr.esisar.compilation.global.src;

/**
 * Classe des décors.
 * Un décor est un triplet (Defn defn, Type type, int infoCode).
 * Le champ infoCode est initialisé par défaut à -1.
 */

public class Decor {

   private Defn defn;    // La defn de ce décor
   private Type type;    // Le type de ce décor
   private int infoCode; // Le champ infoCode de ce décor

   private boolean premierSaut; // Pour l'affichage d'un décor

   /**
    * Constructeur de décor.
    * La defn et le type sont initialisées à null.
    * Le champ infoCode est initialisé à -1.
    */
   public Decor() { 
      infoCode = -1;
   }

   /**
    * Constructeur de décor avec la defn spécifiée.
    * Le type est initialisée à null.
    * Le champ infoCode est initialisé à -1.
    */
   public Decor(Defn defn) {
      this.defn = defn;
      infoCode = -1;
   }

   /**
    * Constructeur de décor avec le type spécifiée.
    * La defn est initialisée à null;
    * Le champ infoCode est initialisé à -1.
    */
   public Decor(Type type) {
      this.type = type;
      infoCode = -1;
   }

   /**
    * Constructeur de décor avec la defn et le type spécifiées.
    * Le champ infoCode est initialisé à -1.
    */
   public Decor(Defn defn, Type type) {
      this.defn = defn;
      this.type = type;
      infoCode = -1;
   }

   /**
    * La defn de ce décor.
    */
   public Defn getDefn() {
      return defn;
   }

   /**
    * Le type de ce décor.
    */
   public Type getType() {
      return type;
   }

   /**
    * L'infoCode de ce décor.
    */
   public int getInfoCode() {
      return infoCode;
   }

   /**
    * Modifie la defn de ce décor avec la defn spécifiée.
    */
   public void setDefn(Defn defn) {
      this.defn = defn;
   }

   /**
    * Modifie le type de ce décor avec le type spécifiée.
    */
   public void setType(Type type) {
      this.type = type;
   }

   /**
    * Modifie l'infoCode de ce décor avec l'entier spécifié.
    */
   public void setInfoCode(int infoCode) {
      this.infoCode = infoCode;
   }

   /**
    * Affiche un retour à la ligne si c'est la première ligne affichée
    * et si le numéro de colonne est différent de 1.
    */
   private void premiereLigne() {
      if (premierSaut) {
         if (Affichage.getCol() != 1) {
            Affichage.ecrire("\n");
         }
         premierSaut = false; 
      }
   }

   /**
    * Affiche ce décor avec le niveau de détails spécifié.
    * <ul>
    *  <li> niveau = 0 : pas de décor affiché </li>
    *  <li> niveau = 1 : décors relatifs à la passe 2 </li>
    *  <li> niveau = 2 : décors relatifs aux passes 2 et 3 </li>
    * </ul>
    */
   public void afficher(int niveau) {
      premierSaut = true;
      int c = Affichage.getCol();
      if (defn != null) {
         if (niveau >= 1) {
            premiereLigne();
            Affichage.commencer();
            Affichage.ecrire("defn :\n");
            Affichage.empiler(3, "");
            defn.afficher(niveau);
            Affichage.depiler();
            Affichage.ecrire("\n");
         }
      }

      if (type != null) {
         if (niveau >= 1) {
            premiereLigne();
            Affichage.commencer();
            Affichage.ecrire("type :\n");
            Affichage.empiler(3, "");
            type.afficher(niveau);
            Affichage.depiler();
            Affichage.ecrire("\n");
         }
      }

      if (infoCode != -1) {
         if (niveau >= 2) {
            premiereLigne();
            Affichage.commencer();
            Affichage.ecrire("infoCode : " + infoCode + "\n");
         }
      }

      if (!premierSaut) {
         Affichage.setCol(c);
      }

   }
}

