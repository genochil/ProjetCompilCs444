package fr.esisar.compilation.verif;

/**
 * Résultat de l'opération arithCompatible(t1, t2), où t1 est le type de la
 * partie gauche et t2 est le type de la partie droite.
 * Un objet de le classe ResultatAffectCompatible a trois attributs :
 * <ul>
 * <li> ok, qui vaut vrai ssi t1 est de nature NatureType.Interval ou 
 *      NatureType.Real et t2 est de nature NatureType.Interval ou
 *      NatureType.Real ; </li>
 * <li> conv1, qui vaut vrai ssi t1 est de nature NatureType.Interval et 
 *      t2 est de nature NatureType.Real ; </li>
 * <li> conv2, qui vaut vrai ssi t1 est de nature NatureType.Real et 
 *      t2 est de nature NatureType.Interval. </li>
 * </ul>
 */

public class ResultatArithCompatible extends ResultatAffectCompatible {

   private boolean conv1;

   boolean getConv1() {
      return conv1;
   }

   void setConv1(boolean conv1) {
      this.conv1 = conv1;
   }
}


