------------------------------------------------------------------------
-- lecture_reels.ads : lecture de reels machine abstraite             --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 28/11/94                                        --
------------------------------------------------------------------------

with TYPES_BASE;
use  TYPES_BASE;

package LECTURE_REELS is

  procedure lire_reel(x : out reel);
  -- Lit la representation decimale d'un reel machine abstraite
  -- eventuellement precede d'un signe '+' ou '-' (sans espace) et le range
  -- dans x.
  -- 
  -- Le reel (le signe le cas echeant) peut etre precede d'espaces et/ou de
  -- retour-chariot. La lecture s'arrete sur un espace ou un retour-chariot.
  -- La representation externe du reel doit tenir sur 80 caracteres.
  -- Sinon, CONSTRAINT_ERROR est levee.
  --
  -- La syntaxe des reels machine abstraite est la suivante :
  -- CHIFFRE = [0-9]
  -- NUM = {CHIFFRE}+
  -- DEC = {NUM}.{NUM}
  -- EXP = [Ee]([+-]?)
  -- REELMA = (DEC) | {DEC}{EXP}(NUM)
  --
  -- CONSTRAINT_ERROR ou NUMERIC_ERROR est levee si le reel est trop grand.
  -- DATA_ERROR est levee si ce qui est lu n'est pas un reel.
  -- On n'utilise pas le get de REEL_ES, car il n'est pas assez restrictif
  -- (par exemple, il accepte les '_')

end LECTURE_REELS;
