-------------------------------------------------------------------------------
--  Types_Base : corps du paquetage
--
--  Auteur : un enseignant du projet GL
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

package body Types_Base is

   -------- ======== --------------------------
   function Creation (S : String) return Chaine is
   begin
      return new St_Chaine' (St_Chaine (S));
   end Creation;

   -------- ============ ------------------------------------
   function Acces_String (C : access St_Chaine) return String is
   begin
      return String (C.all);
   end Acces_String;

   -------- ======== -------------------------------------
   function Longueur (C : access St_Chaine) return Natural is
   begin
      return C'Length;
   end Longueur;

end Types_Base;

