-------------------------------------------------------------------------------
--  Pseudo_Code : corps du paquetage
--
--  Auteur : un enseignant du projet Compilation
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

with Ada.Text_IO, Entier_ES, Reel_ES;
use  Ada.Text_IO, Entier_ES, Reel_ES;

with Pseudo_Code.Table; use Pseudo_Code.Table;

with Types_Base;

package body Pseudo_Code is

   -- Les operandes registre
   Table_Registres : array (Banalise) of Operande;

   -- Les operandes permis dans chaque instruction

   type Booleens is array (Nature_Operande) of Boolean;
   type Operandes is array (Code_Operation range <>) of Booleens;

   Rm : constant Booleens :=
      Booleens' (Op_Direct => True, others => false);
   DADR : constant Booleens :=
      Booleens' (Op_Indirect | Op_Indexe => True, others => False);
   DVAL : constant Booleens :=
      Booleens' (Op_Direct .. Op_Reel => True, others => False);

   Table_Op1 : constant Operandes :=
      Operandes' (
          Code_BSR .. Code_BOV => Booleens' (Op_Etiq => True, others => False),
          Code_SEQ .. Code_SOV => Rm,
          Code_ADDSP | Code_SUBSP | Code_TSTO =>
              Booleens' (Op_Entier => True, others => False),
          Code_PEA => DADR,
          Code_PUSH | Code_POP => Rm,
          Code_WSTR => Booleens' (Op_Chaine => True, others => False),
          Code_LOAD => DVAL,
          Code_STORE => Rm,
          Code_LEA => DADR,
          Code_ADD .. Code_FLOAT => DVAL);

   Table_Op2 : constant Operandes :=
      Operandes' (
          Code_LOAD => Rm,
          Code_STORE => DADR,
          Code_LEA => Rm,
          Code_ADD .. Code_FLOAT => Rm);

   -- 1. OPERATIONS SUR LE TYPE Etiq

   -------- ====== ------------------------
   function L_Etiq (S : String) return Etiq is
      E : Element_Table;
      Existe : Boolean;
   begin
      Chercher (S, True, Existe, E);
      if Existe then
         if Acces_Etiq (E).Reserve then
            raise Erreur_Etiq_Illegale;
         end if;
      else
         Changer_Etiq (E, new St_Etiq' (False, Creation (S),
                                        null));
      end if;
      return Acces_Etiq (E);
   end L_Etiq;

   -------- ========== -------------------------------------
   function L_Etiq_Num (S : String; N : Natural) return Etiq is
      Sbis : String := Natural'Image (N);
   begin
      Sbis (1) := '.';
      return L_Etiq (S & Sbis);
   end L_Etiq_Num;

   -------- ============ ----------------------------------
   function Acces_String (E : access St_Etiq) return String is
   begin
      return Acces_String (E.Nom);
   end acces_String;

   -------- =========== ----------------------------------
   function Acces_Ligne (E : access St_Etiq) return Ligne is
   begin
      return E.Def;
   end Acces_Ligne;

   --------- ======== --------------------
   procedure Afficher (E : access St_Etiq) is
   begin
      Put (Acces_String (E.Nom));
   end Afficher;

   -- 2. OPERATIONS SUR LE TYPE Operande

   -------- =========== ------------------------------
   function Le_Registre (R : Banalise) return Operande is
   begin
      return Table_Registres (R);
   end Le_Registre;

   -------- ==================== ---------------------------------
   function Creation_Op_Indirect (D : Deplacement;
                                  Base : Registre) return Operande is
   begin
      return new St_Operande' (Op_Indirect, D, Base);
   end Creation_Op_Indirect;

   -------- ================== ----------------------------------
   function Creation_Op_Indexe (D : Deplacement;
                                Base : Registre;
                                Index : Banalise) return Operande is
   begin
      return new St_Operande' (Op_Indexe, D, Base, Index);
   end Creation_Op_Indexe;

   -------- ================== ----------------------------
   function Creation_Op_Entier (V : Entier) return Operande is
   begin
      return new St_Operande' (Op_Entier, V);
   end creation_Op_Entier;

   -------- ================ --------------------------
   function Creation_Op_Reel (V : Reel) return Operande is
   begin
      return new St_Operande' (Op_Reel, V);
   end Creation_Op_Reel;

   -------- ================== --------------------------------------
   function Creation_Op_Chaine (V : Chaine := null) return Operande is
   begin
      return new St_Operande' (Op_Chaine, V);
   end Creation_Op_Chaine;

   -------- ================ ------------------------------------
   function Creation_Op_Etiq (E : Etiq := null) return Operande is
   begin
      return new St_Operande' (Op_Etiq, E);
   end Creation_Op_Etiq;

   -------- ============ ------------------------------------------------
   function Acces_Nature (Op : access St_Operande) return Nature_Operande is
   begin
      return Op.Nature;
   end Acces_Nature;

   -------- ============== -----------------------------------------
   function Acces_Registre (Op : access St_Operande) return Banalise is
   begin
      return Op.Reg;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Registre;

   -------- ================= --------------------------------------------
   function Acces_Deplacement (Op : access St_Operande) return Deplacement is
   begin
      return Op.Dep;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Deplacement;

   -------- ========== -----------------------------------------
   function Acces_Base (Op : access St_Operande) return Registre is
   begin
      return Op.Base;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Base;

   -------- =========== -----------------------------------------
   function Acces_Index (Op : access St_Operande) return Banalise is
   begin
      return Op.Index;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Index;

   -------- ============ ---------------------------------------
   function Acces_Entier (Op : access St_Operande) return Entier is
   begin
      return Op.Val_Ent;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Entier;

   -------- ========== -------------------------------------
   function Acces_Reel (Op : access St_Operande) return Reel is
   begin
      return Op.Val_Reel;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Reel;

   -------- ============ ---------------------------------------
   function Acces_Chaine (Op : access St_Operande) return Chaine is
   begin
      return Op.Val_Chaine;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Chaine;

   -------- ========== -------------------------------------
   function Acces_Etiq (Op : access St_Operande) return Etiq is
   begin
      return Op.Val_Etiq;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Acces_Etiq;

   --------- ==================== ------------------------------
   procedure Changer_Deplacement (Op : access St_Operande;
                                  Nouveau_Dep : in Deplacement) is
   begin
      Op.Dep := Nouveau_Dep;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Deplacement;

   --------- ============ -----------------------------
   procedure Changer_Base (Op : access St_Operande;
                           Nouvelle_Base : in Registre) is
   begin
      Op.Base := Nouvelle_Base;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Base;

   --------- ============= ----------------------------
   procedure Changer_Index (Op : access St_Operande;
                            Nouvel_Index : in Banalise) is
   begin
      Op.Index := Nouvel_Index;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Index;

   --------- ============== ---------------------------
   procedure Changer_Entier (Op : access St_Operande;
                             Nouvel_Entier : in Entier) is
   begin
      Op.Val_Ent := Nouvel_Entier;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Entier;

   --------- ============ -------------------------
   procedure Changer_Reel (Op : access St_Operande;
                           Nouveau_Reel : in Reel)  is
   begin
      Op.Val_Reel := Nouveau_Reel;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Reel;

   --------- ============== -----------------------------
   procedure Changer_Chaine (Op : access St_Operande;
                             Nouvelle_Chaine : in Chaine) is
   begin
      Op.Val_Chaine := Nouvelle_Chaine;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Chaine;

   --------- ============ -------------------------
   procedure Changer_Etiq (Op : access St_Operande;
                           Nouvelle_Etiq : in Etiq) is
   begin
      Op.Val_Etiq := Nouvelle_Etiq;
   exception
      when Constraint_Error =>
         raise Erreur_Operande;
   end Changer_Etiq;

   --------- ======== -------------------------
   procedure Afficher (Op : access St_Operande) is
   begin
      case Op.Nature is
         when Op_Direct =>
            Put (Banalise'Image (Op.Reg));
         when Op_Indirect =>
            Put (Entier (Op.Dep), 1);
            Put (" (");
            Put (Registre'Image (Op.Base));
            Put (')');
         when Op_Indexe =>
            Put (Entier (Op.Dep), 1);
            Put (" (");
            Put (Registre'Image (Op.Base));
            Put (", ");
            Put (Banalise'Image (Op.Index));
            Put (')');
         when Op_Entier =>
            Put ('#');
            Put (Op.Val_Ent, 1);
         when Op_Reel =>
            Put ('#');
            Put (Op.Val_Reel, 1);
         when Op_Chaine =>
            Put ('"');
            declare
               S : String := Acces_String (Op.Val_Chaine);
            begin
               for I in S'Range loop
                  if S (I) = '"' then
                     Put ('"');
                  end if;
                  Put (S (I));
               end loop;
            end;
            Put ('"');
          when Op_Etiq =>
             Put (Acces_String (Op.Val_Etiq));
      end case;
   end Afficher;

   -- 3. OPERATIONS SUR LE TYPE Inst

   -------- ============== -----------------------------------
   function Creation_Inst0 (Code : Code_Operation) return Inst is
      Res : Inst := new St_Inst (Code);
   begin
      if Code not in Code_RTS .. Code_HALT then
         raise Erreur_Inst;
      end if;
      return Res;
   end Creation_Inst0;

   -------- ============== ------------------------------------
   function Creation_Inst1 (Code : Code_Operation;
                            Op1 : Operande := null) return Inst is
      Res : Inst := new St_Inst (Code);
   begin
      if (Code not in Code_BSR .. Code_WSTR) or else
         (Op1 /= null and then not Table_Op1 (Code) (Op1.Nature)) then
         raise Erreur_Inst;
      end if;
      Res.Op1 := Op1;
      return Res;
   end Creation_Inst1;

   -------- ============== -----------------------------------------
   function Creation_Inst2 (Code : Code_Operation;
                            Op1, Op2 : Operande := null) return Inst is
      Res : Inst := new St_Inst (Code);
   begin
      if (Code not in Code_LOAD .. Code_FLOAT) or else
         ((Op1 /= null and then not Table_Op1 (Code) (Op1.Nature)) or
          (Op2 /= null and then not Table_Op2 (Code) (Op2.Nature))) then
         raise Erreur_Inst;
      end if;
      Res.Op1 := Op1;
      Res.Op2 := Op2;
      return Res;
   end Creation_Inst2;

   -------- ==================== ------------------------------------------
   function Acces_Code_Operation (I : access St_Inst) return Code_Operation is
   begin
      return I.Codop;
   end Acces_Code_Operation;

   -------- ========= ------------------------------------
   function Acces_Op1 (I : access St_Inst) return Operande is
   begin
      return I.Op1;
   exception
      when Constraint_Error =>
         raise Erreur_Inst;
   end Acces_Op1;

   -------- ========= ------------------------------------
   function Acces_Op2 (I : access St_Inst) return Operande is
   begin
      return I.Op2;
   exception
      when Constraint_Error =>
         raise Erreur_Inst;
   end Acces_Op2;

   --------- =========== -------------------------
   procedure Changer_Op1 (I : access St_Inst;
                          Nouvel_Op : in Operande) is
   begin
      if (Nouvel_Op /= null and then
          not Table_Op1 (I.Codop) (Nouvel_Op.Nature)) then
         raise Erreur_Inst;
      end if;
      I.Op1 := Nouvel_Op;
   exception
      when Constraint_Error =>
         raise Erreur_Inst;
   end Changer_Op1;

   --------- =========== -------------------------
   procedure Changer_Op2 (I : access St_Inst;
                          Nouvel_Op : in Operande) is
   begin
      if (Nouvel_Op /= null and then
          not Table_Op2 (I.Codop) (Nouvel_Op.Nature)) then
         raise Erreur_Inst;
      end if;
      I.Op2 := Nouvel_Op;
   exception
      when Constraint_Error =>
         raise Erreur_Inst;
   end Changer_Op2;

   --------- ======== --------------------
   procedure Afficher (I : access St_Inst) is
   begin
      Put (Acces_String (Table_Codop (I.Codop)));
      Put (' ');
      case I.Codop is
         when Code_RTS .. Code_HALT =>
            null;
         when Code_BSR .. Code_WSTR =>
            Afficher (I.Op1);
         when Code_LOAD .. Code_FLOAT =>
            Afficher (I.Op1);
            Put (", ");
            Afficher (I.Op2);
      end case;
   end Afficher;


   -- 4. OPERATIONS SUR LE TYPE Ligne

   -------- ======== ----------------------------------------
   function Creation (E : Etiq := null;
                      I : Inst := null;
                      Com : String := "";
                      Suiv : Ligne := null;
                      Num_Ligne : Positive := 1) return Ligne is
      Res : Ligne := new St_Ligne' (E, I, new String' (Com), Suiv, num_Ligne);
   begin
      if (E /= null) then
         if (E.Def /= null) then
            raise Erreur_Double_Def;
         end if;
         E.Def := Res;
      end if;
      return Res;
   end Creation;

   -------- ========== ---------------------------------
   function Acces_Etiq (L : access St_Ligne) return Etiq is
   begin
      return L.Def_Etiq;
   end Acces_Etiq;

   -------- ========== ---------------------------------
   function Acces_Inst (L : access St_Ligne) return Inst is
   begin
      return L.Val_Inst;
   end Acces_Inst;

   -------- ============= -----------------------------------
   function Acces_Comment (L : access St_Ligne) return String is
   begin
      return L.Comment.all;
   end Acces_Comment;

   -------- ============= ----------------------------------
   function Acces_Suivant (L : access St_Ligne) return Ligne is
   begin
      return L.Suiv;
   end Acces_Suivant;

   -------- =============== -------------------------------------
   function Acces_Num_Ligne (L : access St_Ligne) return Positive is
   begin
      return L.Num_Ligne;
   end Acces_Num_Ligne;

   --------- ============ -------------------------
   procedure Changer_Etiq (L : access St_Ligne;
                           Nouvelle_Etiq : in Etiq) is
   begin
      if (Nouvelle_Etiq /= null) then
         if (Nouvelle_Etiq.Def /= null) then
            raise Erreur_Double_Def;
         end if;
         Nouvelle_Etiq.Def := Ligne (L);
      end if;
      if L.Def_Etiq /= null then
         L.Def_Etiq.Def := null;
      end if;
      L.Def_Etiq := Nouvelle_Etiq;
   end Changer_Etiq;

   --------- ============ -------------------------
   procedure Changer_Inst (L : access St_Ligne;
                           Nouvelle_Inst : in Inst) is
   begin
      L.Val_Inst := Nouvelle_Inst;
   end Changer_Inst;

   --------- =============== ------------------------
   procedure Changer_Comment (L : access St_Ligne;
                             Nouveau_Com : in String) is
   begin
      L.Comment := new String' (Nouveau_Com);
   end Changer_Comment;

   --------- =============== -------------------------
   procedure Changer_Suivant (L : access St_Ligne;
                              Nouveau_Suiv : in Ligne) is
   begin
      L.Suiv := Nouveau_Suiv;
   end Changer_Suivant;

   --------- ================= ---------------------------
   procedure Changer_Num_Ligne (L : access St_Ligne;
                                Nouveau_Num : in Positive) is
   begin
      L.Num_Ligne := Nouveau_Num;
   end Changer_Num_Ligne;

   --------- ======== ---------------------
   procedure Afficher (L : access St_Ligne) is
      Champ_Existant : Boolean := False;  -- indique s'il y a au moins un champ
   begin
      if L.Def_Etiq /= null then
         Afficher (L.Def_Etiq);
         Put (" :");
         Champ_Existant := True;
      end if;
      if L.Val_Inst /= null then
         if Champ_Existant then
            New_Line;
         end if;
         Put ("     ");
         Afficher (L.Val_Inst);
         Champ_Existant := True;
      end if;
      if L.Comment /= null then
         if Champ_Existant then
            Set_Col (40);
         end if;
         Put ("; ");
         Put (L.Comment.all);
      end if;
      New_Line;
   end Afficher;

   --------- ================== ---------------------
   procedure Afficher_Programme (L : access St_Ligne) is
      Cour : Ligne := Ligne (L);
   begin
      loop
         Afficher (Cour);
         Cour := Cour.Suiv;
         exit when Cour = null;
      end loop;
   end Afficher_Programme;


begin

   for R in Banalise loop
      Table_Registres (R) := new St_Operande' (Op_Direct, R);
   end loop;

end Pseudo_Code;
