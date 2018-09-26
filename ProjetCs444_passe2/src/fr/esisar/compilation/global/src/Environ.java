package fr.esisar.compilation.global.src;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;

/** 
 * Un environnement qui permet d'associer des Defn à des chaînes de caractères.
 */

public class Environ {

   /**
    * La table qui contient l'environnement
    */
   private final Hashtable<String, Defn> table;

   /**
    * Constructeur d'environnement. 
    */
   public Environ() { 
      table = new Hashtable<String, Defn>();
   }

   /**
    * Enrichissement de l'environnement avec le couple (s, defn).
    * <ul>
    * <li> Si la chaîne s est présente dans l'environnement, 
    *      enrichir(s, defn) ne fait rien et retourne true. </li>
    * <li> Si la chaîne s n'est pas présente dans l'environnement, 
    *      enrichir(s, defn) ajoute l'association (s, defn) dans 
    *      l'environnement et retourne false. </li>
    * </ul>
    */
   public boolean enrichir(String s, Defn defn) {
      if (table.containsKey(s)) {
         return true;
      } else {
         table.put(s, defn);
         return false;
      }
   }

   /**
    * Cherche la defn associée à la chaîne s dans l'environnement.
    * Si la chaîne s n'est pas dans l'environnement, chercher(s) 
    * retourne null.
    */
   public Defn chercher(String s) {
      return table.get(s);
   }

   /**
    * Affiche cet environnement, avec un niveau de détails spécifié. 
    */
   public void afficher(int niveau) {
     Enumeration<String> keys = table.keys();
     while (keys.hasMoreElements()) {
        String s = "CHAINE : " + keys.nextElement() + " --> DEFN : ";
        Affichage.ecrire(s);
        Affichage.empiler(s.length(), "");
        Defn def = table.get(s);
        def.afficher(niveau);
        Affichage.depiler();
        Affichage.ecrire("\n\n");
     }
   }
     
}

