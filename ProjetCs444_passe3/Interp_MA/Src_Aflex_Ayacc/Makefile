# Makefile pour generer les analyseurs lexicaux et syntaxiques de 
# la machine abstraite
#
# Remarque 
# Ce makefile :
#   - utilise adaflex et adayacc ; 
#   - recopie les fichiers Ada generes dans le repertoire ../Src

all : les_fichiers_lex les_fichiers_yacc

les_fichiers_lex : ma_lexico.adb \
   ma_lexico_dfa.ads ma_lexico_dfa.adb ma_lexico_io.ads ma_lexico_io.adb

les_fichiers_yacc : ma_syntax.adb \
   ma_syntax_goto.ads ma_syntax_shift_reduce.ads ma_syntax_tokens.ads

ma_lexico.adb ma_lexico_dfa.ads ma_lexico_dfa.adb ma_lexico_io.ads \
   ma_lexico_io.adb : ma_lexico.l
	adaflex ma_lexico.l
	cp ma_lexico.adb ma_lexico_dfa.ads ma_lexico_dfa.adb \
           ma_lexico_io.ads ma_lexico_io.adb ../Src

ma_syntax.adb ma_syntax_goto.ads ma_syntax_shift_reduce.ads \
   ma_syntax_tokens.ads : ma_syntax.y
	adayacc ma_syntax.y
	cp ma_syntax.adb ma_syntax_goto.ads ma_syntax_shift_reduce.ads \
           ma_syntax_tokens.ads ../Src

