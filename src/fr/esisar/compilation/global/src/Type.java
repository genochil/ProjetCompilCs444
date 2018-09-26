package fr.esisar.compilation.global.src;

/**
 * Classe des types.
 *
 * Un objet de la classe Type représente un type du langage JCas. 
 *
 * La nature d'un type est définie par un attribut de type {@link fr.esisar.compilation.global.src.NatureType NatureType}.
 * Les différentes natures d'un type sont les suivantes : 
 * <code>NatureType.String</code>, <code>NatureType.Boolean</code>, 
 * <code>NatureType.Real</code>, <code>NatureType.Interval</code>, 
 * <code>NatureType.Array</code>.
 * <br><br>
 * Tous les types ont un attribut <code>taille</code> de type <code>int</code>,
 * initialisé à la valeur -1 lors de leur création. 
 * Cet attribut sert à stocker la taille mémoire occupée par une valeur du 
 * type en passe 3.
 * <br><br>
 * Les types <code>Type.Interval</code> possèdent deux attributs de type 
 * <code>int</code> : 
 * la borne inférieure <code>borneInf</code> et la borne supérieure 
 * <code>borneSup</code> de l'intervalle.
 * <br><br>
 * Les types {@link fr.esisar.compilation.global.src.Type#String Type.String}, 
 * {@link fr.esisar.compilation.global.src.Type#Boolean Type.Boolean}, 
 * {@link fr.esisar.compilation.global.src.Type#Real Type.Real} n'existent qu'en un 
 * seul exemplaire.
 * <br><br>
 * Le type 'Type.Integer' est prédéfini comme un Type.Interval, avec les 
 * attributs : 
 * <PRE>
   borneInf = -java.lang.Integer.MAX_VALUE 
   borneSup = java.lang.Integer.MAX_VALUE 
 * </PRE>
 */

public class Type { 

   private NatureType nature; // La nature de ce type
   int taille;                // La taille de ce type

   /**
    * Le type string.
    */
   public static final Type String = new Type(NatureType.String);

   /**
    * Le type boolean.
    */
   public static final Type Boolean = new Type(NatureType.Boolean);

   /**
    * Le type real.
    */
   public static final Type Real = new Type(NatureType.Real);

   /**
    * Le type integer.
    */
   public static final Type Integer = 
      creationInterval(-java.lang.Integer.MAX_VALUE, 
                        java.lang.Integer.MAX_VALUE); 

   // ------------------------------------------------------------------------
   // Constructeurs
   // ------------------------------------------------------------------------

   /**
    * Constructeur de type de nature spécifiée.
    * La taille du type est initialisée à -1.
    */
   Type(NatureType nature) {
      this.nature = nature;
      this.taille = -1;
   }

   /**
    * Constructeur de type intervalle de bornes inférieure et supérieure
    * spécifiées.
    */
   public static Type creationInterval(int borneInf, int borneSup) {
      return new TypeInterval(borneInf, borneSup);
   }

   /**
    * Constructeur de type tableau. 
    * Précondition : la nature de typeIndice est NatureType.Interval.
    */
   public static Type creationArray(Type typeIndice, Type typeElement) {
      if (typeIndice.getNature() != NatureType.Interval) {
         throw new ErreurType(
            "La nature de typeIndice doit etre NatureType.Interval") ; 
      }
      return new TypeArray(typeIndice, typeElement);
   }

   // ------------------------------------------------------------------------
   // Sélecteurs
   // ------------------------------------------------------------------------

   /**
    * La nature de ce type.
    */
   public NatureType getNature() {
      return nature;
   }

   /**
    * La taille de ce type.
    */
   public int getTaille() {
      return taille;
   }
 
   /**
    * Le type des indices pour un type tableau.
    * Précondition : la nature de ce type doit être NatureType.Array.
    */

   public Type getIndice() {
      throw new ErreurType("Type tableau attendu.");
   }

   /**
    * Le type des éléments pour un type tableau.
    * Précondition : la nature de ce type doit être NatureType.Array.
    */

   public Type getElement() {
      throw new ErreurType("Type tableau attendu.");
   }

   /**
    * La borne inférieure d'un type intervalle.
    * Précondition : la nature de ce type doit être NatureType.Interval.
    */

   public int getBorneInf() {
      throw new ErreurType("Type intervalle attendu.");
   }

   /**
    * La borne supérieure d'un type intervalle.
    * Précondition : la nature de ce type doit être NatureType.Interval.
    */

   public int getBorneSup() {
      throw new ErreurType("Type intervalle attendu.");
   }


   // ------------------------------------------------------------------------
   // Mutateurs
   // ------------------------------------------------------------------------

   /**
    * Modifie la taille de ce type.
    */
   public void setTaille(int taille) {
      this.taille = taille;
   }

   /**
    * Modifie le type des indices pour un type tableau.
    * Précondition : la nature de ce type doit être NatureType.Array.
    */

   public void setIndice(Type typeIndice) {
      throw new ErreurType("Type tableau attendu.");
   }

   /**
    * Modifie le type des éléments pour un type tableau.
    * Précondition : la nature de ce type doit être NatureType.Array.
    */

   public void setElement(Type typeElement) {
      throw new ErreurType("Type tableau attendu.");
   }

   /**
    * Modifie la borne inférieure d'un type intervalle.
    * Précondition : la nature de ce type doit être NatureType.Interval.
    */

   public void setBorneInf(int borneInf) {
      throw new ErreurType("Type intervalle attendu.");
   }

   /**
    * Modifie la borne supérieure d'un type intervalle.
    * Précondition : la nature de ce type doit être NatureType.Interval.
    */

   public void setBorneSup(int borneSup) {
      throw new ErreurType("Type intervalle attendu.");
   }


   // ------------------------------------------------------------------------
   // Divers
   // ------------------------------------------------------------------------

   String natureToString() {
      String s;
      switch (nature) {
         case Interval:
            if (this == Type.Integer) {
               s = "Type.Integer";
            } else {
               s = "NatureType.Interval";
            }
            break;
         default:
            s = nature.toString();
      }
      return s;
   }

   /**
    * Chaîne de carcatères qui correspond à ce type.
    */
   public String toString() {
      String s;
      s = natureToString();
      switch (nature) {
         case Interval:
            if (this != Type.Integer) {
               s = s + "(" + getBorneInf() + ", " + getBorneSup() + ")";
            }
            break;
         case Array:
            s = s + "(" + getIndice() + ", " + getElement() + ")";
         break;
      }
      return s;
   }

   /**
    * Affiche ce type avec le niveau de détails spécifié.
    */

   public void afficher(int niveau) {
      int c1, c2;
      c1 = Affichage.getCol();
      if (c1 == 1) {
         Affichage.commencer();
      }
      switch (nature) {
         case Array:
            Affichage.ecrire(natureToString());
            Affichage.ecrire("(");
            getIndice().afficher(niveau);
            Affichage.ecrire(", ");
            getElement().afficher(niveau);
            Affichage.ecrire(")");
            break;
         default:
            Affichage.ecrire(this.toString());
      }
      if (niveau >= 2) {
         c2 = Affichage.getCol();
         Affichage.ecrire("\n");
         Affichage.commencer();
         Affichage.setCol(c1);
         Affichage.ecrire("... taille = " + getTaille() + "\n");
         Affichage.commencer();
         Affichage.setCol(c2);
      }
   }
     
}


