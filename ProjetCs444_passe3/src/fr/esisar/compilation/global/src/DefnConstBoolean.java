
package fr.esisar.compilation.global.src;

/**
 * Classe des defn de nature ConstBoolean.
 */

class DefnConstBoolean extends Defn {

   private boolean val;

   /**
    * Constructeur de defn de nature ConstBoolean.
    */
   DefnConstBoolean(boolean val) {
      super(NatureDefn.ConstBoolean, Type.Boolean); 
      this.val = val;
   }

   /**
    * La valeur d'une defn de nature ConstBoolean.
    */
   public boolean getValeurBoolean() {
      return val;
   }

   /**
    * Modifie la valeur d'une defn de nature ConstBoolean.
    */
   public void setValeurBoolean(boolean val) {
      this.val = val;
   }
}

