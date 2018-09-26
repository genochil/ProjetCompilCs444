package fr.esisar.compilation.global.src;

/**
 * Classe pour représenter les arbres abstraits du programme.
 */

public class Arbre {

   private Noeud noeudArbre; // Le noeud de l'arbre
   private int numLigne;     // Le numéro de ligne
   private Decor decor;      // Le décor de l'arbre

   // ------------------------------------------------------------------------
   // CONSTRUCTEURS
   // ------------------------------------------------------------------------

   /**
    * Constructeur d'arbre de noeud Noeud.Entier.
    */

   public static Arbre creationEntier(int valEntier, int numLigne) {
      return new ArbreEntier(valEntier, numLigne);
   }

   /**
    * Constructeur d'arbre de noeud Noeud.Reel.
    */

   public static Arbre creationReel(float valReel, int numLigne) {
      return new ArbreReel(valReel, numLigne);
   }

   /**
    * Constructeur d'arbre de noeud Noeud.Chaine.
    */
   
   public static Arbre creationChaine(String valChaine, int numLigne) {
      return new ArbreChaine(Noeud.Chaine, valChaine, numLigne);
   }

   /**
    * Constructeur d'arbre de noeud Noeud.Ident.
    */
 
   public static Arbre creationIdent(String valIdent, int numLigne) {
      return new ArbreChaine(Noeud.Ident, valIdent, numLigne);
   }

   /**
    * Constructeur de noeud et d'arité spécifiés.
    */
   Arbre(Noeud noeud, int numLigne, int arite) {
      if (noeud.arite != arite) {
         throw new ErreurArbre("Noeud d'arite " + arite + " attendu");
      }
      this.noeudArbre = noeud;
      this.numLigne = numLigne;
   }
      
   /**
    * Constructeur d'arbre d'arité 0.
    * Précondition : noeud.arite == 0.
    */
   Arbre(Noeud noeud, int numLigne) {
      if (noeud.arite != 0) {
         throw new ErreurArbre("Noeud d'arite 0 attendu");
      }
      this.noeudArbre = noeud;
      this.numLigne = numLigne;
   } 

   /**
    * Constructeur d'arbre d'arité 0.
    * Précondition : noeud.arite == 0.
    */
   public static Arbre creation0(Noeud noeud, int numLigne) {
      return new Arbre(noeud, numLigne);
   }

   /**
    * Constructeur d'arbre d'arité 1.
    * Précondition : noeud.arite == 1.
    */
   public static Arbre creation1(Noeud noeud, Arbre fils1, int numLigne) {
      return new ArbreFils(noeud, fils1, numLigne);
   }

   /**
    * Constructeur d'arbre d'arité 2.
    * Précondition : noeud.arite == 2.
    */
   public static Arbre creation2(Noeud noeud, 
         Arbre fils1, Arbre fils2, int numLigne) {
      return new ArbreFils(noeud, fils1, fils2, numLigne);
   }

   /**
    * Constructeur d'arbre d'arité 3.
    * Précondition : noeud.arite == 3.
    */
   public static Arbre creation3(Noeud noeud, 
         Arbre fils1, Arbre fils2, Arbre fils3, int numLigne) {
      return new ArbreFils(noeud, fils1, fils2, fils3, numLigne);
   }

   // ------------------------------------------------------------------------
   // Sélecteurs
   // ------------------------------------------------------------------------

   /**
    * Le noeud de l'arbre.
    */
   public Noeud getNoeud() {
      return noeudArbre;
   }

   /**
    * Le numéro de ligne de l'arbre.
    */
   public int getNumLigne() {
      return numLigne;
   }

   /**
    * L'arité de l'arbre.
    */
   public int getArite() {
      return noeudArbre.arite;
   }

   /**
    * La chaîne associée à l'arbre.
    * Précondition : 
    *    this.getNoeud() == Noeud.Chaine ou this.getNoeud() == Noeud.Ident
    */
   public String getChaine() {
      throw new ErreurArbre("getChaine() impossible sur " + noeudArbre);
   }

   /**
    * L'entier associé à un Noeud.Entier.
    * Précondition : 
    *    this.getNoeud() == Noeud.Entier
    */
   public int getEntier() {
      throw new ErreurArbre("getEntier() impossible sur " + noeudArbre);
   }

   /**
    * Le réel associé à un Noeud.Reel.
    * Précondition :
    *    this.getNoeud() == Noeud.Reel
    */
   public float getReel() {
      throw new ErreurArbre("getReel() impossible sur " + noeudArbre);
   }

   /**
    * Le premier fils de cet arbre.
    * Précondition : l'arbre a au moins un fils.
    */
   public Arbre getFils1() {
      return getFils(1);
   }

   /**
    * Le deuxième fils de cet arbre.
    * Précondition : l'arbre a au moins deux fils.
    */
   public Arbre getFils2() {
      return getFils(2);
   }

   /**
    * Le troisième fils de cet arbre.
    * Précondition : l'arbre a au moins trois fils.
    */
   public Arbre getFils3() {
      return getFils(3);
   }

   /**
    * Le n-ième fils de cet arbre.
    * Précondition : le fils existe.
    */
   public Arbre getFils(int n) {
      if (n <= 0 || n > 3) {
         throw new ErreurArbre(
            "Accès au fils no " + n + " impossible");
      } else { 
         throw new ErreurArbre(
            "Accès au fils no " + n + " impossible sur " + noeudArbre +
            " (l'arbre n'a pas de fils)");
      }
   }

   /**
    * Le décor de cet arbre.
    */
   public Decor getDecor() {
      return decor;
   }

   // ------------------------------------------------------------------------
   // Mutateurs
   // ------------------------------------------------------------------------

   /**
    * Modifie le numéro de ligne associé à cet arbre.
    */
   public void setNumLigne(int numLigne) {
      this.numLigne = numLigne;
   }

   /**
    * Modifie la chaîne associée à cet arbre.
    * Précondition :
    *    this.getNoeud() == Noeud.Chaine ou this.getNoeud() == Noeud.Ident
    */
   public void setChaine(String valChaine) {
      throw new ErreurArbre("setChaine() impossible sur " + noeudArbre);
   }

   /**
    * Modifie l'entier associé à cet arbre.
    * Précondition :
    *    this.getNoeud() == Noeud.Entier
    */
   public void setEntier(int valEntier) {
      throw new ErreurArbre("setEntier() impossible sur " + noeudArbre);
   }

   /**
    * Modifie le réel associé à cet arbre.
    * Précondition :
    *    this.getNoeud() == Noeud.Reel
    */
   public void setReel(float valReel) {
      throw new ErreurArbre("setReel() impossible sur " + noeudArbre);
   }

   /**
    * Modifie le premier fils de cet arbre.
    * Précondition : l'arbre a au moins un fils.
    */
   public void setFils1(Arbre fils1) {
      setFils(1, fils1);
   }

   /**
    * Modifie le deuxième fils de cet arbre.
    * Précondition : l'arbre a au moins deux fils.
    */
   public void setFils2(Arbre fils2) {
      setFils(2, fils2);
   }

   /**
    * Modifie le troisième fils de cet arbre.
    * Précondition : l'arbre a au moins trois fils.
    */
   public void setFils3(Arbre fils3) {
      setFils(3, fils3);
   }

   /**
    * Modifie le n-ième fils de cet arbre.
    * Précondition : le fils existe.
    */
   public void setFils(int n, Arbre fils) {
      if (n <= 0 || n > 3) {
         throw new ErreurArbre(
            "Modification du fils no " + n + " impossible");
      } else {
         throw new ErreurArbre(
            "Modification du fils no " + n + " impossible sur " + noeudArbre +
            " (l'arbre n'a pas de fils)");
      }
   }


   /**
    * Modifie le décor associé à cet arbre.
    */
   public void setDecor(Decor decor) {
      this.decor = decor;
   }

   // ------------------------------------------------------------------------
   // Affichages d'arbres
   // ------------------------------------------------------------------------

   /**
    * Affiche cet arbre, avec un niveau de détails spécifié.
    * <ul>
    *  <li> niveau = 0 : pas de détail (passe 1) </li>
    *  <li> niveau = 1 : décors relatifs à la passe 2 </li>
    *  <li> niveau = 2 : décors relatifs aux passes 2 et 3 </li>
    * </ul>
    */
   public void afficher(int niveau) {
      afficher1(0, niveau);
   }

   private void afficher1(int indent, int niveau) {
      final int colNumLignes = 60;
      Affichage.commencer();
      Affichage.ecrire(noeudArbre + " ");
      switch (noeudArbre) {
         case Ident:
         case Chaine:
            Affichage.ecrire("\"" + getChaine() + "\"");
            break;
         case Entier:
            Affichage.ecrire(Integer.toString(getEntier()));
            break;
         case Reel:
            Affichage.ecrire(Float.toString(getReel()));
      }
      if (Affichage.getCol() < colNumLignes) {
         Affichage.setCol(colNumLignes);
      }
      Affichage.ecrire("  -- ligne : " + getNumLigne() + "\n");
      Affichage.empiler(0, "-- ");
      if (decor != null) {
         decor.afficher(niveau);
      }
      Affichage.depiler();
      if (getArite() != 0) {
         Affichage.empiler(0, ". ");
         for (int i = 1; i <= getArite(); i++) {
            getFils(i).afficher1(indent + 1, niveau);
         }
         Affichage.depiler();
      }
   }
      
   // ------------------------------------------------------------------------
   // Décompilation de cet arbre
   // ------------------------------------------------------------------------

   /**
    * Augmenter l'indentation de 3 espaces.
    */
   private static void augmenterIndentation() {
      Affichage.empiler(3, "");
   }

   /**
    * Diminuer l'indentation de 3 espaces.
    */

   private static void diminuerIndentation() {
      Affichage.depiler();
   }

   /**
    * Méthode appelée si l'arbre ou le programme est incorrect
    * Affiche un message d'erreur et lève l'exception ErreurArbre.
    */
   
   private void arbreOuProgrammeIncorrect(String s) {
      System.out.println(s);
      System.out.println("Noeud trouve : " + getNoeud() + 
                         " -- ligne " + getNumLigne());
      while (!Affichage.pileVide()) {
         Affichage.depiler();
      }
      throw new ErreurArbre(s);
   }

   private void arbreIncorrect(String s) {
      System.out.println();
      System.out.print("Arbre incorrect : ");
      arbreOuProgrammeIncorrect(s);
   }

   private void programmeIncorrect(String s) {
      System.out.println();
      System.out.print("Programme incorrect : ");
      arbreOuProgrammeIncorrect(s);
   }

   private void testerNoeudArbre(Noeud n) {
      if (getNoeud() != n) {
         arbreIncorrect(n.toString() + " attendu");
      }
   }

   /**
    * Indente, puis affiche la chaîne s.
    */
   private void indenter(String s) {
      Affichage.commencer();
      Affichage.ecrire(s);
   }

   /**
    * Indente, affiche la chaîne s puis passe à la ligne.
    */
   private void indenterLigne(String s) {
      Affichage.commencer();
      Affichage.ecrire(s + "\n");
   }

   /**
    * Affiche la String S sous forme JCas.
    */
   private static void decompilerString(String s) {
      Affichage.ecrire('"');
      for (int i = 0; i < s.length(); i++) {
         char c = s.charAt(i);
         if (c == '"') {
            Affichage.ecrire('"');
         }
         Affichage.ecrire(c);
      }
      Affichage.ecrire('"');
   }

   private void afficherNoeud(int niveau) {
      switch (getNoeud()) {
         case Affect:
            Affichage.ecrire(" := ") ;
            break;
         case Chaine:
            decompilerString(getChaine());
            break;
         case Conversion:
            if (niveau != 0) {
               Affichage.ecrire("-- Noeud.Conversion ");
            }
            break;
         case DivReel:
            Affichage.ecrire(" / ");
            break;
         case Egal:
            Affichage.ecrire(" = ");
            break;
         case Entier:
            Affichage.ecrire(Integer.toString(getEntier()));
            break;
         case Et:
            Affichage.ecrire(" and ");
            break;
         case Ident:
            Affichage.ecrire(getChaine());
            break;
         case Index:
            break;
         case Inf:
            Affichage.ecrire(" < ");
            break;
         case InfEgal:
            Affichage.ecrire(" <= ");
            break;
         case Moins:
            Affichage.ecrire(" - ");
            break;
         case MoinsUnaire:
            Affichage.ecrire("-");
            break;
         case Mult:
            Affichage.ecrire(" * ");
            break;
         case Non:
            Affichage.ecrire("not ");
            break;
         case NonEgal:
            Affichage.ecrire(" /= ");
            break;
         case Ou:
            Affichage.ecrire(" or ");
            break;
         case Plus:
            Affichage.ecrire(" + ");
            break;
         case PlusUnaire:
            Affichage.ecrire("+");
            break;
         case Quotient:
            Affichage.ecrire(" div ");
            break;
         case Reel:
            Affichage.ecrire(Float.toString(getReel()));
            break;
         case Reste:
            Affichage.ecrire(" mod ");
            break;
         case Sup:
            Affichage.ecrire(" > ");
            break;
         case SupEgal:
            Affichage.ecrire(" >= ");
            break;
         default:
            arbreIncorrect("Noeud incorrect");
      }
   }

   private void afficherNoeudPuisDecor(int niveau) {
      Affichage.empiler(
         Affichage.getCol() - Affichage.longueur() + getNoeud().nbEspaces - 1, 
         "-- ");
      afficherNoeud(niveau);
      if (decor != null) {
         decor.afficher(niveau);
      }
      Affichage.depiler();
/*
      Affichage.empiler(0, "-- ");
      afficherNoeud(niveau);
      if (decor != null) {
         decor.afficher(niveau);
      }
      Affichage.depiler();
*/
   }

   // ------------------------------------------------------------------------
   // Méthodes principales de décompilation, en fonction des non terminaux
   // de la grammaire d'arbre.
   // ------------------------------------------------------------------------

   /**
    * Décompiler les arbres qui dérivent de PROGRAMME.
    */
   private void decompiler_PROGRAMME(int niveau) {
      testerNoeudArbre(Noeud.Programme);
      indenterLigne("program");
      getFils1().decompiler_LISTE_DECL(niveau);
      indenterLigne("begin");
      getFils2().decompiler_LISTE_INST(niveau);
      indenterLigne("end.");
   }

   /**
    * Décompiler les arbres qui dérivent de LISTE_DECL.
    */
   private void decompiler_LISTE_DECL(int niveau) {
      augmenterIndentation();
      decompiler_LISTE_DECL_Aux(niveau);
      diminuerIndentation();
   }

   private void decompiler_LISTE_DECL_Aux(int niveau) {
      switch (getNoeud()) {
         case Vide:
            break;
         case ListeDecl:
            getFils1().decompiler_LISTE_DECL_Aux(niveau);
            getFils2().decompiler_DECL(niveau);
            break;
         default:
            arbreIncorrect("Noeud.Vide ou Noeud.ListeDecl attendu");
      }
   }

   /**
    * Décompiler les arbres qui dérivent de DECL.
    */
   private void decompiler_DECL(int niveau) {
      testerNoeudArbre(Noeud.Decl);
      Affichage.commencer();
      Arbre listeIdent = getFils1();
      Arbre typ = getFils2();
      listeIdent.testerNoeudArbre(Noeud.ListeIdent);
      listeIdent.decompiler_LISTE_IDENT(niveau);
      Affichage.ecrire(" : ");
      typ.decompiler_TYPE(niveau);
      Affichage.ecrire("; \n");
   }

   /**
    * Décompiler les arbres qui dérivent de LISTE_IDENT.
    */
   private void decompiler_LISTE_IDENT(int niveau) {
      switch (getNoeud()) {
         case Vide:
            break;
         case ListeIdent:
            decompiler_LISTE_IDENT_NonVide(niveau);
            break;
         default:
            arbreIncorrect("Noeud.Vide ou Noeud.ListeIdent attendu");
      }
   }

   private void decompiler_LISTE_IDENT_NonVide(int niveau) {
      testerNoeudArbre(Noeud.ListeIdent);
      Arbre lIdent = getFils1();
      Arbre ident = getFils2(); 
      switch (lIdent.getNoeud()) {
         case Vide:
            break;
         case ListeIdent:
            lIdent.decompiler_LISTE_IDENT_NonVide(niveau);
            Affichage.ecrire(", ");
            break;
         default:
            arbreIncorrect("Noeud.Vide ou Noeud.ListeIdent attendu");
      }
      ident.decompiler_IDENT(niveau);
   }

   /**
    * Décompiler les arbres qui dérivent de TYPE.
    */
   private void decompiler_TYPE(int niveau) {
      switch (getNoeud()) {
         case Intervalle:
            getFils1().decompiler_EXP_CONST(niveau);
            Affichage.ecrire(" .. ");
            getFils2().decompiler_EXP_CONST(niveau);
            break;
         case Tableau:
            Affichage.ecrire("array [");
            getFils1().decompiler_TYPE(niveau);
            Affichage.ecrire("] of ");
            getFils2().decompiler_TYPE(niveau);
            break;
         case Ident:
            decompiler_IDENT(niveau);
            break;
         default:
            arbreIncorrect(
               "Noeud.Intervalle, Noeud.Tableau ou Noeud.Ident attendu");
      }
   }

   /**
    * Décompiler les arbres qui dérivent de EXP_CONST.
    */
   private void decompiler_EXP_CONST(int niveau) {
      switch (getNoeud()) {
         case Entier:
            afficherNoeudPuisDecor(niveau);
            break;
         case Ident:
            decompiler_IDENT(niveau);
            break;
         case PlusUnaire:
         case MoinsUnaire:
            afficherNoeudPuisDecor(niveau);
            getFils1().decompiler_EXP_CONST(niveau);
            break;
         default:
            arbreIncorrect(
               "Noeud.Entier, Noeud.Ident, Noeud.PlusUnaire ou " +
               "Noeud.MoinsUnaire attendu");
      }
   }

   /**
    * Décompiler les arbres qui dérivent de LISTE_INST.
    */
   private void decompiler_LISTE_INST(int niveau) {
      switch (getNoeud()) {
         case Vide:
            programmeIncorrect("Noeud.ListeInst attendu");
            break;
         case ListeInst:
            augmenterIndentation();
            decompiler_LISTE_INST_2(niveau);
            diminuerIndentation();
            break;
         default:
            arbreIncorrect("Noeud.ListeInst attendu");
      }
   }

   private void decompiler_LISTE_INST_2(int niveau) {
      switch (getNoeud()) {
         case Vide:
            break;
         case ListeInst:
            getFils1().decompiler_LISTE_INST_2(niveau);
            getFils2().decompiler_INST(niveau);
            break;
         default:
            arbreIncorrect("Noeud.Vide ou Noeud.ListeInst attendu");
      }
   }

   /**
    * Décompiler les arbres qui dérivent de INST.
    */
   private void decompiler_INST(int niveau) {
      Affichage.commencer();
      switch (getNoeud()) {
         case Nop:
            Affichage.ecrire("null");
            break;
         case Affect:
            decompiler_INST_Affect(niveau);
            break;
         case Si:
            decompiler_INST_Si(niveau);
            break;
         case TantQue:
            decompiler_INST_TantQue(niveau);
            break;
         case Pour:
            decompiler_INST_Pour(niveau);
            break;
         case Lecture:
            decompiler_INST_Lecture(niveau);
            break;
         case Ecriture:
            decompiler_INST_Ecriture(niveau);
            break;
         case Ligne:
            Affichage.ecrire("new_line");
            break;
         default:
            arbreIncorrect(
               "Noeud.Nop, Noeud.Affect, Noeud.Si, Noeud.TantQue, " +
               "Noeud.Pour, Noeud.Lecture, Noeud.Ecriture ou Noeud.Ligne " +
               "attendu");
      }
      Affichage.ecrire(";\n");
   }

   private void decompiler_INST_Affect(int niveau) {
      getFils1().decompiler_PLACE(niveau);
      afficherNoeudPuisDecor(niveau);
      getFils2().decompiler_EXP(niveau);
   }

   private void decompiler_INST_Si(int niveau) {
      Affichage.ecrire("if ");
      getFils1().decompiler_EXP(niveau);
      Affichage.ecrire(" then \n");
      getFils2().decompiler_LISTE_INST(niveau);
      Arbre sinon = getFils3();
      if (sinon.getNoeud() != Noeud.Vide) {
         // Il y a une partie else
         indenterLigne("else");
         sinon.decompiler_LISTE_INST(niveau);
      }
      indenter("end");
   }

   private void decompiler_INST_TantQue(int niveau) {
      Affichage.ecrire("while ");
      getFils1().decompiler_EXP(niveau);
      Affichage.ecrire(" do\n");
      getFils2().decompiler_LISTE_INST(niveau);
      Affichage.commencer();
      Affichage.ecrire("end");
   }

   private void decompiler_INST_Pour(int niveau) {
      Arbre pas = getFils1();
      Arbre insts = getFils2();
      Affichage.ecrire("for ");
      pas.getFils1().decompiler_IDENT(niveau);
      Affichage.ecrire(" := ");
      pas.getFils2().decompiler_EXP(niveau);
      switch (pas.getNoeud()) {
         case Increment:
            Affichage.ecrire(" to ");
            break;
         case Decrement:
            Affichage.ecrire(" downto ");
            break;
         default:
            pas.arbreIncorrect("Noeud.Increment ou Noeud.Decrement attendu");
      }
      pas.getFils3().decompiler_EXP(niveau);
      Affichage.ecrire(" do \n");
      insts.decompiler_LISTE_INST(niveau);
      indenter("end");
   }

   private void decompiler_INST_Lecture(int niveau) {
      Affichage.ecrire("read(");
      getFils1().decompiler_PLACE(niveau);
      Affichage.ecrire(")");
   }

   private void decompiler_INST_Ecriture(int niveau) {
      Affichage.ecrire("write");
      Arbre listeExp = getFils1();
      switch (listeExp.getNoeud()) {
         case Vide:
            listeExp.programmeIncorrect("Noeud.Liste attendu");
            break;
         case ListeExp:
            Affichage.ecrire("(");
            listeExp.decompiler_LISTE_EXP(niveau);
            Affichage.ecrire(")");
            break;
         default:
            listeExp.arbreIncorrect("Noeud.Liste attendu");
      }
   }

   /**
    * Décompiler un arbre qui dérive de IDENT.
    */
   private void decompiler_IDENT(int niveau) {
      testerNoeudArbre(Noeud.Ident);
      afficherNoeudPuisDecor(niveau);
   }

   /**
    * Décompiler un arbre qui dérive de PLACE.
    */
   private void decompiler_PLACE(int niveau) {
      switch (getNoeud()) {
         case Index:
            getFils1().decompiler_PLACE(niveau);
            Affichage.ecrire("[");
            getFils2().decompiler_EXP(niveau);
            Affichage.ecrire("]");
            afficherNoeudPuisDecor(niveau);
            break;
         case Ident:
            decompiler_IDENT(niveau);
            break;
         default:
            arbreIncorrect("Noeud.Index ou Noeud.Ident attendu");
      }
   }

   /**
    * Décompiler un arbre non vide qui dérive de LISTE_EXP.
    */
   private void decompiler_LISTE_EXP(int niveau) {
      testerNoeudArbre(Noeud.ListeExp);
      Arbre listeExp = getFils1();
      Arbre exp = getFils2();
      switch (listeExp.getNoeud()) {
         case Vide:
            exp.decompiler_EXP(niveau);
            break;
         case ListeExp:
            listeExp.decompiler_LISTE_EXP(niveau);
            Affichage.ecrire(", ");
            exp.decompiler_EXP(niveau);
            break;
         default:
            listeExp.arbreIncorrect("Noeud.Vide ou Noeud.ListeExp attendu");
      }
   }
        
   /**
    * Décompiler un arbre qui dérive de EXP.
    */
   private void decompiler_EXP(int niveau) {
      decompiler_EXP_NoeudSup(Noeud.Vide, true, niveau);
   }

   /**
    * Décompiler un arbre qui dérive de EXP. N_Sup est le noeud supérieur, 
    * Branche_Gauche = true ssi on est dans une branche gauche pour 
    * les noeud binaires.
    */
   private void decompiler_EXP_NoeudSup
         (Noeud noeudSup, boolean brancheGauche, int niveau) {
      boolean par;
      Noeud noeudInf = getNoeud(); // Le noeud courant
      switch (noeudInf) {
         case Ident:
            decompiler_IDENT(niveau);
            break;
         case Index:
            decompiler_PLACE(niveau);
            break;
         case Entier:
         case Reel:
         case Chaine:
            afficherNoeudPuisDecor(niveau);
            break;
         case Conversion:
            // noeud "transparent"
            afficherNoeudPuisDecor(niveau);
            getFils1().decompiler_EXP_NoeudSup(noeudSup, brancheGauche, niveau);
            break;
         case PlusUnaire:
         case MoinsUnaire:
         case Non:
            // noeud unaire
            par = parenthese(noeudSup, noeudInf, brancheGauche);
            if (par) {
               Affichage.ecrire("(");
            }
            afficherNoeudPuisDecor(niveau);
            getFils1().decompiler_EXP_NoeudSup(noeudInf, true, niveau);
            if (par) {
               Affichage.ecrire(")");
            }
            break;
         case Et:
         case Ou:
         case Egal:
         case InfEgal:
         case SupEgal:
         case NonEgal:
         case Inf:
         case Sup:
         case Plus:
         case Moins:
         case Mult:
         case DivReel:
         case Quotient:
         case Reste:
            // noeud binaire
            par = parenthese(noeudSup, noeudInf, brancheGauche);
            if (par) {
               Affichage.ecrire("(");
            }
            getFils1().decompiler_EXP_NoeudSup(noeudInf, true, niveau);
            afficherNoeudPuisDecor(niveau);
            getFils2().decompiler_EXP_NoeudSup(noeudInf, false, niveau);
            if (par) {
               Affichage.ecrire(")");
            }
            break;
         default:
            arbreIncorrect("Noeud derivant de EXP attendu");
      }
   }

   /**
    * Retourne true s'il faut parentheser.
    * Il faut parenthéser si la priorité de l'opérateur supérieur
    * est strictement supérieure à la priorité de l'opérateur inférieur
    * Si les priorités de l'opérateur supérieur et de 
    * l'opérateur inférieur sont égales, il faut parentheser si
    * <ul> 
    *    <li> on est dans une branche gauche et l'opérateur supérieur 
    *         n'est pas associatif à gauche ;  </li>
    *    <li> on est dans une branche droite et l'opérateur supérieur
    *         n'est pas associatif à droite.   </li>
    * </ul>
    */

   private static boolean parenthese(
         Noeud noeudSup, Noeud noeudInf, boolean brancheGauche) {
      int prioNoeudSup = priorite(noeudSup);
      int prioNoeudInf = priorite(noeudInf);
      if (brancheGauche) {
         return (prioNoeudSup > prioNoeudInf) ||
            ((prioNoeudSup == prioNoeudInf) && (!associatifGauche(noeudSup)));
      } else {
         return (prioNoeudSup > prioNoeudInf) ||
            ((prioNoeudSup == prioNoeudInf) && (!associatifDroite(noeudSup)));
      }
   }

   /**
    * priorite(noeud) est un entier indiquant la priorité de 
    * l'opérateur correspondant au noeud.
    * Plus priorite(noeud) est grand, plus l'opérateur est prioritaire.
    * Le Noeud.Vide signifie qu'il s'agit de la racine.
    * On lui met la priorité minimum, pour que les expressions 
    * ne soient pas parenthésées au plus au niveau.
    */
   private static int priorite(Noeud noeud) {
      switch(noeud) {
         case Non:
         case PlusUnaire:
         case MoinsUnaire:
            return 4;
         case DivReel:
         case Reste:
         case Quotient:
         case Mult:
         case Et:
            return 3;
         case Plus:
         case Moins:
         case Ou:
            return 2;
         case Egal:
         case InfEgal:
         case SupEgal:
         case NonEgal:
         case Inf:
         case Sup:
            return 1;
         case Vide:
            return 0;
         default:
            throw new ErreurArbre("Erreur interne dans priorite");
      }
   }

   /**
    * associatifGauche(noeud) = true ssi 
    * l'opérateur correspondant au noeud est associatif à gauche.
    */
   private static boolean associatifGauche(Noeud noeud) {
      switch (noeud) {
         case Plus:
         case Moins:
         case Ou:
         case Reste:
         case DivReel:
         case Quotient:
         case Mult:
         case Et:
            return true;
         default:
            return false;
      }
   }

   /**
    * associatifDroite(noeud) = true ssi 
    * l'opérateur correspondant au noeud est associatif à droite.
    */
   private static boolean associatifDroite(Noeud noeud) {
      switch(noeud) {
         case Plus:
         case Ou:
         case Mult:
         case Et:
            return true;
         default:
            return false;
      }

   }


   /**
    * Décompile cet arbre, avec un niveau de détails spécifié. 
    * <ul>
    *  <li> niveau = 0 : pas de détail (passe 1) </li>
    *  <li> niveau = 1 : décors relatifs à la passe 2 </li>
    *  <li> niveau = 2 : décors relatifs aux passes 2 et 3 </li>
    * </ul>
    */
   public void decompiler(int niveau) {
      decompiler_PROGRAMME(niveau);
   }
   
}


