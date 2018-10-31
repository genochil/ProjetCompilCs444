
with ENTIER_ES, REEL_ES;
use ENTIER_ES, REEL_ES;
separate(PARTIE_OP)
procedure afficher_val_pile(dmin, dmax : deplacement) is
  amin : adresse_pile;
  amax : adresse_pile;
  t : natural;
  prec : boolean;
  nbpr : natural range 0 .. 2;
begin
  amin := adresse_pile(dmin) - le_GB.v_adr_mem;
  amax := adresse_pile(dmax) - le_GB.v_adr_mem;
  t := adresse_pile'image(amax)'length - 1;
  for a in reverse amin .. amax loop
    prec := false;
    nbpr := 0;
    if a = le_LB.v_adr_mem then
      put("LB ");
      prec := true;
      nbpr := nbpr + 1;
    end if;
    if a = le_SP.v_adr_mem then
      put("SP ");
      prec := true;
      nbpr := nbpr + 1;
    end if;
    if prec then
      for i in nbpr .. 1 loop
        put("---");
      end loop;
      put("-> ");
    else
      put("         "); -- 9 espaces = 3("SP ") + 3("LB ") + 3("-> ")
    end if;
    put(entier(a), t);
    put(" | ");
    afficher_valeur(P(a));
    new_line;
  end loop;
exception
  when constraint_error =>
    raise erreur_exec_inst; -- nom peu explicite ici, mais c'est 
                            -- pour ne pas avoir a nommer une nouvelle
                            -- exception juste pour ce cas, qui serait
                            -- traitee exactement de la meme maniere que
                            -- erreur_exec_inst.
end;
