
-- corps du paquetage d'analyse syntaxique

with Ada.TEXT_IO, MA_LEXICO, MA_SYNTAX_SHIFT_REDUCE, MA_SYNTAX_GOTO, TYPES_BASE,
     MA_DETIQ, PSEUDO_CODE; 
use  Ada.TEXT_IO, MA_LEXICO, MA_SYNTAX_SHIFT_REDUCE, MA_SYNTAX_GOTO, TYPES_BASE,
     MA_DETIQ, PSEUDO_CODE; 

package body MA_SYNTAX is

  function R(n : Num_reg_banalise) return banalise is
  begin
    return banalise'val(n + banalise'pos(banalise'first));
  end R;

  function creation_direct(n : Num_reg_banalise) return operande is
  begin
    return le_registre(R(n));
  end creation_direct;

  procedure yyerror(text : in string) is
  -- affiche un message d'erreur
  begin
    put_line("***** ligne" & positive'image(num_ligne) & " : " & text);
  end yyerror;

  
  -- yyparse
procedure YYParse is

   -- Rename User Defined Packages to Internal Names.
    package yy_goto_tables         renames
      Ma_Syntax_Goto;
    package yy_shift_reduce_tables renames
      Ma_Syntax_Shift_Reduce;
    package yy_tokens              renames
      Ma_Syntax_Tokens;

   use yy_tokens, yy_goto_tables, yy_shift_reduce_tables;

   procedure yyerrok;
   procedure yyclearin;


   package yy is

       -- the size of the value and state stacks
       stack_size : constant Natural := 300;

       -- subtype rule         is natural;
       subtype parse_state  is natural;
       -- subtype nonterminal  is integer;

       -- encryption constants
       default           : constant := -1;
       first_shift_entry : constant :=  0;
       accept_code       : constant := -3001;
       error_code        : constant := -3000;

       -- stack data used by the parser
       tos                : natural := 0;
       value_stack        : array(0..stack_size) of yy_tokens.yystype;
       state_stack        : array(0..stack_size) of parse_state;

       -- current input symbol and action the parser is on
       action             : integer;
       rule_id            : rule;
       input_symbol       : yy_tokens.token;


       -- error recovery flag
       error_flag : natural := 0;
          -- indicates  3 - (number of valid shifts after an error occurs)

       look_ahead : boolean := true;
       index      : integer;

       -- Is Debugging option on or off
        DEBUG : constant boolean := FALSE;

    end yy;


    function goto_state
      (state : yy.parse_state;
       sym   : nonterminal) return yy.parse_state;

    function parse_action
      (state : yy.parse_state;
       t     : yy_tokens.token) return integer;

    pragma inline(goto_state, parse_action);


    function goto_state(state : yy.parse_state;
                        sym   : nonterminal) return yy.parse_state is
        index : integer;
    begin
        index := goto_offset(state);
        while  integer(goto_matrix(index).nonterm) /= sym loop
            index := index + 1;
        end loop;
        return integer(goto_matrix(index).newstate);
    end goto_state;


    function parse_action(state : yy.parse_state;
                          t     : yy_tokens.token) return integer is
        index      : integer;
        tok_pos    : integer;
        default    : constant integer := -1;
    begin
        tok_pos := yy_tokens.token'pos(t);
        index   := shift_reduce_offset(state);
        while integer(shift_reduce_matrix(index).t) /= tok_pos and then
              integer(shift_reduce_matrix(index).t) /= default
        loop
            index := index + 1;
        end loop;
        return integer(shift_reduce_matrix(index).act);
    end parse_action;

-- error recovery stuff

    procedure handle_error is
      temp_action : integer;
    begin

      if yy.error_flag = 3 then -- no shift yet, clobber input.
      if yy.debug then
          Ada.Text_IO.put_line("Ayacc.YYParse: Error Recovery Clobbers " &
                   yy_tokens.token'image(yy.input_symbol));
      end if;
        if yy.input_symbol = yy_tokens.end_of_input then  -- don't discard,
        if yy.debug then
            Ada.Text_IO.put_line("Ayacc.YYParse: Can't discard END_OF_INPUT, quiting...");
        end if;
        raise yy_tokens.syntax_error;
        end if;

            yy.look_ahead := true;   -- get next token
        return;                  -- and try again...
    end if;

    if yy.error_flag = 0 then -- brand new error
        yyerror("Syntax Error");
    end if;

    yy.error_flag := 3;

    -- find state on stack where error is a valid shift --

    if yy.debug then
        Ada.Text_IO.put_line("Ayacc.YYParse: Looking for state with error as valid shift");
    end if;

    loop
        if yy.debug then
          Ada.Text_IO.put_line("Ayacc.YYParse: Examining State " &
               yy.parse_state'image(yy.state_stack(yy.tos)));
        end if;
        temp_action := parse_action(yy.state_stack(yy.tos), error);

            if temp_action >= yy.first_shift_entry then
                if yy.tos = yy.stack_size then
                    Ada.Text_IO.put_line(" Stack size exceeded on state_stack");
                    raise yy_Tokens.syntax_error;
                end if;
                yy.tos := yy.tos + 1;
                yy.state_stack(yy.tos) := temp_action;
                exit;
            end if;

        Decrement_Stack_Pointer :
        begin
          yy.tos := yy.tos - 1;
        exception
          when Constraint_Error =>
            yy.tos := 0;
        end Decrement_Stack_Pointer;

        if yy.tos = 0 then
          if yy.debug then
            Ada.Text_IO.put_line("Ayacc.YYParse: Error recovery popped entire stack, aborting...");
          end if;
          raise yy_tokens.syntax_error;
        end if;
    end loop;

    if yy.debug then
        Ada.Text_IO.put_line("Ayacc.YYParse: Shifted error token in state " &
              yy.parse_state'image(yy.state_stack(yy.tos)));
    end if;

    end handle_error;

   -- print debugging information for a shift operation
   procedure shift_debug(state_id: yy.parse_state; lexeme: yy_tokens.token) is
   begin
       Ada.Text_IO.put_line("Ayacc.YYParse: Shift "& yy.parse_state'image(state_id)&" on input symbol "&
               yy_tokens.token'image(lexeme) );
   end;

   -- print debugging information for a reduce operation
   procedure reduce_debug(rule_id: rule; state_id: yy.parse_state) is
   begin
       Ada.Text_IO.put_line("Ayacc.YYParse: Reduce by rule "&rule'image(rule_id)&" goto state "&
               yy.parse_state'image(state_id));
   end;

   -- make the parser believe that 3 valid shifts have occured.
   -- used for error recovery.
   procedure yyerrok is
   begin
       yy.error_flag := 0;
   end yyerrok;

   -- called to clear input symbol that caused an error.
   procedure yyclearin is
   begin
       -- yy.input_symbol := yylex;
       yy.look_ahead := true;
   end yyclearin;


begin
    -- initialize by pushing state 0 and getting the first input symbol
    yy.state_stack(yy.tos) := 0;


    loop

        yy.index := shift_reduce_offset(yy.state_stack(yy.tos));
        if integer(shift_reduce_matrix(yy.index).t) = yy.default then
            yy.action := integer(shift_reduce_matrix(yy.index).act);
        else
            if yy.look_ahead then
                yy.look_ahead   := false;

                yy.input_symbol := yylex;
            end if;
            yy.action :=
             parse_action(yy.state_stack(yy.tos), yy.input_symbol);
        end if;


        if yy.action >= yy.first_shift_entry then  -- SHIFT

            if yy.debug then
                shift_debug(yy.action, yy.input_symbol);
            end if;

            -- Enter new state
            if yy.tos = yy.stack_size then
                Ada.Text_IO.put_line(" Stack size exceeded on state_stack");
                raise yy_Tokens.syntax_error;
            end if;
            yy.tos := yy.tos + 1;
            yy.state_stack(yy.tos) := yy.action;
              yy.value_stack(yy.tos) := yylval;

        if yy.error_flag > 0 then  -- indicate a valid shift
            yy.error_flag := yy.error_flag - 1;
        end if;

            -- Advance lookahead
            yy.look_ahead := true;

        elsif yy.action = yy.error_code then       -- ERROR

            handle_error;

        elsif yy.action = yy.accept_code then
            if yy.debug then
                Ada.Text_IO.put_line("Ayacc.YYParse: Accepting Grammar...");
            end if;
            exit;

        else -- Reduce Action

            -- Convert action into a rule
            yy.rule_id  := -1 * yy.action;

            -- Execute User Action
            -- user_action(yy.rule_id);


                case yy.rule_id is

when  1 =>
--#line  96
 
yyval := (nt_list_ligne, null, null); 

when  2 =>
--#line  98
 if 
yy.value_stack(yy.tos-1).val_list_ligne = null then
         
yyval := 
yy.value_stack(yy.tos);
       elsif 
yy.value_stack(yy.tos).val_list_ligne = null then
         
yyval := 
yy.value_stack(yy.tos-1);
       else
         changer_suivant(
yy.value_stack(yy.tos-1).val_dern_ligne, 
yy.value_stack(yy.tos).val_list_ligne); 
         
yyval := (nt_list_ligne, 
yy.value_stack(yy.tos-1).val_list_ligne, 
yy.value_stack(yy.tos).val_dern_ligne); 
       end if; 

when  3 =>
--#line  110
 
yyval := 
yy.value_stack(yy.tos-1); 

when  4 =>
--#line  112
 declare
          L : ligne := creation(null, 
yy.value_stack(yy.tos-1).val_instr, "", 
                                null, 
yy.value_stack(yy.tos-1).num_ligne_instr);
        begin
          if 
yy.value_stack(yy.tos-2).val_list_ligne = null then
            
yyval := (nt_list_ligne, L, L);
          else
            changer_suivant(
yy.value_stack(yy.tos-2).val_dern_ligne, L);
            
yyval := (nt_list_ligne, 
yy.value_stack(yy.tos-2).val_list_ligne, L);
          end if;
        end; 

when  5 =>
--#line  127
 
yyval := (nt_list_ligne, null, null); 

when  6 =>
--#line  129
 if 
yy.value_stack(yy.tos-1).val_list_ligne = null then
          
yyval := 
yy.value_stack(yy.tos);
        else
          changer_suivant(
yy.value_stack(yy.tos-1).val_dern_ligne, 
yy.value_stack(yy.tos).val_list_ligne);
          
yyval := (nt_list_ligne, 
yy.value_stack(yy.tos-1).val_list_ligne, 
yy.value_stack(yy.tos).val_dern_ligne);
        end if; 

when  7 =>
--#line  139
 declare
          E : etiq;
          L : ligne := creation(null, null, "", null, 
yy.value_stack(yy.tos-1).num_ligne_etiq);
        begin
          ins_def_etiq(acces_string(
yy.value_stack(yy.tos-1).val_etiq), E);
          changer_etiq(L, E);
          changer_ligne_def(E, L);
          
yyval := (nt_list_ligne, L, L);
        exception
          when MA_double_defetiq =>
            put_line("ERREUR ligne " & positive'image(
yy.value_stack(yy.tos-1).num_ligne_etiq) &
                     " : etiquette " & acces_string(
yy.value_stack(yy.tos-1).val_etiq) &
                     " deja definie !!");
            raise MA_double_defetiq;
          end; 

when  8 =>
--#line  158
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-3).num_ligne_autre,
               creation_inst2(code_LOAD, 
yy.value_stack(yy.tos-2).val_adressage, 
                                         creation_direct(
yy.value_stack(yy.tos).num_regb))); 

when  9 =>
--#line  162
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-3).num_ligne_autre,
               creation_inst2(code_STORE, creation_direct(
yy.value_stack(yy.tos-2).num_regb), 
                                          
yy.value_stack(yy.tos).val_adressage)); 

when  10 =>
--#line  166
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-3).num_ligne_autre,
               creation_inst2(code_LEA, 
yy.value_stack(yy.tos-2).val_adressage, 
                                        creation_direct(
yy.value_stack(yy.tos).num_regb))); 

when  11 =>
--#line  171
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_autre,
               creation_inst1(code_PEA, 
yy.value_stack(yy.tos).val_adressage)); 

when  12 =>
--#line  174
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_autre,
               creation_inst1(code_ADDSP, creation_op_entier(
yy.value_stack(yy.tos).val_ent))); 

when  13 =>
--#line  177
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_autre,
               creation_inst1(code_SUBSP, creation_op_entier(
yy.value_stack(yy.tos).val_ent))); 

when  14 =>
--#line  180
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-3).num_ligne_codop,
               creation_inst2(
yy.value_stack(yy.tos-3).val_code, 
yy.value_stack(yy.tos-2).val_adressage, 
                                           creation_direct(
yy.value_stack(yy.tos).num_regb))); 

when  15 =>
--#line  184
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-3).num_ligne_codop,
               creation_inst2(
yy.value_stack(yy.tos-3).val_code, 
yy.value_stack(yy.tos-2).val_adressage, 
                                           creation_direct(
yy.value_stack(yy.tos).num_regb))); 

when  16 =>
--#line  188
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-3).num_ligne_codop,
               creation_inst2(
yy.value_stack(yy.tos-3).val_code, 
yy.value_stack(yy.tos-2).val_adressage, 
                                           creation_direct(
yy.value_stack(yy.tos).num_regb))); 

when  17 =>
--#line  192
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_codop,
               creation_inst1(
yy.value_stack(yy.tos-1).val_code, creation_direct(
yy.value_stack(yy.tos).num_regb))); 

when  18 =>
--#line  195
 declare
          E : etiq;
        begin
          ins_util_etiq(acces_string(
yy.value_stack(yy.tos).val_etiq), E);
          
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_codop,
                 creation_inst1(
yy.value_stack(yy.tos-1).val_code, creation_op_etiq(E))); 
        end; 

when  19 =>
--#line  203
 
yyval := (nt_instr, 
yy.value_stack(yy.tos).num_ligne_codop, creation_inst0(
yy.value_stack(yy.tos).val_code)); 

when  20 =>
--#line  205
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_autre,
               creation_inst1(code_WSTR, creation_op_chaine(
yy.value_stack(yy.tos).val_chaine))); 

when  21 =>
--#line  208
 
yyval := (nt_instr, 
yy.value_stack(yy.tos-1).num_ligne_autre,
               creation_inst1(code_TSTO, creation_op_entier(
yy.value_stack(yy.tos).val_ent))); 

when  22 =>
--#line  214
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_ADD); 

when  23 =>
--#line  216
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SUB); 

when  24 =>
--#line  218
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_OPP); 

when  25 =>
--#line  220
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_MUL); 

when  26 =>
--#line  222
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_CMP); 

when  27 =>
--#line  224
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_DIV); 

when  28 =>
--#line  229
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_MOD); 

when  29 =>
--#line  231
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_FLOAT); 

when  30 =>
--#line  236
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_INT); 

when  31 =>
--#line  241
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SEQ); 

when  32 =>
--#line  243
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SNE); 

when  33 =>
--#line  245
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SGT); 

when  34 =>
--#line  247
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SLT); 

when  35 =>
--#line  249
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SGE); 

when  36 =>
--#line  251
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SLE); 

when  37 =>
--#line  253
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_SOV); 

when  38 =>
--#line  255
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_PUSH); 

when  39 =>
--#line  257
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_POP); 

when  40 =>
--#line  262
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_RTS); 

when  41 =>
--#line  264
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_RINT); 

when  42 =>
--#line  266
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_RFLOAT); 

when  43 =>
--#line  268
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_WINT); 

when  44 =>
--#line  270
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_WFLOAT); 

when  45 =>
--#line  272
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_WNL); 

when  46 =>
--#line  274
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_HALT); 

when  47 =>
--#line  279
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BRA); 

when  48 =>
--#line  281
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BEQ); 

when  49 =>
--#line  283
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BNE); 

when  50 =>
--#line  285
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BGT); 

when  51 =>
--#line  287
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BLT); 

when  52 =>
--#line  289
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BGE); 

when  53 =>
--#line  291
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BLE); 

when  54 =>
--#line  293
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BOV); 

when  55 =>
--#line  295
 
yyval := (nt_codop, 
yy.value_stack(yy.tos).num_ligne_autre, code_BSR); 

when  56 =>
--#line  300
 
yyval := 
yy.value_stack(yy.tos); 

when  57 =>
--#line  302
 
yyval := 
yy.value_stack(yy.tos); 

when  58 =>
--#line  307
 
yyval := 
yy.value_stack(yy.tos); 

when  59 =>
--#line  309
 
yyval := (nt_adressage,creation_direct(
yy.value_stack(yy.tos).num_regb)); 

when  60 =>
--#line  311
 
yyval := 
yy.value_stack(yy.tos); 

when  61 =>
--#line  316
 
yyval := 
yy.value_stack(yy.tos); 

when  62 =>
--#line  318
 
yyval := (nt_adressage,creation_direct(
yy.value_stack(yy.tos).num_regb)); 

when  63 =>
--#line  320
 
yyval := (nt_adressage, creation_op_reel(
yy.value_stack(yy.tos).val_reel)); 

when  64 =>
--#line  325
 
yyval := (nt_adressage, 
               creation_op_indirect(deplacement(
yy.value_stack(yy.tos-3).val_ent), 
yy.value_stack(yy.tos-1).val_reg)); 

when  65 =>
--#line  328
 
yyval := (nt_adressage,
               creation_op_indexe(deplacement(
yy.value_stack(yy.tos-5).val_ent), 
yy.value_stack(yy.tos-3).val_reg,
                                  R(
yy.value_stack(yy.tos-1).num_regb))); 

when  66 =>
--#line  335
 
yyval := (nt_adressage,creation_op_entier(
yy.value_stack(yy.tos).val_ent)); 

when  67 =>
--#line  337
 
yyval := (nt_adressage,creation_op_entier(
yy.value_stack(yy.tos).val_ent)); 

when  68 =>
--#line  342
 
yyval := (nt_reg, R(
yy.value_stack(yy.tos).num_regb)); 

when  69 =>
--#line  344
 
yyval := (nt_reg, LB); 

when  70 =>
--#line  346
 
yyval := (nt_reg, GB); 

                    when others => null;
                end case;


            -- Pop RHS states and goto next state
            yy.tos      := yy.tos - rule_length(yy.rule_id) + 1;
            if yy.tos > yy.stack_size then
                Ada.Text_IO.put_line(" Stack size exceeded on state_stack");
                raise yy_Tokens.syntax_error;
            end if;
            yy.state_stack(yy.tos) := goto_state(yy.state_stack(yy.tos-1) ,
                                 get_lhs_rule(yy.rule_id));

              yy.value_stack(yy.tos) := yyval;

            if yy.debug then
                reduce_debug(yy.rule_id,
                    goto_state(yy.state_stack(yy.tos - 1),
                               get_lhs_rule(yy.rule_id)));
            end if;

        end if;


    end loop;


end yyparse;

  procedure analyser_construire_liste_lignes(L : out ligne;
                                             nblignes : out natural) is
  begin
    yyparse;
    L := yyval.val_list_ligne;
    nblignes := num_ligne - 1;
  end analyser_construire_liste_lignes;

end MA_SYNTAX;
