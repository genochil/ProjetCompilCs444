------------------------------------------------------------------------
-- partie_op.calc_val.adp : calcul de la valeur d'un operande         --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 15/10/95                           --
------------------------------------------------------------------------

separate(PARTIE_OP)
function calc_val(dval : operande) return valeur is
  T_OP_DIR   : constant natural        := 0;
  T_OP_INDIR : constant natural        := 4;
  T_OP_INDEX : constant natural        := 5;
  T_OP_ENT   : constant natural        := 2;
  T_OP_REEL  : constant natural        := 2;
  A          :          adresse_pile;
  AXX        :          adresse_mem;
  ERm        :          entier;
begin
  case acces_nature(dval) is
    when op_direct => 
      temps := temps + T_OP_DIR;
      return Reg(acces_registre(dval));
    when op_indirect =>
      begin
        AXX := Reg(acces_base(dval)).v_adr_mem;
      exception
        when constraint_error =>
          erreur("Adressage indirect : la base ne contient pas une adresse");
          return (T => typ_indef); -- n'importe quoi pour eviter un warning
      end;
      begin
        A := AXX + adresse_mem(acces_deplacement(dval));
        temps := temps + T_OP_INDIR;
        return P(A);
      exception
        when constraint_error =>
          erreur("Adressage indirect : pas une adresse de la pile");
          return (T => typ_indef); -- n'importe quoi pour eviter un warning
      end;
    when op_indexe =>
      begin
        AXX := Reg(acces_base(dval)).v_adr_mem;
      exception
        when constraint_error =>
          erreur("Adressage indirect indexe : " &
                        "la base ne contient pas une adresse");
          return (T => typ_indef); -- n'importe quoi pour eviter un warning
      end;
      begin
        ERm := Reg(acces_index(dval)).v_entier;
      exception
        when constraint_error =>
          erreur("Adressage indirect indexe : " & 
                 "l'index ne contient pas un entier");
          return (T => typ_indef); -- n'importe quoi pour eviter un warning
      end;
      begin
        A := AXX + adresse_mem(ERm + acces_deplacement(dval));
        temps := temps + T_OP_INDEX;
        return P(A);
      exception
        when constraint_error =>
          erreur("Adressage indirect indexe : pas une adresse de la pile");
          return (T => typ_indef); -- n'importe quoi pour eviter un warning
      end;
    when op_entier =>
      temps := temps + T_OP_ENT;
      return (typ_entier, acces_entier(dval)); 
    when op_reel =>
      temps := temps + T_OP_REEL;
      return (typ_reel, acces_reel(dval)); 
    when op_chaine | op_etiq =>
      erreur("ERREUR IMPOSSIBLE !!!");
      return (T => typ_indef); -- n'importe quoi pour eviter un warning
  end case;
end calc_val;
