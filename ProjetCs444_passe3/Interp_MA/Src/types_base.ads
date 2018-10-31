-------------------------------------------------------------------------------
--  Types_Base : specification du paquetage
--
--  Auteur : un enseignant du projet GL
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

--|  Definition des types des litteraux de Mini-Pascal : Entier, Reel, Chaine
--|  Pour eviter la confusion entre le type predefini "string" et
--|  le type abstrait "chaine" dans le terme "chaine de caracteres",
--|  nous utilisons le franglais "string".

package Types_Base is

   -- ***** TYPES *****

   -- Les entiers acceptables dans un programme Mini-Pascal
   -- Quelle que soit la valeur effective de 'Valmax', on a toujours :
   --    Entier'First = - Valmax
   --    Entier'Last  =   Valmax

   type Entier is new Integer range - Integer'Last .. Integer'Last;

   Valmax : constant Entier := Entier'Last;

   -- Les reels acceptables dans un programme Mini-Pascal

   type Reel is new Long_Float;

   -- Type abstrait permettant de gerer facilement
   -- des strings constantes de taille quelconque

   type St_Chaine (<>) is limited private;
   type Chaine is access all St_Chaine;

   -- ***** CONSTRUCTEUR *****

   function Creation (S : String) return Chaine;
   -- Cree une nouvelle chaine de memes caracteres que S

   -- ***** SELECTEURS *****

   function Acces_String (C : access St_Chaine) return String;
   -- Retourne la string equivalente a C


   -- ***** DIVERS *****

   function Longueur (C : access St_Chaine) return Natural;
   -- Retourne le nombre de caracteres de C

private

   type St_Chaine is new String;

end Types_Base;
