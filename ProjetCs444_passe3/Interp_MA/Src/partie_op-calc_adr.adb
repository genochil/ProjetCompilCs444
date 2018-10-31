------------------------------------------------------------------------
-- partie_op.calc_adr.adp : calcul de l'adresse d'un operande         --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 21/11/94                           --
------------------------------------------------------------------------

separate(PARTIE_OP)
function calc_adr(dval : operande) return adresse_mem is
  T_OP_INDIR : constant natural := 4;
  T_OP_INDEX : constant natural := 5;
  AXX : adresse_mem;
  ERm : entier;
begin
  case acces_nature(dval) is
    when op_indirect =>
      begin
        AXX := Reg(acces_base(dval)).v_adr_mem;
      exception
        when constraint_error =>
          erreur("Adressage indirect : la base ne contient pas une adresse");
          return taille_pile;  --  n'importe quoi pour eviter un warning
      end;
      begin
        temps := temps + T_OP_INDIR;
        return AXX + adresse_mem(acces_deplacement(dval));
      exception
        when constraint_error =>
          erreur("Adressage indirect : pas une adresse memoire");
          return taille_pile;  --  n'importe quoi pour eviter un warning
      end;
    when op_indexe =>
      begin
        AXX := Reg(acces_base(dval)).v_adr_mem;
      exception
        when constraint_error =>
          erreur("Adressage indirect indexe : " & 
                 "la base ne contient pas une adresse");
          return taille_pile;  --  n'importe quoi pour eviter un warning
      end;
      begin
        ERm := Reg(acces_index(dval)).v_entier;
      exception
        when constraint_error =>
          erreur("Adressage indirect indexe : " & 
                 "l'index ne contient pas un entier");
          return taille_pile;  --  n'importe quoi pour eviter un warning
      end;
      begin
        temps := temps + T_OP_INDEX;
        return AXX + adresse_mem(ERm + acces_deplacement(dval));
      exception
        when constraint_error =>
          erreur("Adressage indirect indexe : pas une adresse memoire");
          return taille_pile;  --  n'importe quoi pour eviter un warning
      end;
    when others =>
      put_line("IMPOSSIBLE !!!");
      raise erreur_exec_inst;
      return taille_pile;  --  n'importe quoi pour eviter un warning
  end case;
end;
