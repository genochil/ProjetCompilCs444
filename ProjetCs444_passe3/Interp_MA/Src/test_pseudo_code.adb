-------------------------------------------------------------------------------
--  Test_Pseudo_Code : corps de procedure
--
--  Auteur : un enseignant du projet Compilation
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

with Types_Base, Pseudo_Code, Ada.Text_IO;
use  Types_Base, Pseudo_Code, Ada.Text_IO;

procedure Test_Pseudo_Code is
   O : Operande;
   I : Inst;
   E : Etiq;
   L : Ligne;

begin
   -- etiquette sans instruction
   I := null;
   E := L_Etiq ("taratata");
   L := Creation (E, I, "coucou", null);

   -- inst etiquetee
   I := Creation_Inst2 (Code_LOAD, Le_Registre (R0), Le_Registre (R1));
   E := L_Etiq ("tagada");
   L := Creation (E, I, "hello", L);

   -- ligne blanche
   L := Creation (Suiv => L);

   -- ligne reduite a un commentaire
   L := Creation (Com => "commentaire seul", Suiv => L);

   -- utilisation d'etiquette deja definie
   I := Creation_Inst1 (Code_BRA, Creation_Op_Etiq (E));
   L := Creation (I => I, Suiv => L);

   -- utilisation d'etiquette numerique non definie
   E := L_Etiq_Num ("abc", 13);
   I := Creation_Inst1 (Code_BGT, Creation_Op_Etiq (E));
   L := Creation (I => I, Suiv => L);

   -- operandes
   O := Creation_Op_Indirect (-3, GB);
   I := Creation_Inst1 (Code_PEA, O);
   L := Creation (null, I, "", L);
   O := Creation_Op_Indexe (7, LB, R1);
   I := Creation_Inst2 (Code_ADD, O, Le_Registre (R15));
   L := Creation (null, I, "", L);
   O := Creation_Op_Entier (421);
   I := Creation_Inst2 (Code_LOAD, O, Le_Registre (R1));
   L := Creation (null, I, "", L);
   O := Creation_Op_Reel (3.1416);
   I := Creation_Inst2 (Code_LOAD, O, Le_Registre (R1));
   L := Creation (null, I, "", L);

   -- inst sans operande
   I := Creation_Inst0 (Code_HALT);
   L := Creation (null, I, "", L);

   -- WSTR
   I := Creation_Inst1 (Code_WSTR,
           Creation_Op_Chaine (Creation ("abc""de")));
   L := Creation (null, I, "", L);
   Afficher_Programme (L);

   -- test double def d'etiquette
   begin
      Changer_Etiq (L, L_Etiq ("tagada"));
      Put_Line ("*** PB Double_Def");
   exception
      when Erreur_Double_Def =>
         null;
   end;

   -- test de nom reserve
   begin
      E := L_Etiq ("pEa");
      Put_Line ("*** PB nom reserve");
   exception
      when Erreur_Etiq_Illegale =>
         null;
   end;

   -- test operandes illegaux
   begin
      I := Creation_Inst1 (Code_PEA, Le_Registre (R0));
      Put_Line ("*** PB operandes");
   exception
      when Erreur_Inst =>
         null;
   end;
end test_Pseudo_Code;
