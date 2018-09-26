package fr.esisar.compilation.global.src3;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe des programmes de la machine abstraite. Cette classe permet de 
 * construire un programme assembleur.
 */

public class Prog {

   // Le programme assembleur
   private static Prog prog = new Prog();  

   // Le programme assembleur est une liste de lignes
   private List<Ligne> liste;

   /**
    * Re-initialise le programme à une liste vide.
    */
   public static void initialiser() {
      prog = new Prog();
   }

   /**
    * L'instance du programme assembleur.
    */
   public static Prog instance() {
      return prog;
   }


   /** 
    * Etiquette prédéfinie pour les débordements d'intervalle. 
    * Cette étiquette est "Etiq_Debordement_Intervalle".
    */
   public static final Etiq L_Etiq_Debordement_Intervalle = 
      Etiq.lEtiq("Etiq_Debordement_Intervalle");

   /**
    * Etiquette prédéfinie pour les débordements arithmétiques.
    * Cette étiquette est "Etiq_Debordement_Arith".
    */
   public static final Etiq L_Etiq_Debordement_Arith = 
      Etiq.lEtiq("Etiq_Debordement_Arith");

   /**
    * Etiquette prédéfinie pour les débordements d'indice de tableau.
    * Cette étiquette est "Etiq_Debordement_Indice".
    */
   public static final Etiq L_Etiq_Debordement_Indice = 
      Etiq.lEtiq("Etiq_Debordement_Indice");

   /**
    * Etiquette prédéfinie pour le débordement de pile.
    * Cette étiquette est "Etiq_Pile_Pleine".
    */
   public static final Etiq L_Etiq_Pile_Pleine = 
      Etiq.lEtiq("Etiq_Pile_Pleine");


   /**
    * Constructeur de programme.
    */
   private Prog() {
      liste = new ArrayList<Ligne>();
   }

   /**
    * Le programme assembleur sous la forme d'une liste de lignes.
    */
   public List<Ligne> getListeLignes() {
      return liste;
   }

   /**
    * Ajoute une ligne à la fin du programme.
    */
   public static void ajouter(Ligne ligne) {
      prog.liste.add(ligne);
   }

   /**
    * Ajoute l'instruction à la fin du programme.
    */
   public static void ajouter(Inst inst) {
      prog.liste.add(new Ligne(null, inst, null));
   }

   /**
    * Ajoute l'instruction et le commentaire à la fin du programme.
    */
   public static void ajouter(Inst inst, String comment) {
      prog.liste.add(new Ligne(null, inst, comment));
   }

   /**
    * Ajoute l'étiquette à la fin du programme.
    */
   public static void ajouter(Etiq etiq) {
      prog.liste.add(new Ligne(etiq, null, null)); 
   }

   /**
    * Ajoute l'étiquette et le commentaire à la fin du programme.
    */
   public static void ajouter(Etiq etiq, String comment) {
      prog.liste.add(new Ligne(etiq, null, comment));
   }

   /**
    * Ajoute le commentaire à la fin du programme.
    */
   public static void ajouter(String comment) {
      prog.liste.add(new Ligne(null, null, comment)); 
   }

   /**
    * Ajoute le commentaire à la fin du programme, entouré de "-".
    */
   public static void ajouterComment(String comment) {
      int nbTirets = (70 - comment.length()) / 2;
      for (int i = 0; i < nbTirets; i++) {
         comment = "-" + comment + "-"; 
      }
      if (comment.length() < 70) {
         comment = comment + "-"; 
      }
      ajouter(comment);
   }

   /**
    * Ajoute le commentaire à la fin du programme, encadré de deux lignes 
    * de "-".
    */
   public static void ajouterGrosComment(String comment) {
      String ligneTirets = 
      "----------------------------------------------------------------------";
      ajouterComment(ligneTirets);
      int nbEsp = (70 - comment.length()) / 2;
      for (int i = 0; i < nbEsp; i++) {
         comment = " " + comment;
      }
      ajouterComment(comment);
      ajouterComment(ligneTirets);
   }

   /**
    * Affiche le programme complet.
    */
   public static void afficher() {
      for (Ligne ligne : prog.liste) {
         System.out.print(ligne.toString());
      }
   }
}
