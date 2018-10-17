package fr.esisar.compilation.verif;

import fr.esisar.compilation.global.src.*;

/**
 * Résultat de l'opération binaireCompatible(noeud, t1, t2), où noeud 
 * représente une opération binaire, t1 le type de la partie gauche et t2
 * le type de la partie droite.
 * Un objet de la classe ResultatBinaireCompatible a quatre attributs : 
 * <ul>
 * <li> ok, qui vaut vrai ssi on peut appliquer l'opération représentée par 
 *      noeud aux types t1 et t2 ; </li> 
 * <li> conv1, qui vaut vrai ssi t1 est de nature NatureType.Interval et 
 *      t2 est de nature NatureType.Real ; </li>
 * <li> conv2, qui vaut vrai ssi t1 est de nature NatureType.Real et 
 *      t2 est de nature NatureType.Interval. </li>
 * <li> typeRes, qui donne le type du résultat de l'opération représentée 
 *      par noeud. </li>
 * </ul>
 */

public class ResultatBinaireCompatible extends ResultatArithCompatible {

   private Type typeRes;

   /**
    * Retourne la valeur de l'attribut <code>typeRes</code>.
    */

   Type getTypeRes() {
      return typeRes;
   }

   /**
    * Modifie la valeur de l'attribut <code>typeRes</code>.
    */

   void setTypeRes(Type typeRes) {
      this.typeRes = typeRes;
   }

}

