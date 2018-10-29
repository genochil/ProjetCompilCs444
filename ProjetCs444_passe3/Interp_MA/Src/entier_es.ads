-------------------------------------------------------------------------------
--  Entier_ES : specification du paquetage
--
--  Auteur : un enseignant du projet GL
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

--|  Entrees/sorties sur le type de base Entier

with Types_Base, Ada.Text_IO;
use  Types_Base, Ada.Text_IO;

package Entier_ES is new Integer_IO (Entier);
