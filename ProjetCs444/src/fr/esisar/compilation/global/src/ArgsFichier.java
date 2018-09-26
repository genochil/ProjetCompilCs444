package fr.esisar.compilation.global.src;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

/** 
 * La classe ArgsFichier permet de récupérer, à l'aide de la fonction
 * ouvrir(args), soit le flot System.in, soit le flot correspondant 
 * au fichier args[0].
 */

public class ArgsFichier {

   /**
    * Constructeur privé, car la classe n'est pas destinée à être 
    * instanciée.
    */
   private ArgsFichier() { }

   /**
    * ouvrir(args) retourne : 
    * <ul>
    * <li> le flot System.in, si le tableau args est de taille 0,
    * </li>
    * <li> le flot correspondant au fichier args[0], si args est de taille 1.
    * </li>
    * </ul>
    * Si args est de taille supérieure ou égale à 2, ou si le fichier arg[0]  
    * ne peut pas être ouvert, un message d'erreur est affiché et 
    * le programme s'arrête.
    */
   public static InputStream ouvrir(String args[]){
      InputStream fichier = null;

      if (args.length == 0) {
         // On va lire sur l'entrée standard
         fichier = System.in;
      } else if (args.length == 1) {
         // On va lire le fichier args[0]
         try {
            fichier = new FileInputStream(args[0]);
         } catch (java.io.FileNotFoundException e) {
            // Exception levée si le fichier n'est pas trouvé
            System.err.println("Erreur : le fichier '" + args[0] +
                               "' n'existe pas ou est impossible a ouvrir");
            System.exit(1);
         }
      } else {
         System.err.println("Zero ou un argument attendu dans la commande");
         System.exit(1);
      }
      
      return fichier;
   }
   
   
   /** 
    * Renvoie le nom du fichier de sortie en fonction des paramètres d'entrée
    * sortie(args) retourne null si le tableau args n'est pas de longueur 1 ou si le nom
    * du fichier dans args[0] n'existe pas ;
    * sinon elle retourne le nom du fichier correspondant à args[Ø], remplacant 
    * l'extension cas par ass si elle est présente, ou ajoutant ".ass" par défaut
    */
   public static String sortie(String args[]) {

	   File f=null;
	   if (args.length == 0) {

		   return null;
	   } else if (args.length == 1) {
		   // On va lire le fichier args[0]

		   f=new File(args[0]);
		   if (!f.exists()) 
			   return null;
		   else
		   {
			   
			   String name=f.getName();
			   if (name.endsWith(".cas")) 
				   name=name.substring(0,name.length()-3)+"ass";
			   
			   else
				   name=name+"ass";
			   return name;
				   
		   }




	   }
	   else
		   return null;
   } 
}


