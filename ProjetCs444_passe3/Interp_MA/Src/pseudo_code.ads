-------------------------------------------------------------------------------
--  Pseudo_Code : specification du paquetage
--
--  Auteur : un enseignant du projet Compilation
--  Affiliation : ENSIMAG
--
--  Historique :
--     19/10/98
--        - version initiale
-------------------------------------------------------------------------------

--|  Primitives pour produire du code machine abstraite
--|  (types abstraits Etiq, Operande, Inst, Ligne).

--|  Ce module est reutilise par l'Interprete de la Machine Abstraite,
--|  c'est pourquoi il definit plus de primitives que celles
--|  qui vous seront necessaires.

--|  Une Etiq est une etiquette de ligne, caracterisee par une chaine
--|  de caracteres.  Une etiquette est dite "definie" quand elle est
--|  associee a une ligne. Une etiquette ne peut pas etre simultanement
--|  associee a plusieurs lignes.

--|  Un Operande est un operande d'instruction. Ce peut etre :
--|     un registre banalise
--|     un mot designe par adressage indirect + deplacement
--|     un mot designe par adressage indirect indexe + deplacement
--|     une valeur immediate (Entier, Reel, Chaine)
--|     une etiquette (pour les branchements).

--|  Une Inst est une instruction de la machine abstraite.

--|  Une Ligne est une ligne de programme en langage d'assemblage, constituee de
--|     une definition d'etiquette
--|     une instruction
--|     un commentaire
--|     un acces a la ligne suivante
--|     un numero de ligne (inutile pour vous).
--|  Chacune de ces informations peut etre vide.

--|  A chacun de ces types est associee une procedure d'impression
--|  sur le flot standard de sortie.
--|  Il suffit donc de rediriger ce dernier et d'utiliser "Afficher_Programme"
--|  pour obtenir un fichier en langage d'assemblage de la machine abstraite.

with Types_Base;
use  Types_Base;

package Pseudo_Code is

   -- Un deplacement est un entier utilise dans les adressages indirects.
   subtype Deplacement is Entier;

   -- Les registres denotables.
   type Registre is (GB, LB, R0, R1, R2, R3, R4, R5, R6, R7, R8, R9,
                             R10, R11, R12, R13, R14, R15);

   -- Seuls les registres banalises sont des operandes d'instructions
   subtype Banalise is Registre range R0 .. R15;

   -- ***** TYPE Etiq *****

   type St_Etiq (<>) is limited private;
   type Etiq is access all St_Etiq;

   -- ***** TYPE Operande *****

   type Nature_Operande is
      (Op_Direct, Op_Indirect, Op_Indexe,
       Op_Entier, Op_Reel, Op_Chaine, Op_Etiq);

   type St_Operande (<>) is limited private;
   type Operande is access all St_Operande;

   -- ***** TYPE Inst *****

   type Code_Operation is
      (-- 0 operande
       Code_RTS, Code_RINT, Code_RFLOAT, Code_WINT,
       Code_WFLOAT, Code_WNL, Code_HALT,
       -- 1 operande de type Etiq
       Code_BSR, Code_BRA, Code_BEQ, Code_BNE, Code_BGT, Code_BLT,
       Code_BGE, Code_BLE, Code_BOV,
       -- 1 operande
       Code_SEQ, Code_SNE, Code_SGT, Code_SLT, Code_SGE, Code_SLE, Code_SOV,
       Code_ADDSP, Code_SUBSP, Code_PEA, Code_PUSH, Code_POP,
       Code_TSTO, Code_WSTR,
       -- 2 operandes
       Code_LOAD, Code_STORE, Code_LEA,
       Code_ADD, Code_SUB, Code_MUL, Code_OPP, Code_DIV, Code_MOD,
       Code_CMP, Code_INT, Code_FLOAT);

   type St_Inst (<>) is limited private;
   type Inst is access all St_Inst;

   -- TYPE Ligne

   type St_Ligne (<>) is limited private;
   type Ligne is access all St_Ligne;

   -- ***** OPERATIONS SUR LE TYPE Etiq *****

   function L_Etiq (S : String) return Etiq;
   -- Retourne l'etiquette de string S
   -- (en la creant si cela n'est pas deja fait).
   -- Deux appels avec la meme string retournent la meme etiquette.

   function L_Etiq_Num (S : String; N : Natural) return Etiq;
   -- Retourne l'etiquette dont la string est la concatenation de S, de '.' et
   -- des caracteres de la representation decimale de N.
   -- Deux appels avec les memes S et N retournent la meme etiquette.

   function Acces_String (E : access St_Etiq) return String;

   function Acces_Ligne (E : access St_Etiq) return Ligne;
   -- retourne la ligne associee a l'etiquette E, si E est definie,
   -- retourne null si E n'est pas definie.

   procedure Afficher (E : access St_Etiq);

   -- ***** OPERATIONS SUR LE TYPE Operande *****
   -- Les selecteurs et mutateurs ont des preconditions evidentes
   -- sur la nature de l'operande

   -- Chaque operande de nature registre direct existe en un seul exemplaire,
   -- accessibles par :
   function Le_Registre (R : Banalise) return Operande;

   function Creation_Op_Indirect (D : Deplacement;
                                  Base : Registre) return Operande;

   function Creation_Op_Indexe (D : Deplacement;
                                Base : Registre;
                                Index : Banalise) return Operande;

   function Creation_Op_Entier (V : Entier) return Operande;

   function Creation_Op_Reel (V : Reel) return Operande;

   function Creation_Op_Chaine (V : Chaine := null) return Operande;

   function Creation_Op_Etiq (E : Etiq := null) return Operande;

   function Acces_Nature (Op : access St_Operande) return Nature_Operande;

   function Acces_Registre (Op : access St_Operande) return Banalise;
   -- Pour les operandes de nature registre direct

   function Acces_Deplacement (Op : access St_Operande) return Deplacement;

   function Acces_Base (Op : access St_Operande) return Registre;
   -- Pour les operandes de nature indirect ou indexe

   function Acces_Index (Op : access St_Operande) return Banalise;

   function Acces_Entier (Op : access St_Operande) return Entier;

   function Acces_Reel (Op : access St_Operande) return Reel;

   function Acces_Chaine (Op : access St_Operande) return Chaine;

   function Acces_Etiq (Op : access St_Operande) return Etiq;

   procedure Changer_Deplacement (Op : access St_Operande;
                                  Nouveau_Dep : in Deplacement);

   procedure Changer_Base (Op : access St_Operande;
                           Nouvelle_Base : in Registre);

   procedure Changer_Index (Op : access St_Operande;
                            Nouvel_Index : in Banalise);

   procedure Changer_Entier (Op : access St_Operande;
                             Nouvel_Entier : in Entier);

   procedure Changer_Reel (Op : access St_Operande;
                           Nouveau_Reel : in Reel);

   procedure Changer_Chaine (Op : access St_Operande;
                             Nouvelle_Chaine : in Chaine);

   procedure Changer_Etiq (Op : access St_Operande;
                           Nouvelle_Etiq : in Etiq);

   procedure Afficher (Op : access St_Operande);


   -- ***** OPERATIONS SUR LE TYPE Inst *****
   -- Preconditions evidentes pour les selecteurs et mutateurs

   function Creation_Inst0 (Code : Code_Operation) return Inst;

   function Creation_Inst1 (Code : Code_Operation;
                            Op1 : Operande := null) return Inst;

   function Creation_Inst2 (Code : Code_Operation;
                            Op1, Op2 : Operande := null) return Inst;

   function Acces_Code_Operation (I : access St_Inst) return Code_Operation;

   function Acces_Op1 (I : access St_Inst) return Operande;

   function Acces_Op2 (I : access St_Inst) return Operande;

   procedure Changer_Op1 (I : access St_Inst;
                          Nouvel_Op : in Operande);

   procedure Changer_Op2 (I : access St_Inst;
                          Nouvel_Op : in Operande);

   procedure Afficher (I : access St_Inst);


   -- ***** OPERATIONS SUR LE TYPE Ligne *****

   function Creation (E : Etiq := null;
                      I : Inst := null;
                      Com : String := "";
                      Suiv : Ligne := null;
                      Num_Ligne : positive := 1) return Ligne;
   -- leve l'exception Erreur_Double_Def si E (si presente) est deja definie.

   function Acces_Etiq (L : access St_Ligne) return Etiq;

   function Acces_Inst (L : access St_Ligne) return Inst;

   function Acces_Comment (L : access St_Ligne) return String;

   function Acces_Suivant (L : access St_Ligne) return Ligne;

   function Acces_Num_Ligne (L : access St_Ligne) return Positive;

   procedure Changer_Etiq (L : access St_Ligne;
                           Nouvelle_Etiq : in Etiq);

   procedure Changer_Inst (L : access St_Ligne;
                           Nouvelle_Inst : in Inst);

   procedure Changer_Comment (L : access St_Ligne;
                              Nouveau_Com : in String);

   procedure Changer_Suivant (L : access St_Ligne;
                              Nouveau_Suiv : in Ligne);

   procedure Changer_Num_Ligne (L : access St_Ligne;
                                Nouveau_Num : in Positive);

   procedure Afficher (L : access St_Ligne);
   -- affiche la ligne L (sans afficher les lignes suivantes)


   -- ***** AFFICHAGE D'UN Programme *****

   procedure Afficher_Programme (L : access St_Ligne);
   -- affiche la ligne L ainsi que les lignes suivantes


   -- ***** EXCEPTIONS *****

   -- exceptions levees en cas d'erreur d'utilisation des types abstraits
   Erreur_Etiq, Erreur_Operande, Erreur_Inst, Erreur_Ligne : exception;

   -- exception levee si deux lignes definissent la meme etiquette
   Erreur_Double_Def : exception;

   -- exception levee si l'on tente de creer une etiquette correspondant
   -- a un nom reserve de la machine abstraite
   Erreur_Etiq_Illegale : exception;

private

   type St_Etiq is
      record
         Reserve : Boolean;
         Nom : Chaine;
         Def : Ligne;
      end record;

   type St_Operande (Nature : Nature_Operande) is
      record
         case Nature is
            when Op_Direct =>
               Reg : Banalise;
            when Op_Indirect | Op_Indexe =>
               Dep : Deplacement;
               Base : Registre;
               case Nature is
                  when Op_Indexe =>
                     Index : Banalise;
                  when Op_Indirect =>
                     null;
                  when others =>  --  Impossible mais obligatoire
                     null;
               end case;
            when Op_Entier =>
               Val_Ent : Entier;
            when Op_Reel =>
               Val_Reel : Reel;
            when Op_Chaine =>
               Val_Chaine : Chaine;
            when Op_Etiq =>
               Val_Etiq : Etiq;
         end case;
      end record;

   type St_Inst (Codop : Code_Operation) is
      record
         case Codop is
            when Code_RTS .. Code_HALT =>  --  0 operande
               null;
            when Code_BSR .. Code_FLOAT =>  --  au moins un operande
               Op1 : Operande;
               case Codop is
                  when Code_BSR .. Code_WSTR =>  --  un operande
                     null;
                  when Code_LOAD .. Code_FLOAT =>  --  deux operandes
                     Op2 : Operande;
                  when others =>  --  impossible, mais oligatoire
                     null;
               end case;
         end case;
      end record;

   type Ptr_Comment is access String;

   type St_Ligne is
      record
         Def_Etiq : Etiq := null;
         Val_Inst : Inst := null;
         Comment : Ptr_Comment := null;
         Suiv : Ligne := null;
         Num_ligne : Positive := 1;
      end record;

end Pseudo_Code;
