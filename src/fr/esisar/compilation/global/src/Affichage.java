package fr.esisar.compilation.global.src;

/**
 * Cette classe permet de coordonner les affichages de différents objets
 * (arbres, types, defns...).
 * Le niveau d'affichage permet d'afficher plus ou moins de détails :
 * <ul>
 *    <li> 0 : pas de décors ; </li>
 *    <li> 1 : décors de la passe de vérification ; </li>
 *    <li> 2 : décors des passes de vérification et de génération de code. </li>
 * </ul>
 */

class Affichage {

   // Le numéro de colonne courant
   private static int numCol = 1;

   // Le nombre maximal de chaînes que l'on peut empiler
   private static final int maxString = 500;

   // La pile des longueurs des chaînes
   private static final int[] pile = new int[maxString];

   // Le sommet de pile
   private static int sommet = -1;

   // La taille maximale de toutes les chaînes empilées
   private static final int maxCar = 20 * maxString;

   // Les caractères de toutes les chaînes empilées
   private static char[] ligne = new char[maxCar];

   // Le nombre total de caractères
   private static int longueur = 0;

   // Le niveau courant d'affichage
   private static int niveauCourant = 0;


   /**
    * Le niveau courant d'affichage.
    */
   public static int getNiveau() {
      return niveauCourant;
   }

   /**
    * Change le niveau courant d'affichage à la valeur spécifiée.
    */
   public static void setNiveau(int niveau) {
      niveauCourant = niveau;
   }

   /**
    * Empile la chaîne s précédée de n espaces.
    */
   public static void empiler(int n, String s) {
      sommet++;
      pile[sommet] = longueur;
      for (int i = longueur + 1; i <= longueur + n; i++) {
         ligne[i] = ' ';
      }
      for (int i = longueur + n + 1; i <= longueur + n + s.length(); i++) {
         ligne[i] = s.charAt(i - longueur - n - 1);
      }
      longueur = longueur + n + s.length();
   }

   /**
    * Dépile la dernière chaîne empilée.
    */
   public static void depiler() {
      longueur = pile[sommet];
      sommet--;
   }

   /**
    * Affiche toutes les chaînes de la pile, précédées de n espaces, de la 
    * plus ancienne à la plus récente.
    */
   public static void commencer() {
      for (int i = 1; i <= longueur; i++) {
         ecrire(ligne[i]);
      }
   }

   /**
    * Retourne le nombre de caractères affichés par Affichage.commencer().
    */

   public static int longueur() {
      return longueur;
   }

   /**
    * Retourne true ssi la pile d'affichages est vide
    */

   public static boolean pileVide() {
      return sommet == -1;
   }

   /**
    * Affichage de la chaîne s.
    */
   public static void ecrire(String s) {
      for (int i = 0; i < s.length(); i++) {
         char c = s.charAt(i);
         ecrire(c);
      }
   }

   /**
    * Affichage du caractère c.
    */
   public static void ecrire(char c) {
      if (c == '\n') {
         numCol = 1;
      } else {
         numCol++;
      }
      System.out.print(c);
   }

   /**
    * Le numéro de colonne courant.
    */
   /**
    * Le numéro de colonne courant.
    */
   public static int getCol() {
      return numCol;
   }

   /**
    * Modifie le numéro de colonne courant, en affichant des espaces, après
    * être éventuellement allé à la ligne.
    */
   public static void setCol(int col) {
      if (numCol > col) {
         System.out.print("\n");
         numCol = 1;
      }
      for (int i = numCol; i < col; i++) {
         System.out.print(" ");
         numCol = col;
      }
   }
   
}

         
