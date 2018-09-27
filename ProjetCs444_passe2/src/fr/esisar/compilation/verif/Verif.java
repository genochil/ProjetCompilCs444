package fr.esisar.compilation.verif;

import fr.esisar.compilation.global.src.*;
 
/**
 * Cette classe permet de réaliser la vérification et la décoration 
 * de l'arbre abstrait d'un programme.
 */
public class Verif {

   private Environ env; // L'environnement des identificateurs

   /**
    * Constructeur.
    */
   public Verif() {
      env = new Environ();
   }

   /**
    * Vérifie les contraintes contextuelles du programme correspondant à 
    * l'arbre abstrait a, qui est décoré et enrichi. 
    * Les contraintes contextuelles sont décrites 
    * dans Context.txt.
    * En cas d'erreur contextuelle, un message d'erreur est affiché et 
    * l'exception ErreurVerif est levée.
    */
   public void verifierDecorer(Arbre a) throws ErreurVerif {
      verifier_PROGRAMME(a);
   }

   /**
    * Initialisation de l'environnement avec les identificateurs prédéfinis.
    */
   private void initialiserEnv() {
      Defn def;
      // integer
      def = Defn.creationType(Type.Integer);
      def.setGenre(Genre.PredefInteger);
      env.enrichir("integer", def);
      
      // ------------
      // A COMPLETER
      // ------------
   
   // boolean
 		def = Defn.creationType(Type.Boolean);
 		def.setGenre(Genre.PredefBoolean);
 		env.enrichir("boolean", def);
 		//true
 		def = Defn.creationConstBoolean(true);
 		def.setGenre(Genre.PredefBoolean);
 		env.enrichir("true", def);
 		//false
 		def = Defn.creationConstBoolean(false);
 		def.setGenre(Genre.PredefBoolean);
 		env.enrichir("false", def);
 		// String
   		def = Defn.creationType(Type.String);
   		env.enrichir("string", def);
   		// real
   		def = Defn.creationType(Type.Real);
   		def.setGenre(Genre.PredefReal);
   		env.enrichir("real", def);
   		//max_int
   		def = Defn.creationConstInteger(java.lang.Integer.MAX_VALUE);
   		def.setGenre(Genre.PredefInteger);
   env.enrichir("max_int", def);
   }

   /**************************************************************************
    * PROGRAMME
    **************************************************************************/
   private void verifier_PROGRAMME(Arbre a) throws ErreurVerif {
      initialiserEnv();
      verifier_LISTE_DECL(a.getFils1());
      verifier_LISTE_INST(a.getFils2());
   }

   /**************************************************************************
    * LISTE_DECL
    **************************************************************************/
   private void verifier_LISTE_DECL(Arbre a) throws ErreurVerif {
      // A COMPLETER
   }

   /**************************************************************************
    * LISTE_INST
    **************************************************************************/
   private void verifier_LISTE_INST(Arbre a) throws ErreurVerif {
      // A COMPLETER
   }

   // ------------------------------------------------------------------------
   // COMPLETER les operations de vérifications et de décoration pour toutes 
   // les constructions d'arbres
   // ------------------------------------------------------------------------

}
