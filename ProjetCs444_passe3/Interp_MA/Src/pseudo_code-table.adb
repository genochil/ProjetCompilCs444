-------------------------------------------------------------------------------
--  Pseudo_Code.Table : corps du paquetage
--
--  Auteur : un enseignant du projet Compilation
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

with Ada.Text_IO, Ada.Characters.Handling;
use  Ada.Text_IO, Ada.Characters.Handling;

with Types_Base;
pragma Elaborate_All (Types_Base);

package body Pseudo_Code.Table is

   -- plus Trace a une valeur importante, plus il y a d'affichage
   Trace : constant Natural := 0;

   type St_Cache_El_Table is
      record
         C : Chaine;              -- la chaine associee a l'entree
         I : Etiq;                -- l'etiq associee
         Gauche : Element_Table;  -- le sous-arbre gauche
         Droit : Element_Table;   -- le sous-arbre droit
      end record;

   type Table_Etiq is
      record
         Racine : Element_Table := null;
      end record;

   T : Table_Etiq;

   procedure Tracer (Niveau_Trace : in Positive; S : in String) is
   begin
      if Niveau_Trace <= Trace then
         Put_Line ("Pseudo_Code.Table : " & S);
      end if;
   end Tracer;

   procedure Chercher (S : in String;
                       A_Creer : in Boolean;
                       Present : out Boolean;
                       E : out Element_Table) is
      Pere, Cour, Nouveau : Element_Table;
      Existe : Boolean;
      M : String := To_Lower (S);

   begin
      Tracer (1, "recherche de la string " & S);
      Pere := null;
      Cour := T.Racine;
      while (Cour /= null) and then (M /= Acces_String (Cour.all.C)) loop
         Pere := Cour;
         if M < Acces_String (Cour.all.C) then
            Cour := Cour.all.Gauche;
         else
            Cour := Cour.all.Droit;
         end if;
      end loop;
      if Cour = null then
         Existe := false;
         if A_Creer then
            Nouveau :=
               new St_Element_Table'
                  (new St_Cache_El_Table' (Creation (M), null, null, null));
            if Pere = null then
               T.Racine := Nouveau;
            elsif M < Acces_String (Pere.all.C) then
               Pere.all.Gauche := Nouveau;
            else
               Pere.all.Droit := Nouveau;
            end if;
            E := Nouveau;
         else
            E := null;
         end if;
      else
         Existe := True;
         E := Cour;
      end if;
         Present := Existe;
         if Existe then
            Tracer (1, "        ... string trouvee");
         else
            Tracer (1, "        ... string non trouvee");
         end if;
   end Chercher;

   function Acces_Chaine (E : access St_Element_Table) return Chaine is
   begin
      return E.all.C;
   end Acces_Chaine;

   function Acces_Etiq (E : access St_Element_Table) return Etiq is
   begin
      return E.all.I;
   end Acces_Etiq;

   procedure Changer_Etiq (E : access St_Element_Table; I : in Etiq) is
   begin
      E.all.I := I;
   end Changer_Etiq;

   --  Initialisation des noms reserves


   procedure Ranger_Nom (S : in String; C : out Chaine) is
      Existe : Boolean;
      E : Element_Table;
   begin
      Chercher (S, True, Existe, E);
      Changer_Etiq (E, new St_Etiq' (True, Creation (S), null));
      C := Acces_Etiq (E).Nom;
   end Ranger_Nom;

   procedure Ranger_Nom_Registre (S : in String) is
      C : Chaine;
   begin
      Ranger_Nom (S, C);
   end Ranger_Nom_Registre;

   procedure Ranger_Nom_Codop (Code : in Code_Operation; S : in String) is
      C : Chaine;
   begin
      Ranger_Nom (S, C);
      Table_Codop (Code) := C;
   end Ranger_Nom_Codop;


begin
   for R in Registre loop
      Ranger_Nom_Registre (Registre'Image (R));
   end loop;

   for I in Code_Operation loop
      declare
         S : constant String := Code_Operation'Image (I);
      begin
         -- S est de la forme : "CODE_HALT"
         Ranger_Nom_Codop (I, S (S'First+5 .. S'Last));
      end;
   end loop;

end Pseudo_Code.Table;
