
package fr.esisar.compilation.global.src;

/**
 * Classe des defn de nature ConstInteger.
 */

class DefnConstInteger extends Defn {

   private int val;

   /**
    * Constructeur de defn de nature ConstInteger.
    */
   DefnConstInteger(int val) {
      super(NatureDefn.ConstInteger, Type.Integer); 
      this.val = val;
   }

   /**
    * La valeur d'une defn de nature ConstInteger.
    */
   public int getValeurInteger() {
      return val;
   }

   /**
    * Modifie la valeur d'une defn de nature ConstInteger.
    */
   public void setValeurInteger(int val) {
      this.val = val;
   }
}

