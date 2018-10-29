------------------------------------------------------------------------
-- ma_lexico.l : source Aflex de l'analyseur lexical du langage       --
--               d'assemblage de la machine abstraite                 --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 17/11/94                           --
------------------------------------------------------------------------

BLANC       [ \t]
COMM_CAR    [\t\040-\377]
COMMENT     (";"{COMM_CAR}*)
SEPAR       ({BLANC}|{COMMENT})

CHIFFRE     [0-9]

NUM         ({CHIFFRE}+)
INTPOS      ("+"{NUM})
INTNAT      {NUM}
INTNEG      ("-"{NUM})
IMMPOS      (#{INTPOS})
IMMNAT      (#{INTNAT})
IMMNEG      (#{INTNEG})

SIGNE       ([+-]?)
EXP         (E{SIGNE}{NUM})
DEC         (#{SIGNE}{NUM}"."{NUM})
IMMREEL     ({DEC}|{DEC}{EXP})

LETTRE      [a-zA-Z]
IDENT       ({LETTRE}({LETTRE}|{CHIFFRE}|[_.])*)

CHAINE_CAR  [\040\041\043-\176]
CHSIMPLE    (\"{CHAINE_CAR}*\")
CHAINE      (\"({CHAINE_CAR}|\"\")+\")


%%


\n         { 
  yylval := (lex_autre, ligne_courante);
  ligne_courante := ligne_courante + 1; 
  return NL_lex;   }


{SEPAR}    { 
  null; }


{IDENT}    { 
  declare
    texte  : string(yytext'range) := yytext;
    att    : chaine;
    code   : token                := ETIQ_lex;
  begin
    normaliser(texte);
    mise_a_jour(texte, code, att);
    case code is
      when ETIQ_lex =>
        yylval := (lex_etiq, ligne_courante, att);
      when REGB_lex =>
        yylval := (lex_regb, ligne_courante, 
                   horner(texte( texte'first+1 ..  texte'last)));
      when others =>
        yylval := (lex_autre, ligne_courante);
    end case;
    return code;
  end; }


{INTPOS}   { 
  begin
    yylval := (lex_entier, ligne_courante, 
               entier(horner(yytext(yytext'first+1 .. yytext'last))));
    return DEPL_lex;
  exception
    when others => 
      erreur("depassement de capacite d'entier") ;
      raise MA_erreur_conversion;
  end ; }


{INTNAT}   { 
  begin
    yylval := (lex_entier, ligne_courante, entier(horner(yytext)));
    return DEPL_lex;
  exception
    when others => 
      erreur("depassement de capacite d'entier") ;
      raise MA_erreur_conversion;
  end ; }


{INTNEG}   { 
  begin
    yylval := (lex_entier, ligne_courante, 
               entier(-horner(yytext(yytext'first+1 .. yytext'last))));
    return DEPL_lex; 
  exception
    when others => 
      erreur("depassement de capacite d'entier") ;
      raise MA_erreur_conversion;
  end; }


{IMMPOS}   { 
  begin
    yylval := (lex_entier, ligne_courante, 
               entier(horner(yytext(yytext'first+2 .. yytext'last))));
    return CONSTENTPOS_lex; 
  exception
    when others => 
      erreur("depassement de capacite d'entier") ;
      raise MA_erreur_conversion;
  end; }


{IMMNAT}   { 
  begin
    yylval := (lex_entier, ligne_courante, 
               entier(horner(yytext(yytext'first+1 .. yytext'last))));
    return CONSTENTPOS_lex; 
  exception
    when others => 
      erreur("depassement de capacite d'entier") ;
      raise MA_erreur_conversion;
  end; }


{IMMNEG}   { 
  begin
    yylval := (lex_entier, ligne_courante, 
               entier(-horner(yytext(yytext'first+2 .. yytext'last))));
    return CONSTENTNEG_lex; 
  exception
    when others => 
      erreur("depassement de capacite d'entier") ;
      raise MA_erreur_conversion;
  end; }


{IMMREEL}  {
  declare 
    val_reel : reel; 
    long     : positive; 
    temp     : string(yytext'first+1 .. yytext'last);
  begin
    temp := yytext(yytext'first+1 .. yytext'last);
    get(temp, val_reel, long);
    yylval := (lex_reel, ligne_courante, val_reel);
    return (CONSTREEL_lex); 
  exception
    when others => 
      erreur("depassement de capacite de reel") ;
      raise MA_erreur_conversion;
  end; }


"("        { 
  yylval := (lex_autre, ligne_courante);
  return '('; }


")"        { 
  yylval := (lex_autre, ligne_courante);
  return ')'; }


","        { 
  yylval := (lex_autre, ligne_courante);
  return ','; }


{CHSIMPLE} { --  Les chaines simples sont plus faciles a convertir.
  yylval := (lex_chaine, ligne_courante, valeur_chaine(yytext));
  return CONSTCHAINE_lex; }


{CHAINE}   { -- Il faut convertir les chaines dans le cas general 
  yylval := (lex_chaine, ligne_courante, valeur_chaine2(yytext));
  return CONSTCHAINE_lex; }


":"        { 
  yylval := (lex_autre, ligne_courante);
  return ':'; }


.          { 
  erreur("caractere " & yytext(1) & " non permis"); 
  raise MA_erreur_lexicale ; }

%%

with MA_DICT, TYPES_BASE, REEL_ES;
use  MA_DICT, TYPES_BASE, REEL_ES;

pragma Elaborate_All (MA_Dict);
package body MA_LEXICO is

  subtype Chiffre is Character range '0' .. '9';
  chiffres       : constant array(Chiffre) of natural := 
                                (0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    -- pour convertir un chiffre en sa valeur numerique

  ligne_courante :          positive := 1; -- : numero de ligne courante

  -----------------------------------------------------------------------------

  function num_ligne return positive is
  begin
     return ligne_courante;
  end num_ligne;

  -----------------------------------------------------------------------------

  function valeur_chaine (S : string) return chaine is
  -- retourne une copie protegee de la constante chaine de caracteres
  begin
    return creation(S(S'first+1..S'last-1));
  end valeur_chaine;

  -----------------------------------------------------------------------------

  function valeur_chaine2 (S : string) return chaine is
  -- retourne une copie protegee de la constante chaine de caracteres
    temp         : string(S'range);
    indice_copie : integer         := temp'first;
    indice       : integer         := S'first + 1;
  begin
    while indice <= S'last-1 loop
      temp(indice_copie) := S(indice);
      indice_copie := indice_copie+1;
      if S(indice) /= '"' then 
        indice := indice + 1;
      else 
        indice := indice + 2; 
      end if;
    end loop;
    return creation(temp(temp'first .. indice_copie-1));
  end valeur_chaine2;

  -----------------------------------------------------------------------------

  procedure erreur(S : in string) is
  -- affichage de message d'erreur 
  begin
    put_line ("LIGNE " & positive'image(ligne_courante) & " -- ");
    put_line ("   Erreur : " & S);
  end erreur;

  -----------------------------------------------------------------------------

  function horner (S : string) return natural is
  -- convertit une chaine de chiffres en sa valeur numerique
    -- val : natural := 0;
  begin
    return Natural'Value (S);
    -- for i in S'range loop
      -- val := val * 10 + chiffres(S(i));
    -- end loop;
    -- return val;
  end horner;

  -----------------------------------------------------------------------------

  procedure normaliser  (S : in out string) is
  -- convertit une chaine d'alphanumeriques en majuscules
    diff : constant := character'pos('A')-character'pos('a');
  begin
    for i in S'range loop
      if S(i) in 'a'..'z' then 
        S(i) := character'val(character'pos(S(i)) + diff); 
      end if;
    end loop;
  end normaliser;

  -----------------------------------------------------------------------------

  function sup_esp(S : string) return string is
  -- supprime l'espace en seconde position de S
    S1 :         string(1 .. S'length-1);
  begin
    S1(1) := S(S'first);
    S1(2 .. S1'last) := S(S'first + 2 .. S'last);
    return S1;
  end sup_esp;

  -----------------------------------------------------------------------------

  procedure init_dico is
  -- initialise le dictionnaire avecles ``identificateurs'' autorises :
  -- codes op, LB et GB 
  begin
    ins_tok ("LOAD",    LOAD_lex);
    ins_tok ("STORE",   STORE_lex);
    ins_tok ("LEA",     LEA_lex);
    ins_tok ("PEA",     PEA_lex);
    ins_tok ("PUSH",    PUSH_lex);
    ins_tok ("POP",     POP_lex);
    ins_tok ("ADDSP",   ADDSP_lex);
    ins_tok ("SUBSP",   SUBSP_lex);
    ins_tok ("ADD",     ADD_lex);
    ins_tok ("SUB",     SUB_lex);
    ins_tok ("OPP",     OPP_lex);
    ins_tok ("MUL",     MUL_lex);
    ins_tok ("DIV",     DIV_lex);
    ins_tok ("CMP",     CMP_lex);
    ins_tok ("MOD",     MOD_lex);
    ins_tok ("FLOAT",   FLOAT_lex);
    ins_tok ("SEQ",     SEQ_lex);
    ins_tok ("SNE",     SNE_lex);
    ins_tok ("SGT",     SGT_lex);
    ins_tok ("SLT",     SLT_lex);
    ins_tok ("SGE",     SGE_lex);
    ins_tok ("SLE",     SLE_lex);
    ins_tok ("SOV",     SOV_lex);
    ins_tok ("INT",     INT_lex);
    ins_tok ("BRA",     BRA_lex);
    ins_tok ("BEQ",     BEQ_lex);
    ins_tok ("BNE",     BNE_lex);
    ins_tok ("BGT",     BGT_lex);
    ins_tok ("BLT",     BLT_lex);
    ins_tok ("BGE",     BGE_lex);
    ins_tok ("BLE",     BLE_lex);
    ins_tok ("BOV",     BOV_lex);
    ins_tok ("BSR",     BSR_lex);
    ins_tok ("RTS",     RTS_lex);
    ins_tok ("RINT",    RINT_lex);
    ins_tok ("RFLOAT",  RFLOAT_lex);
    ins_tok ("WINT",    WINT_lex);
    ins_tok ("WFLOAT",  WFLOAT_lex);
    ins_tok ("WSTR",    WSTR_lex);
    ins_tok ("WNL",     WNL_lex);
    ins_tok ("TSTO",    TSTO_lex);
    ins_tok ("HALT",    HALT_lex);

    ins_tok ("LB",      LB_lex);
    ins_tok ("GB",      GB_lex);

    for i in Num_reg_banalise loop
      ins_tok(sup_esp("R" & Num_reg_banalise'image(i)), REGB_lex);
    end loop;
  end init_dico;

-- code de yylex
##

begin
  init_dico;
end MA_LEXICO;
