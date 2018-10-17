package fr.esisar.compilation.verif;

import fr.esisar.compilation.global.src.*;

/** 
 * Résultat de l'opération unaireCompatible(noeud, t), où noeud représente 
 * une opération unaire et t est le type de l'expression.
 * Un objet de la classe ResultatUnaireCompatible a deux attributs : 
 * ok, qui vaut vrai ssi on peut appliquer l'opération représentée par 
 *      noeud au type t ;  
 * typeRes, qui donne le type du résultat de l'opération représentée 
 *      par noeud.
 * 
 */

public class ResultatUnaireCompatible {

   private boolean ok;
   private Type typeRes;

   /**
    * Retourne la valeur de l'attribut <code>ok</code>.
    */
   boolean getOk() {
      return ok;
   }

   /**
    * Retourne la valeur de l'attribut <code>typeRes</code>.
    */
   Type getTypeRes() {
      return typeRes;
   }

   /**
    * Modifie la valeur de l'attribut <code>ok</code>.
    */
   void setOk(boolean ok) {
      this.ok = ok;
   }

   /**
    * Modifie la valeur de l'attribut <code>typeRes</code>.
    */
   void setTypeRes(Type typeRes) {
      this.typeRes = typeRes;
   }
}

   
