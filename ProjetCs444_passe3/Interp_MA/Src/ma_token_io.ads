------------------------------------------------------------------------
-- ma_token_io.ada : paquetage de lecture et d'ecriture de tokens     --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 01/94                                        --
-- Date de derniere modification :                                    --
------------------------------------------------------------------------

with TEXT_IO, MA_SYNTAX_TOKENS;
use  TEXT_IO, MA_SYNTAX_TOKENS;

package MA_TOKEN_IO is new ENUMERATION_IO(token);

