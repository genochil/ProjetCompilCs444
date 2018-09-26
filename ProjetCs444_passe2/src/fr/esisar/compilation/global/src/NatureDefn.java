package fr.esisar.compilation.global.src;

/**
 * Le type des natures des defns.
 */
public enum NatureDefn {
   ConstInteger,
   ConstBoolean,
   Type,
   Var;

   /**
    * Affichage d'une nature de defn.
    */
   public String toString() {
      return "NatureDefn." + super.toString();
   }
}


