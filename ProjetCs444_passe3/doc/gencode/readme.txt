
<GÉNÉRATION DU CODE>
 La génération de code est réalisé à partir de l'arbre généré lors de la passe 2.
On parcours l'arbre de manière descendante.
On parcours via les fonction coder_XXX, par exemple, coder_LIST_DECL() code tout ce qui correspond à la décalration des variables (« ADDSP »)
On effectue les vérifications suivantes
 - verification des bornes lors de l'affectation
 - verification d'overlfow sur les allocations de variables en pile

<GESTION DES REGISTRES>
Nous avons crée une classe Memory pour gérer les registres.
La méthode allocate() permet d'obtenir un registre non utilisé, elle retourne null si aucun registre n'est libre
La méthode liberate() permet de libérer un registre allouer précédemment afin de le réutiliser par la suite.

Les registres R0 et R1 sont réservés, le registre R1 pour les fonctions de lecture et d'écriture.
Le registre R0 est réservé pour une utilisation locale dans une fonction.
Les autres registres sont alloué et libéré via les fonction cités précédemment.

<GESTION DE LA PILE>
Pour simuler notre pile, nous avons utilisé une HashMap qui contient la position de la pile par rapport à GB et le nom de la variable 
Au début de la génération, on utilise la méthode add_var(Arbre a) qui permet d'ajouter une variable dans la HaspMap.
A la fin de LIST_DECL, on teste si l'ajout des variables n'a pas crée un débordement de pile et on incrémente SP(stack pointer).
La méthode get_var(String var) permet de récuperer la position en pile de la variable var.
La méthode add_new_var() permet d'allouer un emplacement en pile et retourne ce dernier, on peut libérer l'emplacement libérer avec la méthode free(int pos) avec pos la position en pile.
 

<PROBLEMES CONNUS>
Notre projet de gère pas les tableaux.
Erreur d'allocation des registres.
