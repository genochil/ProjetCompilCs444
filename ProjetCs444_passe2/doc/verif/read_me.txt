
La documentation des messages d'erreurs à été faite en javadoc via "ErreurContext.java" et est visible dans le fichier :
ProjetCompil/html/fr/esisar/compilation/ErreurContext.html
  
La documentation de l'architecture de la passe 2 à également été faite en javadoc via "Verif.java" et est visible dans le fichier : 
ProjetCompil/html/fr/esisar/compilation/Verif.html


Méthodologie de test :

- Dans un premier temps nous avons testé chaque fonction "verifier_X" de notre fichier Verif.java avec un exemple de programme Jcas lié à cette fonction. 
- Ensuite, nous avons testé notre compilateur par des exemples de programmes Jcas crées indépendament des fonctions de Verif.java
- Enfin, nous avons utilisé corbertura pour verifier notre programme final, les résultats retournés nous paraissent cependant incohérents et nous n'avons donc pas pu améliorer notre compilateur avec corbetura.
