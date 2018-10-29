------------------------------------------------------------------------
-- ma_syntax.ads : interface de l'analyseur syntaxique de la MA       --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                           --
-- Date de derniere modification : 17/11/94                           --
------------------------------------------------------------------------

with MA_SYNTAX_TOKENS, PSEUDO_CODE; 
use  MA_SYNTAX_TOKENS, PSEUDO_CODE; 

package MA_SYNTAX is

  procedure analyser_construire_liste_lignes(L : out ligne; 
                                             nblignes : out natural);
  -- Analyse syntaxique et construction de la liste d'instructions du programme
  -- En cas d'erreur syntaxique leve l'exception MA_erreur_syntaxe.

  MA_erreur_syntaxe : exception renames MA_syntax_tokens.syntax_error;

end MA_SYNTAX;
