------------------------------------------------------------------------
-- partie_op.aff_val.adp : affichage de valenurs                      --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 21/11/94                           --
------------------------------------------------------------------------
with ENTIER_ES, REEL_ES;
use ENTIER_ES, REEL_ES;
separate(PARTIE_OP)
procedure afficher_valeur(V : valeur) is
begin
  case V.T is
    when typ_indef =>
      put ("<indefini>");
    when  typ_entier =>
      put(V.v_entier, 1);
    when typ_reel =>
      put(V.v_reel);
    when typ_adr_mem =>
      put("@ mem : "); 
      put(entier(V.v_adr_mem), 1); 
      put("(GB) , ");
      put(entier(V.v_adr_mem - le_LB.v_adr_mem), 1); 
      put("(LB)");
    when typ_adr_code =>
      put("@ code ligne "); 
      put(num_ligne(V.v_adr_code), 1);
      put(" : ");
      afficher_instr(V.v_adr_code);
    when others =>
      put("ERREUR VALEUR REGISTRE");
  end case;
end;
