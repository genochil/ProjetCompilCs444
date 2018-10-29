------------------------------------------------------------------------
-- ima.ada : interprete-metteur-au-point de la machine abstraite      --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 28/11/94                           --
--   suppression de l'utilisation d'ENTIER_ES                         --
------------------------------------------------------------------------

with Ada.TEXT_IO, Ada.INTEGER_Text_IO, Ada.Command_Line, ASSEMBLEUR, PARTIE_OP,
     OPTIONS;
use  Ada.TEXT_IO, Ada.INTEGER_Text_IO, Ada.Command_Line, ASSEMBLEUR, PARTIE_OP,
     OPTIONS;

procedure ima is

  F : file_type; -- acces au ficher source
  no_cont,                     -- lorsqu'on ne peut continuer
  fin_prog : boolean := false; -- vrai en fin de programme


  -- Ces exceptions servent a programmer par exception :-)
  -- mais elles ne sont pas transmises a l'exterieur.
  pas_de_cont     : exception; -- il est impossible de continuer
  erreur_fichier  : exception; -- probleme avec le fichier d'entree
  
  ---------------------------------------------------------------------------

  procedure menu is
  begin
    --new_line;
    put_line("+----------------------------------------------------------------------+");
    put_line("| d : Demarrer l'execution jusqu'au premier point d'arret              |");
    put_line("| c : Continuer l'execution jusqu'au point d'arret suivant             |");
    put_line("| a : Ajouter un point d'arret                                         |");
    put_line("| e : Enlever un point d'arret                                         |");
    put_line("| s : Initialiser l'execution en mode pas a pas                        |");
    put_line("| x : Executer l'instruction courante                                  |");
    put_line("| i : Afficher l'instruction courante                                  |");
    put_line("| p : Afficher tout le programme avec les points d'arret               |");
    put_line("| l : Afficher le programme entre deux lignes, avec les points d'arret |");
    put_line("| r : Contenu des registres et des codes-condition                     |");
    put_line("| m : Contenu de la pile entre 2 adresses relatives a GB               |");
    put_line("| t : Temps d'execution depuis le debut de l'execution                 |");
    put_line("| ? ou h : Afficher ce menu                                            |");
    put_Line("| q : Quitter                                                          |");
    put_line("+----------------------------------------------------------------------+");
  end menu;

  ---------------------------------------------------------------------------
  
  procedure affich_prog(lgd, lgf : positive) is
  -- affiche le programme entre les lignes lgd et lgf
    ligne : string(1 .. 120); -- une ligne de programme
    longueur : natural; -- sa longueur
    t : positive; -- indentation
    nlcour : natural; -- numero de ligne courante
  begin
    if (lgd < 1) or (lgf > nb_lignes) or (lgd > lgf) then
      raise constraint_error;
    end if;
    reset(F);
    set_line(F, count(lgd));
    t := positive'image(lgf)'length - 1;
    begin
      nlcour := num_ligne_cour; 
    exception
      when erreur_PC =>
        nlcour := 0; -- PC hors du programme
    end;
    for i in lgd .. lgf loop
      if i = nlcour then
        put("--> ");
      else 
        put("    ");
      end if;
      if est_point(i) then
        put(" ## ");
      else
        put("    ");
      end if;
      put(i, width => t);
      put("| ");
      get_line(F, ligne, longueur);
      put_line(ligne(1 .. longueur));
    end loop;
  end affich_prog;
    
  ---------------------------------------------------------------------------
  
  procedure demarrer is
  begin
    fin_prog := false;
    init_mem;
    init_PC;
    charger_RI;
    init_temps;
    no_cont := true;
  end demarrer;

  ---------------------------------------------------------------------------
  
  procedure exec_jusqu_a_point is
    begin
      loop
        if est_point then
          finir_lecture;
          finir_ecriture;
          -- set_col(1);
          set_col(35+col);
          put(num_ligne_cour, 1);
          put(" : ");
          afficher_instr;
          new_line;
          exit;
        end if;
        exec_inst;
        charger_RI;
      end loop;
    exception
      when fin_du_programme =>
        finir_lecture;
        fin_prog := true;
        raise fin_du_programme;
      when erreur_exec_inst =>
        set_col(3);
        put_line("** IMA ** ERREUR ** Ligne" & 
                  positive'image(num_ligne_cour) & " : ");
        set_col(5);
        put_line(message_erreur_exec_inst);
        no_cont := true;
      when erreur_PC =>
        set_col(3);
        put_line("** IMA ** ERREUR ** Plus d'instructions !!");
        no_cont := true;
    end exec_jusqu_a_point;

  ---------------------------------------------------------------------------
  
  procedure initialiser_fichier is
  begin
    open(F, in_file, Argument (Argument_Count), "");
    assembler_charger(F);
  exception
    when erreur_lignes =>
      set_col(3);
      put_line("IMA -- Trop de lignes dans le fichier," &
                positive'image(max_lg_prog) & " maximum !!");
      raise erreur_fichier;
    when name_error =>
      set_col(3);
      put_line("IMA -- Fichier " & Argument (Argument_Count) & " introuvable");
      raise erreur_fichier;
    when end_error =>
      set_col(3);
      put_line("IMA *** ERREUR IMPOSSIBLE en fin de fichier !!");
      raise erreur_fichier;
  end initialiser_fichier;

  ---------------------------------------------------------------------------
  
  procedure mise_au_point is separate;
  
  ---------------------------------------------------------------------------
  
  procedure execution is
  begin
    verif_opt;
    initialiser_fichier;
    demarrer;
    exec_jusqu_a_point;
  exception
    when fin_du_programme =>
      if aff_temps then
        put_line("Temps d'execution : " & natural'image(temps_d_exec));
      end if;
  end execution;

  ---------------------------------------------------------------------------
  
begin -- ima
   verif_opt;
   case Argument_Count is
     when 1 => execution;
     when 2 => if debug then
                 mise_au_point;
               else
                 execution;
               end if;
     when others => 
       put_line ("Usage : " & Command_Name & " [-d | -t | -r] file.ass");
       return;
  end case;
exception
  when erreur_inst | erreur_syntaxe | erreur_fichier | mauvaise_option
    => null ;
end ima;

