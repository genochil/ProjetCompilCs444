package fr.esisar.compilation.global.src3;

/**
 * Le type des différentes natures d'opérande de la machine abstraite.
 */

public enum NatureOperande {

   /** Opérande direct : pour l'adressage direct par registre. */
   OpDirect,   

   /** Opérande indirect : pour l'adressage indirect avec déplacement. */
   OpIndirect, 

   /** Opérande indexé : pour l'adressage indirect indexé avec déplacement. */
   OpIndexe,   

   /** Opérande entier : pour l'adressage immédiat entier. */ 
   OpEntier,   

   /** Opérande réel : pour l'adressage immédiat réel. */ 
   OpReel,     

   /** Opérande chaîne. */
   OpChaine,   

   /** Opérande étiquette. */
   OpEtiq;     
}

