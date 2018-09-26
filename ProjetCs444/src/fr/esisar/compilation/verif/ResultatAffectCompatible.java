package fr.esisar.compilation.verif;

/**
 * Un objet de la classe ResultatAffectCompatible a deux attributs : 
 * <ul>
 * <li> ok, qui vaut vrai ssi on peut affecter une expression de type t2
 *      à une place de type t1. </li>
 * <li> conv2, qui vaut vrai ssi il faut effectuer une conversion
 *      d'entier vers réel pour l'expression
 *      (affectation d'une expression entière à une place réelle). </li>
 * </ul>
 */

public class ResultatAffectCompatible {

   private boolean ok;
   private boolean conv2;

   /**
    * Retourne la valeur de l'attribut <code>ok</code>.
    */
   public boolean getOk() {
      return ok;
   }

   /**
    * Retourne la valeur de l'attribut <code>conv2</code>.
    */
   public boolean getConv2() {
      return conv2;
   }

   /**
    * Modifie la valeur de l'attribut <code>ok</code>.
    */
   public void setOk(boolean ok) {
      this.ok = ok;
   }

   /**
    * Modifie la valeur de l'attribut conv2.
    */
   public void setConv2(boolean conv2) {
      this.conv2 = conv2;
   }
}

