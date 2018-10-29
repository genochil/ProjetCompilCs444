------------------------------------------------------------------------
-- partie_op.exec_inst.adp : execution des instructions de la MA      --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Dates de modifications :                                           --
--   27/11/94 :                                                       --
--     acces_nom --> acces_string (changement dans tables.ads)        --
--   15/10/95 :                                                       --
--     Changements dans la syntaxe :                                  --
--       PUSH dval --> PUSH Rm                                        --
--       WINT, WFLOAT, RINT et RFLOAT deviennent unaires              --
--   01/11/95 :                                                       --
--     Changement de comportement sur RINT et RFLOAT :                --
--       On ne fait que positionner OV si pas un entier ou pas un     --
--     Le temps de calcul est ajoute meme si OV                       --
--   27/01/99 :                                                       --
--     Correction d'un bug lie a Ada 95 : en absence de               --
--     Machine_Overflows, il n'y a pas de Constraint_Error sur les    --
--     reels si debordement ou division par 0. On passe par une       --
--     triple conversion Reel'Image (Reel'Value (Reel'Image (X)),     --
--     avec un test explicite de caractere sur la second Reel'Image   --
--     pour retrouver Constraint_Error.                               --
--   29/01/99 :                                                       --
--     Simplification de la correction du 27/01/99 : il suffit de     --
--     tester X in Reel'Range.                                        --
--   03/02/99 :                                                       --
--     Simplification de l'implantation de INT grace a l'attribut     --
--     Reel'Truncation.                                               --
--   04/02/99 :                                                       --
--     Pour les resultats de calculs sur les Reels, on passe          --
--     systematiquement par 'Value ('Image) pour tenter de supprimer  --
--     les problemes d'arrondi (voir fonction La_Valeur).             --
------------------------------------------------------------------------

with LECTURE_ENTIERS, LECTURE_REELS, ENTIER_ES, REEL_ES, OPTIONS;
use LECTURE_ENTIERS, LECTURE_REELS, ENTIER_ES, REEL_ES, OPTIONS;
separate(PARTIE_OP)
procedure exec_inst is
  T_LOAD : constant natural := 2;
  T_STORE : constant natural := 2;
  T_LEA : constant natural := 0;
  T_PEA : constant natural := 4;
  T_PUSH : constant natural := 4;
  T_POP : constant natural := 2;
  T_ADDSP : constant natural := 4;
  T_SUBSP : constant natural := 4;
  T_ADDe : constant natural := 2;
  T_ADDr : constant natural := 2;
  T_SUBe : constant natural := 2;
  T_SUBr : constant natural := 2;
  T_OPPe : constant natural := 2;
  T_OPPr : constant natural := 2;
  T_MULe : constant natural := 35;
  T_MULr : constant natural := 20;
  T_CMPe : constant natural := 2;
  T_CMPr : constant natural := 2;
  T_DIVe : constant natural := 79;
  T_DIVr : constant natural := 40;
  T_MOD : constant natural := 79;
  T_FLOAT : constant natural := 4;
  T_Sccv : constant natural := 3;
  T_Sccf : constant natural := 2;
  T_INT : constant natural := 4;
  T_BRA : constant natural := 5;
  T_Bccv : constant natural := 5;
  T_Bccf : constant natural := 4;
  T_BSR : constant natural := 9;
  T_RTS : constant natural := 8;
  T_RINT : constant natural := 16;
  T_RFLOAT : constant natural := 16;
  T_WINT : constant natural := 16;
  T_WFLOAT : constant natural := 16;
  T_WSTR : constant natural := 16;
  T_WNL : constant natural := 14;
  T_TSTO : constant natural := 4;
  T_HALT : constant natural := 1;

  COD : code_operation;
  OP1, OP2 : operande;
  V1, V2 : valeur;
  T1, T2 : type_val;
  Rm : banalise;
  A1 : adresse_pile;
  AM : adresse_mem;

   function La_Valeur (X : Reel) return Reel is
   begin
      return Reel'Value (Reel'Image (X));
   end La_Valeur;

   function Test_Debord (X : Reel) return Reel is
   begin
      if X in Reel'Range then
         return X;
      else
         raise Constraint_Error;
      end if;
   end Test_Debord;

   -- function Test_Debord (X : Reel) return Reel is
      -- Z : Reel := Reel'Value (Reel'Image (X));
      -- T : String := Reel'Image (Z);
   -- begin
      -- if T (T'First + 1) not in '0' .. '9' then 
         -- --  T = " +Inf*************" ou T = "-Inf*************"
         -- raise Constraint_Error;
      -- end if;
      -- return Z;
   -- end Test_Debord;

begin -- exec_inst
  incr_PC;
  ecr_a_finir := false;
  COD := code_op;
  case COD is
    when code_LOAD =>
      OP1 := oper1;
      OP2 := oper2;
      V1 := calc_val(OP1);
      Reg(acces_registre(OP2)) := V1;
      pos_CC(V1);
      OV := false;
      temps := temps + T_LOAD;
    when code_STORE =>
      OP1 := oper1;
      V1 := Reg(acces_registre(OP1));
      OP2 := oper2;
      begin
        A1 := calc_adr(OP2);
        P(A1) := V1;
      exception
        when constraint_error =>
          erreur("STORE : Pas une adresse de la pile");
      end;
      pos_CC(V1);
      OV := false;
      temps := temps + T_STORE;
    when code_LEA =>
      OP1 := oper1;
      OP2 := oper2;
      AM := calc_adr(OP1);
      Reg(acces_registre(OP2)) := (typ_adr_mem, AM);
      temps := temps + T_LEA;
    when code_PEA =>
      begin
        OP1 := oper1;
        AM := calc_adr(OP1);
        le_SP.v_adr_mem := le_SP.v_adr_mem + 1;
        P(le_SP.v_adr_mem) := (typ_adr_mem, AM);
        temps := temps + T_PEA;
      exception
        when constraint_error =>
          erreur("PEA : Debordement de la pile");
      end;
    when code_PUSH =>
      begin
        OP1 := oper1;
        V1 := Reg(acces_registre(OP1));
        le_SP.v_adr_mem := le_SP.v_adr_mem + 1;
        P(le_SP.v_adr_mem) := V1;
        pos_CC(V1);
        OV := false;
        temps := temps + T_PUSH;
      exception
        when constraint_error =>
          erreur("PUSH : Debordement de la pile");
      end;
    when code_POP =>
      begin
        OP1 := oper1;
        V1 := P(le_SP.v_adr_mem);
        Reg(acces_registre(OP1)) := V1;
        le_SP.v_adr_mem := le_SP.v_adr_mem - 1;
        pos_CC(V1);
        OV := false;
        temps := temps + T_POP;
      exception
        when constraint_error =>
          erreur("POP : SP ne contient pas une adresse de la pile");
      end;
    when code_ADDSP =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        le_SP.v_adr_mem := le_SP.v_adr_mem + adresse_mem(V1.v_entier);
        temps := temps + T_ADDSP;
      exception
        when constraint_error =>
          erreur("ADDSP : Pas une adresse");
      end;
    when code_SUBSP =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        le_SP.v_adr_mem := le_SP.v_adr_mem - adresse_mem(V1.v_entier);
        temps := temps + T_SUBSP;
      exception
        when constraint_error =>
           erreur("SUBSP : Pas une adresse");
      end;
    when code_ADD =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        V2 := Reg(Rm);
        T2 := V2.T;
        if T1 = typ_entier and T2 = typ_entier then
          temps := temps + T_ADDe;
          Reg(Rm).v_entier := V2.v_entier  + V1.v_entier;
        elsif T1 = typ_reel and T2 = typ_reel then
          temps := temps + T_ADDr;
          Reg(Rm).v_reel := La_Valeur (Test_Debord (V2.v_reel  + V1.v_reel));
        else
          erreur("ADD avec op1 : " & mess_typ_val(T1) &
                 " et op2 : " & mess_typ_val(T2));
        end if;
        pos_CC(Reg(Rm));
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_SUB =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        V2 := Reg(Rm);
        T2 := V2.T;
        if T1 = typ_entier and T2 = typ_entier then
          temps := temps + T_SUBe;
          Reg(Rm).v_entier := V2.v_entier  - V1.v_entier;
        elsif T1 = typ_reel and T2 = typ_reel then
          temps := temps + T_SUBr;
          Reg(Rm).v_reel := La_Valeur (Test_Debord (V2.v_reel  - V1.v_reel));
        else
          erreur("SUB avec op1 : " & mess_typ_val(T1) &
                 " et op2 : " & mess_typ_val(T2));
        end if;
        pos_CC(Reg(Rm));
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_OPP =>
      OP1 := oper1;
      V1 := calc_val(OP1);
      T1 := V1.T;
      OP2 := oper2;
      Rm := acces_registre(OP2);
      if T1 = typ_entier then
        Reg(Rm) := (typ_entier, - V1.v_entier);
        temps := temps + T_OPPe;
      elsif T1 = typ_reel then
        Reg(Rm) := (typ_reel, La_Valeur (- V1.v_reel));
        temps := temps + T_OPPr;
      else
        erreur("OPP avec operande : " & mess_typ_val(T1));
      end if;
      pos_CC(Reg(Rm));
      OV := false;
    when code_MUL =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        V2 := Reg(Rm);
        T2 := V2.T;
        if T1 = typ_entier and T2 = typ_entier then
          temps := temps + T_MULe;
          Reg(Rm).v_entier := V2.v_entier  * V1.v_entier;
        elsif T1 = typ_reel and T2 = typ_reel then
          temps := temps + T_MULr;
          Reg(Rm).v_reel := La_Valeur (Test_Debord (V2.v_reel  * V1.v_reel));
        else
          erreur("MUL avec op1 : " & mess_typ_val(T1) &
                 " et op2 : " & mess_typ_val(T2));
        end if;
        pos_CC(Reg(Rm));
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_CMP =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        V2 := Reg(Rm);
        T2 := V2.T;
        if T1 = typ_entier and T2 = typ_entier then
          N := V2.v_entier < V1.v_entier;
          Z := V2.v_entier = V1.v_entier;
          temps := temps + T_CMPe;
        elsif T1 = typ_reel and T2 = typ_reel then
          N := V2.v_reel < V1.v_reel;
          Z := V2.v_reel = V1.v_reel;
          temps := temps + T_CMPr;
        else
          erreur("CMP avec op1 : " & mess_typ_val(T1) &
                 " et op2 : " & mess_typ_val(T2));
        end if;
        OV := false;
      -- exception
      --   when constraint_error =>
      --     OV := true;
      end;
    when code_DIV =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        V2 := Reg(Rm);
        T2 := V2.T;
        if T1 = typ_entier and T2 = typ_entier then
          temps := temps + T_DIVe;
          Reg(Rm).v_entier := V2.v_entier  / V1.v_entier;
        elsif T1 = typ_reel and T2 = typ_reel then
          temps := temps + T_DIVr;
          Reg(Rm).v_reel := La_Valeur (Test_Debord (V2.v_reel  / V1.v_reel));
        else
          erreur("DIV avec op1 : " & mess_typ_val(T1) &
                 " et op2 : " & mess_typ_val(T2));
        end if;
        pos_CC(Reg(Rm));
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_MOD =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        V2 := Reg(Rm);
        T2 := V2.T;
        if T1 = typ_entier and T2 = typ_entier then
          temps := temps + T_MOD;
          Reg(Rm).v_entier := V2.v_entier  mod V1.v_entier;
          pos_CC(Reg(Rm));
        else
          erreur("MOD avec op1 : " & mess_typ_val(T1) &
                 " et op2 : " & mess_typ_val(T2));
        end if;
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_FLOAT =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        if T1 = typ_entier then
          temps := temps + T_FLOAT;
          Reg(Rm) := (typ_reel,  La_Valeur (Test_Debord (reel(V1.v_entier))));
          pos_CC(Reg(Rm));
        else
          erreur("FLOAT avec operande : " & mess_typ_val(T1));
        end if;
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_SEQ =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if EQ then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_SNE =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if NE then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_SGT =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if GT then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_SLT =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if LT then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_SGE =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if GE then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_SLE =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if LE then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_SOV =>
      OP1 := oper1;
      Rm := acces_registre(OP1);
      if OV then 
        Reg(Rm) := (typ_entier, 1);
        temps := temps + T_Sccv;
      else 
        Reg(Rm) := (typ_entier, 0);
        temps := temps + T_Sccf;
      end if;
    when code_INT =>
      begin
        OP1 := oper1;
        V1 := calc_val(OP1);
        T1 := V1.T;
        OP2 := oper2;
        Rm := acces_registre(OP2);
        if T1 = typ_reel then
          declare 
            r : reel;
            b : entier;
          begin
            temps := temps + T_INT;
            r := V1.v_reel;
            b := Entier (Reel'Truncation (r));
            Reg(Rm) := (typ_entier, b); 
          end;
        else
          erreur("INT avec operande : " & mess_typ_val(T1));
        end if;
        OV := false;
      exception
        when constraint_error =>
          OV := true;
      end;
    when code_BRA =>
      OP1 := oper1;
      brancher(acces_etiq(OP1));
      temps := temps + T_BRA;
    when code_BEQ =>
      OP1 := oper1;
      if EQ then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BNE =>
      OP1 := oper1;
      if NE then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BGT =>
      OP1 := oper1;
      if GT then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BLT =>
      OP1 := oper1;
      if LT then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BGE =>
      OP1 := oper1;
      if GE then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BLE =>
      OP1 := oper1;
      if LE then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BOV =>
      OP1 := oper1;
      if OV then
        brancher(acces_etiq(OP1));
        temps := temps + T_Bccv;
      else
        temps := temps + T_Bccf;
      end if;
    when code_BSR =>
      begin
        OP1 := oper1;
        le_SP.v_adr_mem := le_SP.v_adr_mem + 2;
        P(le_SP.v_adr_mem - 1) := (typ_adr_code, contenu_PC);
        P(le_SP.v_adr_mem) := le_LB;
        le_LB.v_adr_mem := le_SP.v_adr_mem;
        brancher(acces_etiq(OP1));
        temps := temps + T_BSR;
      exception
        when constraint_error =>
          erreur("BSR : Debordement de la pile");
      end;
    when code_RTS =>
      declare
        AM : adresse_mem;
        AP : adresse_pile;
      begin
        begin
          AM := le_LB.v_adr_mem;
        exception
          when constraint_error => 
            erreur("RTS : LB ne contient pas une adresse ** IMPOSSIBLE **");
        end;
        begin
          AP := AM - 1;
        exception
          when constraint_error =>
            erreur("RTS : LB ne pointe pas sur la pile");
        end;
        begin
          charger_PC(P(AP).v_adr_code);
        exception
          when constraint_error => 
            erreur("RTS : Pas d'adresse de retour");
        end;
        begin
          le_SP.v_adr_mem := AM - 2;
        exception
          when constraint_error =>
             erreur("RTS : Pas une adresse memoire pour SP");
        end;
        begin
          le_LB.v_adr_mem := P(AM).v_adr_mem;
          temps := temps + T_RTS;
        exception
          when constraint_error =>
             erreur("RTS : Pas une adresse memoire pour LB");
        end;
      end;
    when code_RINT =>
      declare
        val_e : entier;
      begin
        temps := temps + T_RINT;
        lire_entier(val_e);
        pos_CC_ent(val_e);
        Reg(R1) := (typ_entier, val_e);
        OV := false;
        lect_pas_finie := true;
      exception
        when constraint_error =>
          OV := true;
          lect_pas_finie := true;
        when data_error =>
          OV := true;
          lect_pas_finie := true;
          -- lect_pas_finie := false;
          -- skip_line; -- pour manger ce qu'il reste sur la ligne
          -- erreur("RINT : pas un entier");
      end;
    when code_RFLOAT =>
      declare
        val_r : reel;
      begin
        temps := temps + T_RFLOAT;
        lire_reel(val_r);
        pos_CC_reel(val_r);
        Reg(R1) := (typ_reel, val_r);
        OV := false;
        lect_pas_finie := true;
      exception
        when constraint_error =>
          OV := true;
          lect_pas_finie := true;
        when data_error =>
          OV := true;
          lect_pas_finie := true;
          -- lect_pas_finie := false;
          -- skip_line; -- pour manger ce qu'il reste sur la ligne
          -- erreur("RFLOAT : pas un reel");
      end;
    when code_WINT =>
      V1 := Reg(R1);
      if V1.T /= typ_entier then
        erreur("WINT avec R1 " & mess_typ_val(V1.T));
      end if;
      put(V1.v_entier, WIDTH => 1);
      temps := temps + T_WINT;
      if beautify then
        new_line;
      else
        ecr_a_finir := true;
      end if;
    when code_WFLOAT =>
      V1 := Reg(R1);
      if V1.T /= typ_reel then
        erreur("WFLOAT avec R1 " & mess_typ_val(V1.T));
      end if;
      put(V1.v_reel, FORE => 1);
      temps := temps + T_WFLOAT;
      if beautify then
        new_line;
      else
        ecr_a_finir := true;
      end if;
    when code_WSTR =>
      OP1 := oper1;
      put(acces_string(acces_chaine(OP1)));
      temps := temps + T_WSTR;
      temps := temps + 2 * longueur(acces_chaine(OP1));
      if beautify then
        new_line;
      else
        ecr_a_finir := true;
      end if;
    when code_WNL =>
      new_line;
      temps := temps + T_WNL;
    when code_TSTO =>
      OP1 := oper1;
      OV := taille_pile < le_SP.v_adr_mem + adresse_mem(acces_entier(OP1));
      temps := temps + T_TSTO;
    when code_HALT =>
      temps := temps + T_HALT;
      raise fin_du_programme;
  end case;
end;
