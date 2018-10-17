package fr.esisar.compilation.global.src;

import fr.esisar.compilation.global.src3.Operande;

/**
 * Classe des "defns", définitions qui sont associées aux identificateurs.
 */

public class Defn { 

   private NatureDefn nature; // La nature de la defn
   private Type type;   // Le type de la defn
   private Genre genre;       // Le genre d'une defn
   private Operande operande; // L'opérande associé à une defn

   // ------------------------------------------------------------------------
   // CONSTRUCTEURS
   // ------------------------------------------------------------------------

   /**
    * Constructeur de defn.
    */
   Defn(NatureDefn nature, Type type) {
      this.nature = nature;
      this.type = type;
      this.genre = Genre.NonPredefini;
      this.operande = null;
   }

   /**
    * Constructeur de defn de nature NatureDefn.Var.
    */

   public static Defn creationVar(Type type) {
      return new Defn(NatureDefn.Var, type);
   }

   /**
    * Constructeur de defn de nature NatureDefn.Type.
    */

   public static Defn creationType(Type type) {
      return new Defn(NatureDefn.Type, type);
   }

   /**
    * Constructeur de defn de nature NatureDefn.ConstInteger de valeur val.
    */

   public static Defn creationConstInteger(int val) {
      return new DefnConstInteger(val); 
   }

   /**
    * Constructeur de defn de nature NatureDefn.ConstBoolean de valeur val.
    */

   public static Defn creationConstBoolean(boolean val) {
      return new DefnConstBoolean(val);
   }

   // ------------------------------------------------------------------------
   // Sélecteurs
   // ------------------------------------------------------------------------

   /**
    * La nature de cette defn.
    */
   public NatureDefn getNature() {
      return nature;
   }

   /**
    * Le type de cette defn.
    */
   public Type getType() {
      return type;
   }

   /**
    * Le genre de cette defn.
    */
   public Genre getGenre() {
      return genre;
   }

   /**
    * L'opérande associé à cette defn.
    */
   public Operande getOperande() {
      return operande;
   }

   /**
    * La valeur entière associée à cette defn. 
    * Précondition : la nature de cette defn est NatureDefn.ConstInteger.
    */
   public int getValeurInteger() {
      throw new ErreurDefn("getValeurInteger() impossible sur " + nature);
   }

   /**
    * La valeur booléenne associée à cette defn. 
    * Précondition : la nature de cette defn est NatureDefn.ConstBoolean.
    */
   public boolean getValeurBoolean() {
      throw new ErreurDefn("getValeurBoolean() impossible sur " + nature);
   }

   // ------------------------------------------------------------------------
   // Mutateurs
   // ------------------------------------------------------------------------

   /**
    * Modifie le type de cette defn avec le type spécifié.
    */
   public void setType(Type type) {
      this.type = type;
   }

   /**
    * Modifie le genre de cette defn avec le genre spécifié.
    */

   public void setGenre(Genre genre) {
      this.genre = genre;
   }
   
   /**
    * Modifie l'opérande associé à cette defn avec l'opérande spécifié.
    */

   public void setOperande(Operande operande) {
      this.operande = operande;
   }

   /**
    * Modifie la valeur entière associée à cette defn. 
    * Précondition : la nature de cette defn est NatureDefn.ConstInteger.
    */
   public void setValeurInteger(int val) {
      throw new ErreurDefn("setValeurInteger(val) impossible sur " + nature);
   }

   /**
    * Modifie la valeur booléenne associée à cette defn. 
    * Précondition : la nature de cette defn est NatureDefn.ConstBoolean.
    */
   public void setValeurBoolean(boolean val) {
      throw new ErreurDefn("setValeurBoolean(val) impossible sur " + nature);
   }

   // ------------------------------------------------------------------------
   // Divers
   // ------------------------------------------------------------------------

   /**
    * Retourne la chaîne associée à la nature de cette defn.
    * Forme de la chaîne : NatureDefn.Var ou NatureDefn.Type ou 
    * NatureDefn.ConstInteger(1) ou NatureDefn.ConstBoolean(true).
    */
   public String natureToString() {
      String s = nature.toString();
      switch (nature) {
         case ConstInteger:
            s = s + "(" + getValeurInteger() + ")";
            break;
         case ConstBoolean:
            s = s + "(" + getValeurBoolean() + ")";
            break;
      }
      return s;
   }

   /**
    * Une chaîne de caractères qui représente cette defn.
    */
   public String toString() {
      String s = "(" + natureToString();
      s = s + ", " + type + ", " + genre + ", " + operande + ")";
      return s;
   }

   /**
    * Affiche cette defn, avec le niveau de détails spécifié.
    */
   public void afficher(int niveau) {
      int c1, c2;
      c1 = Affichage.getCol();
      if (c1 == 1) {
         Affichage.commencer();
      }
      Affichage.ecrire("(" + natureToString() + ", ");
      if (type == null) {
         Affichage.ecrire("null");
      } else {
         type.afficher(niveau);
      }
      Affichage.ecrire(")");
      if (niveau >= 2) {
         c2 = Affichage.getCol();
         Affichage.ecrire("\n");
         Affichage.commencer();
         Affichage.ecrire("... genre    = " + genre + "\n");
         Affichage.commencer();
         Affichage.ecrire("... operande = " + operande);
         if (c1 != 1) {
            Affichage.ecrire("\n");
            Affichage.commencer();
            Affichage.setCol(c2);
         }
      }

   }

}
