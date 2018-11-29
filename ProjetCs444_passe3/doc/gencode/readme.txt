<GESTION DES REGISTRES>
Nous avons crée une classe Memory pour gérer les registres.
La méthode allocate() permet d'obtenir un registre non utilisé, elle retourne null si aucun registre n'est libre
La méthode liberate() permet de libérer un registre allouer précédemment afin de le réutiliser par la suite.

<GESTION DE LA PILE>
Pour simuler notre pile, nous avons utilisé une HashMap qui contient la position de la pile par rapport à GB et le nom de la variable 
Au début de la génération, on utilise la méthode add_var(Arbre a) qui permet d'ajouter une variable dans la HaspMap.
A la fin de LIST_DECL, on teste si l'ajout des variables n'a pas crée un débordement de pile et on incrémente SP(stack pointer).
La méthode get_var(String var) permet de récuperer la position en pile de la variable var.
La méthode add_new_var() permet d'allouer un emplacement en pile et retourne ce dernier, on peut libérer l'emplacement libérer avec la méthode free(int pos) avec pos la position en pile.


<GÉNÉRATION DU CODE>
 La génération de code est réalisé à partir de l'arbre généré lors de la passe 2.
On parcours l'arbre de manière descendante, en vérifiant suivantes
 - verification des bornes lors de l'affectation
 - verification d'overlfow sur les allocations de variables en pile
 
Les registres R0 et R1 sont réservés, le registre R1 pour les fonctions de lecture et d'écriture.
Le registre R0 est réservé pour une utilisation locale dans une fonction.
Les autres registres sont 
 Utilisation de la pile:
 Certaines instructions complexes (copie de tableau) necessitent plusieurs variables temporaires, les 3 registres libres ne suffisent pas,
 dans ce cas il aurait été necessaire de recoder plusieurs fois la même instruction avec des allocations de registres/pile 
 ou de créer plus de registres libres, ce qui est censé être une exception...
 pour simplifier le code, nous avons seulement utilisé la pile et les 3 registres libres dans ce cas.
 Enfin la pile peut servir à allouer des variables locales pour l'évaluation des expressions quand il n'y a plus de registres de libre.
 
