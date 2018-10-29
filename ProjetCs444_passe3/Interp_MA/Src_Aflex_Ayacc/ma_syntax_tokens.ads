with  Types_Base, Pseudo_Code;
use   Types_Base, Pseudo_Code;
package Ma_Syntax_Tokens is

 
  subtype Num_reg_banalise is natural 
    range 0 ..  banalise'pos(banalise'last) - banalise'pos(banalise'first);

  -- discriminant de yystype
  type type_valeur is 
    (nt_list_ligne, nt_adressage, nt_instr, nt_codop, nt_reg,
     lex_etiq, lex_entier, lex_reel, lex_regb, lex_chaine, lex_autre);

  -- le type de yylval (voir MA_lexico.ads) et de yyval (voir MA_rules.y)
  type yystype (discriminant : type_valeur := lex_autre) is record
    case discriminant is
      when nt_list_ligne =>             -- liste de lignes
        val_list_ligne : ligne; 
        val_dern_ligne : ligne;
      when nt_adressage =>              -- adressage
        val_adressage : operande;
      when nt_instr =>                  -- ligne
        num_ligne_instr : positive;
        val_instr : inst;
      when nt_codop =>                  -- code op
        num_ligne_codop : positive;
        val_code : code_operation;
      when nt_reg =>
        val_reg : registre;
      when lex_etiq =>                  -- etiquette
        num_ligne_etiq : positive;
        val_etiq : chaine;
      when lex_entier =>                -- constante entier
        num_ligne_entier : positive;
        val_ent : entier;
      when lex_reel =>                  -- constante reelle
        num_ligne_reel : positive;
        val_reel : reel;
      when lex_regb =>                  -- registre banalise
        num_ligne_regb : positive;
        num_regb : Num_reg_banalise; 
      when lex_chaine =>                -- constante chaine
        num_ligne_chaine : positive;
        val_chaine : chaine;
      when lex_autre =>                 -- autres unites lexicales
        num_ligne_autre : positive;
    end case;
  end record;

    YYLVal, YYVal : YYSType; 
    type Token is
        (End_Of_Input, Error, Nl_Lex, Etiq_Lex,
         Regb_Lex, Depl_Lex, Constentpos_Lex,
         Constentneg_Lex, Constreel_Lex, Constchaine_Lex,
         Load_Lex, Store_Lex, Lea_Lex,
         Pea_Lex, Push_Lex, Pop_Lex,
         Addsp_Lex, Subsp_Lex, Add_Lex,
         Sub_Lex, Opp_Lex, Mul_Lex,
         Div_Lex, Cmp_Lex, Mod_Lex,
         Float_Lex, Seq_Lex, Sne_Lex,
         Sgt_Lex, Slt_Lex, Sge_Lex,
         Sle_Lex, Sov_Lex, Int_Lex,
         Bra_Lex, Beq_Lex, Bne_Lex,
         Bgt_Lex, Blt_Lex, Bge_Lex,
         Ble_Lex, Bov_Lex, Bsr_Lex,
         Rts_Lex, Rint_Lex, Rfloat_Lex,
         Wint_Lex, Wfloat_Lex, Wstr_Lex,
         Wnl_Lex, Tsto_Lex, Halt_Lex,
         Lb_Lex, Gb_Lex, ':',
         ',', '(', ')' );

    Syntax_Error : exception;

end Ma_Syntax_Tokens;
