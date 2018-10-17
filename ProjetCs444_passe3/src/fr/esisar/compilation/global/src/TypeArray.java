package fr.esisar.compilation.global.src;

/**
 * Classe des types tableau.
 */

public class TypeArray extends Type {

   private Type typeIndice;  // Le type des indices de ce type tableau
   private Type typeElement; // Le type des éléments de ce type tableau

   /**
    * Constructeur de type tableau.
    */
   TypeArray(Type typeIndice, Type typeElement) {
      super(NatureType.Array);
      this.typeIndice = typeIndice;
      this.typeElement = typeElement;
   }

   /**
    * Le type des indices de ce type.
    */
   
   public Type getIndice() {
      return typeIndice;
   }

   /**
    * Le type des éléments de ce type.
    */

   public Type getElement() {
      return typeElement;
   }

   /**
    * Modifie le type des indices de ce type.
    */

   public void setIndice(Type typeIndice) {
      this.typeIndice = typeIndice;
   }

   /**
    * Le type des éléments de ce type.
    */

   public void setElement(Type typeElement) {
      this.typeElement = typeElement;
   }

}

