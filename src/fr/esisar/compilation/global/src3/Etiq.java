package fr.esisar.compilation.global.src3;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe des étiquettes de la machine abstraite.
 */

public class Etiq {

   private String chaine; // Le texte de l'étiquette

   private static Map<String,Etiq> listeEtiq = 
      new HashMap<String,Etiq>(); // Le catalogue des étiquettes existantes

   private static int compteur = 0; 
                        // Compteur pour fabriquer des nouvelles étiquettes.

   /**
    * Constructeur.
    * Construit une nouvelle étiquette à partir de la chaîne spécifiée.
    */
   private Etiq(String chaine) {
      this.chaine = chaine;
   }

   /**
    * Renvoie l'étiquette de chaîne spécifiée, si celle-ci a déjà été créée, 
    * sinon crée cette étiquette et la renvoie.
    */
   public static Etiq lEtiq(String chaine) {
      String chaineMin = chaine.toLowerCase();
      if (listeEtiq.containsKey(chaineMin)) {
         return listeEtiq.get(chaineMin);
      } else {
         Etiq etiq = new Etiq(chaineMin);
         listeEtiq.put(chaineMin, etiq);
         return etiq;
      }
   }

   /**
    * Renvoie l'étiquette ayant la chaîne (chaine + "." + n), si celle-ci 
    * a déjà été créée, sinon crée cette étiquette et la renvoie.
    */
   private static Etiq lEtiqNum(String chaine, int n) {
      return lEtiq(chaine + "." + n);
   }

   /**
    * Renvoie une étiquette ayant la chaîne (chaine + "." + n) où n est 
    * incrémenté à chaque appel de cette méthode.
    */
   public static Etiq nouvelle(String chaine) {
      compteur++;
      return lEtiqNum(chaine, compteur);
   }
      

   /**
    * La chaîne de caractères qui correspond à cette étiquette.
    */
   public String toString() {
      return chaine;
   }

}


