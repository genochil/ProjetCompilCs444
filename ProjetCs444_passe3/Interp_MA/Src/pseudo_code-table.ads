-------------------------------------------------------------------------------
--  Pseudo_Code.Table : specification du paquetage
--
--  Auteur : un enseignant du projet Compilation
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

with Types_Base;
use  Types_Base;
private package Pseudo_Code.Table is

   -- Un element de la table est un couple (Chaine, etiq).

   type St_Element_Table (<>) is limited private;
   type Element_Table is access all St_Element_Table;

   procedure Chercher (S : in String;
                       A_Creer : in Boolean;
                       Present : out Boolean;
                       E : out Element_Table);
   -- Recherche S dans la table
   -- si elle y est deja, Present := True
   -- sinon, si A_Creer = True, un nouvel element E est ajoute
   -- tel que Acces_Chaine (E) = S et Acces_Etiq (E) = null.

   function Acces_Chaine (E : access St_Element_Table) return Chaine;
   -- Retourne la chaine associee a E.

   function Acces_Etiq (E : access St_Element_Table) return Etiq;
   -- Retourne l'etiq associee a E.

   procedure Changer_Etiq (E : access St_Element_Table; I : in Etiq);
   -- Modifie l'etiq associee a l'element de table E.

   Table_Codop : array (Code_Operation) of Chaine;

private

   type St_Cache_El_Table;
   type St_Element_Table is access all St_Cache_El_Table;

end Pseudo_Code.Table;
