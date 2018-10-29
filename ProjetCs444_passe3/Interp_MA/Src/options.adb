------------------------------------------------------------------------
-- options.adb : implantation de la gestion des options               --
--                                                                    --
-- Auteur : X. Nicollin                                               --
--                                                                    --
-- Date de creation : 16/10/95                                        --
-- Date de derniere modification :                                    --
------------------------------------------------------------------------

with Ada.Command_Line, TEXT_IO;
use  Ada.Command_Line, TEXT_IO;

package body OPTIONS is
  
  tps : boolean := false; -- affichage du temps
  deb : boolean := false; -- mise au point
  ret : boolean := false; -- retour chariot


  procedure verif_opt is
    i1, i2 : integer;
    mauv_opt : exception;
  begin
    for i in 1 .. Argument_Count - 1 loop
      i1 := Argument(i)'first;
      i2 := Argument(i)'last;
      if i2-i1 /= 1 then
        raise mauv_opt;
      end if;
      if Argument (i)(i1) /= '-' then
        raise mauv_opt;
      end if;
      case Argument (i) (i2) is
        when 'd' => deb := true;
        when 't' => tps := true;
        when 'r' => ret := true;
        when others =>
          raise mauv_opt;
      end case;
    end loop;
  exception
    when mauv_opt =>
      put_line ("Usage : " & Command_Name & " [-d | -t | -r] file.ass");
      raise mauvaise_option;
  end verif_opt;

  function debug return boolean is
  begin
    return deb;
  end debug;

  function aff_temps return boolean is
  begin
    return tps;
  end aff_temps;

  function beautify return boolean is
  begin
    return ret;
  end beautify;

end OPTIONS;
