package fr.esisar.compilation.global.src;

/**
 * Le type des genres d'une defn.
 */

public enum Genre {
   NonPredefini,
   PredefInteger,
   PredefBoolean,
   PredefReal,
   PredefMaxInt,
   PredefTrue,
   PredefFalse;

   /**
    * String correspondant à un genre.
    */

   public String toString() {
      return "Genre." + super.toString();
   }
}

