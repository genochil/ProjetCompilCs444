------------------------------------------------------------------------
-- partie_op.adb : corps de la "partie operative"                     --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 15/10/95                           --
------------------------------------------------------------------------

with Ada.TEXT_IO, Ada.INTEGER_Text_IO, ASSEMBLEUR, TYPES_BASE;
use  Ada.TEXT_IO, Ada.INTEGER_Text_IO, ASSEMBLEUR, TYPES_BASE;

package body PARTIE_OP is

  ----------------------------------------------------------------------------
  -- Types des valeurs des objets de la machine abstraite

  subtype adresse_mem is deplacement;

  type type_val is (typ_indef, typ_entier, typ_reel,
                    typ_adr_mem, typ_adr_code);

  type valeur(T : type_val := typ_indef) is record
    case T is
      when typ_indef =>
        null;
      when typ_entier => 
        v_entier : entier;
      when typ_reel => 
        v_reel : reel;
      when typ_adr_mem =>
        v_adr_mem : adresse_mem;
      when typ_adr_code =>
        v_adr_code : adresse_code;
    end case;
  end record;

  subtype val_adresse_mem is valeur(typ_adr_mem);

  -- "Bits" du registre instruction
  N : boolean := false;
  Z : boolean := false;
  OV : boolean := false;

  temps : natural;

  type message is access string;

  le_message_err_inst : message;

  lect_pas_finie : boolean := false;

  ecr_a_finir : boolean := false;

  ----------------------------------------------------------------------------
  -- Ce qui concerne les registres GB, LB et banalises

  type val_reg is array(registre) of valeur;

  Reg : val_reg; -- Les registres

  le_GB : val_adresse_mem renames Reg(GB);
  le_LB : val_adresse_mem renames Reg(LB);

  ----------------------------------------------------------------------------
  -- Ce qui concerne la pile

  -- noms des registres (pour l'affichage)
  taille_pile : constant adresse_mem := 10_000;

  subtype adresse_pile is adresse_mem range 1 .. taille_pile;

  type Pile is array(adresse_pile) of valeur; 

  P : Pile; -- La pile

  le_SP : val_adresse_mem := (typ_adr_mem, 0); -- Pointeur de pile

  procedure init_mem is
  begin
    Reg := (          (typ_adr_mem, 0), -- GB
                      (typ_adr_mem, 0), -- LB
            others => (T => typ_indef));
    P := (others => (T => typ_indef));
    le_SP := (typ_adr_mem, 0);
  end;

  ----------------------------------------------------------------------------
  -- Ce qui concerne les codes condition
  -- Ce qui suit devrait etre local a exec_inst...

  function EQ return boolean is
  begin
    return Z;
  end;

  function NE return boolean is
  begin
    return not Z;
  end;

  function GT return boolean is
  begin
    return not Z and then not N;
  end;

  function LT return boolean is
  begin
   return N;
  end;

  function GE return boolean is
  begin
    return not N;
  end;

  function LE return boolean is
  begin
    return Z or else N;
  end;
  
  procedure pos_CC_ent (V : entier) is
  begin
    N := V < 0;
    Z := V = 0;
  end;

  procedure pos_CC_reel(V : reel) is
  begin
    N := V < 0.0;
    Z := V = 0.0;
  end;

  procedure pos_CC(V : valeur) is
  begin
    case V.T is
      when typ_entier => 
        pos_CC_ent(V.v_entier);
      when typ_reel => 
        pos_CC_reel(V.v_reel);
      when others =>
        null;
    end case;
  end;

  ----------------------------------------------------------------------------

  function mess_typ_val (T : type_val) return string is
  begin
    case T is
      when typ_indef    => return "indefini";
      when typ_entier   => return "entier";
      when typ_reel     => return "reel";
      when typ_adr_mem  => return "adresse memoire";
      when typ_adr_code => return "adresse code";
    end case;
  end;

  procedure erreur(S : in string) is
  begin
    finir_lecture;
    le_message_err_inst := new string'(S);
    raise erreur_exec_inst;
  end erreur;

  function message_erreur_exec_inst return string is
  begin
    return le_message_err_inst.all;
  end;

  ----------------------------------------------------------------------------

  function calc_val(dval : operande) return valeur is separate;
  -- Remarque : en principe, cett procedure devrait etre locale a exec_inst

  function calc_adr(dval : operande) return adresse_mem is separate;
  -- Remarque : en principe, cett procedure devrait etre locale a exec_inst

  procedure exec_inst is separate;

  ----------------------------------------------------------------------------

  procedure afficher_valeur(V : valeur) is separate;
  -- procedure utilisee par les deux suivantes.

  procedure afficher_val_reg is separate;

  procedure afficher_val_pile(dmin, dmax : deplacement) is separate;

  ----------------------------------------------------------------------------

  procedure init_temps is
  begin
    temps := 0;
  end;

  function  temps_d_exec return natural is
  begin
    return temps;
  end;

  procedure finir_lecture is
  begin
    if lect_pas_finie then
      skip_line;
      lect_pas_finie := false;
    end if;
  end finir_lecture;

  procedure finir_ecriture is
  begin
    if ecr_a_finir then
      new_line;
      ecr_a_finir := false; -- ajoute 15/10/95
    end if;
  end finir_ecriture;

end PARTIE_OP;
