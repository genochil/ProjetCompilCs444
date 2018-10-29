------------------------------------------------------------------------
-- options.ads : interface de recuperation et gestion des options     --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 16/10/95                                        --
-- Date de derniere modification :                                    --
------------------------------------------------------------------------

package OPTIONS is
  
  procedure verif_opt;
  -- verification des options

  function debug return boolean;
  -- vrai si option mise au point

  function aff_temps return boolean;
  -- vrai si option d'affichage du temps d'execution

  function beautify return boolean;
  -- vrai si option de retour a la ligne apres ecriture

  mauvaise_option : exception;
  -- levee si pas la bonne option, ou options incompatibles.

end OPTIONS;
