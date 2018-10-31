package fr.esisar.compilation.global.src3;

/**
 * Classe des opérandes des instructions de la machine abstraite.
 */

public abstract class Operande {

   /** La nature de cet opérande. */
   private NatureOperande nature; // La nature de cet opérande

   /**
    * Tableau permettant de créer une fois pour toutes les opérandes
    * qui correspondent aux différents registres de la machine abstraite.
    */
   private static Operande[] tableauOpDirect = initialiserTableauOpDirect(); 

   /**
    * Initialisation de tableauOpDirect.
    */
   private static Operande[] initialiserTableauOpDirect() {
      Registre[] tabRegistre = Registre.values(); 
      int nbReg = tabRegistre.length; 
      Operande[] tabRes = new Operande[nbReg]; 
      for (int i = 0; i < nbReg; i++) {
         tabRes[i] = Operande.creationOpDirect(tabRegistre[i]); 
      }
      return tabRes; 
   }
         

   /**
    * Opérande correspondant au registre Registre.R0.
    */
   public final static Operande R0 = Operande.opDirect(Registre.R0); 

   /**
    * Opérande correspondant au registre Registre.R1.
    */
   public final static Operande R1 = Operande.opDirect(Registre.R1);

   /**
    * Opérande correspondant au registre Registre.R2.
    */
   public final static Operande R2 = Operande.opDirect(Registre.R2);

   /**
    * Opérande correspondant au registre Registre.R3.
    */
   public final static Operande R3 = Operande.opDirect(Registre.R3);

   /**
    * Opérande correspondant au registre Registre.R4.
    */
   public final static Operande R4 = Operande.opDirect(Registre.R4);

   /**
    * Opérande correspondant au registre Registre.R5.
    */
   public final static Operande R5 = Operande.opDirect(Registre.R5);

   /**
    * Opérande correspondant au registre Registre.R6.
    */
   public final static Operande R6 = Operande.opDirect(Registre.R6);

   /**
    * Opérande correspondant au registre Registre.R7.
    */
   public final static Operande R7 = Operande.opDirect(Registre.R7);

   /**
    * Opérande correspondant au registre Registre.R8.
    */
   public final static Operande R8 = Operande.opDirect(Registre.R8);

   /**
    * Opérande correspondant au registre Registre.R9.
    */
   public final static Operande R9 = Operande.opDirect(Registre.R9);

   /**
    * Opérande correspondant au registre Registre.R10.
    */
   public final static Operande R10 = Operande.opDirect(Registre.R10);

   /**
    * Opérande correspondant au registre Registre.R11.
    */
   public final static Operande R11 = Operande.opDirect(Registre.R11);

   /**
    * Opérande correspondant au registre Registre.R12.
    */
   public final static Operande R12 = Operande.opDirect(Registre.R12);

   /**
    * Opérande correspondant au registre Registre.R13.
    */
   public final static Operande R13 = Operande.opDirect(Registre.R13);

   /**
    * Opérande correspondant au registre Registre.R14.
    */
   public final static Operande R14 = Operande.opDirect(Registre.R14);

   /**
    * Opérande correspondant au registre Registre.R15.
    */
   public final static Operande R15 = Operande.opDirect(Registre.R15);

   /**
    * Opérande correspondant au registre Registre.GB.
    */
   public final static Operande GB = Operande.opDirect(Registre.GB);

   /**
    * Opérande correspondant au registre Registre.LB.
    */
   public final static Operande LB = Operande.opDirect(Registre.LB);

   // ------------------------------------------------------------------------
   // CONSTRUCTEURS
   // ------------------------------------------------------------------------

   /**
    * Constructeur d'opérande registre (de nature OpDirect). 
    * Permet l'adressage direct par registre.
    * Ce constructeur est privé car il est préférable d'utiliser la 
    * méthode opDirect qui ne re-crée
    * pas un nouvel opérande à chaque appel.
    */
   private static Operande creationOpDirect(Registre reg) {
      return new OperandeDirect(reg);
   }

   /**
    * L'opérande qui correspond au registre reg.
    * Cet opérande est crée une seule fois.
    */
   public static Operande opDirect(Registre reg) {
      return tableauOpDirect[reg.ordinal()]; 
   }

   /**
    * Constructeur d'opérande indirect (de nature OpIndirect).
    * Permet l'adressage indirect avec déplacement.
    */

   public static Operande creationOpIndirect(int deplacement, Registre reg) {
      return new OperandeIndirect(deplacement, reg);
   }

   /**
    * Constructeur d'opérande indexé (de nature OpIndexe). 
    * Permet l'adressage indirect indexé avec déplacement.
    */

   public static Operande creationOpIndexe
      (int deplacement, Registre regBase, Registre regIndex) {
      return new OperandeIndexe(deplacement, regBase, regIndex);
   }

   /**
    * Constructeur d'opérande entier (de nature OpEntier).
    * Permet l'adressage immédiat entier.
    */

   public static Operande creationOpEntier(int valEntier) {
      return new OperandeEntier(valEntier);
   }

   /**
    * Constructeur d'opérande réel (de nature OpReel).
    * Permet l'adressage immédiat réel.
    */

   public static Operande creationOpReel(float valReel) {
      return new OperandeReel(valReel);
   }

   /**
    * Constructeur d'opérande chaîne (de nature OpChaine). 
    */

   public static Operande creationOpChaine(String valChaine) {
      return new OperandeChaine(valChaine);
   }

   /**
    * Constructeur d'opérande étiquette (de nature OpEtiq). 
    */

   public static Operande creationOpEtiq(Etiq etiquette) {
      return new OperandeEtiq(etiquette);
   }

   /**
    * Constructeur d'opérande de nature spécifiée.
    */
   Operande(NatureOperande nature) {
      this.nature = nature;
   }

   // ------------------------------------------------------------------------
   // Sélecteurs
   // ------------------------------------------------------------------------

   /**
    * La nature de cet opérande.
    */
   public NatureOperande getNature() {
      return nature;
   }

   /**
    * Le registre qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpDirect
    */
   public Registre getRegistre() {
      throw new ErreurOperande(
         "getRegistre impossible sur opérande de nature " + nature);
   }

   /**
    * Le déplacement qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpIndirect ou
    *    this.getNature() == NatureOperande.OpIndexe
    */
   public int getDeplacement() {
      throw new ErreurOperande(
         "getDeplacement impossible sur opérande de nature " + nature);
   }

   /**
    * Le registre de base qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpIndirect ou
    *    this.getNature() == NatureOperande.OpIndexe
    */
   public Registre getRegistreBase() {
      throw new ErreurOperande(
         "getRegistreBase impossible sur opérande de nature " + nature);
   }

   /**
    * Le registre d'index qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpIndexe
    */
   public Registre getRegistreIndex() {
      throw new ErreurOperande(
         "getRegistreIndex impossible sur opérande de nature " + nature);
   }

   /**
    * L'entier qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpEntier
    */
   public int getEntier() {
      throw new ErreurOperande(
         "getEntier impossible sur opérande de nature " + nature);
   }

   /**
    * Le réel qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpReel
    */
   public float getReel() {
      throw new ErreurOperande(
         "getReel impossible sur opérande de nature " + nature);
   }

   /**
    * La chaîne qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpChaine
    */
   public String getChaine() {
      throw new ErreurOperande(
         "getChaine impossible sur opérande de nature " + nature);
   }

   /**
    * L'étiquette qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpEtiq
    */
   public Etiq getEtiq() {
      throw new ErreurOperande(
         "getEtiq impossible sur opérande de nature " + nature);
   }



   // ------------------------------------------------------------------------
   // Mutateurs
   // ------------------------------------------------------------------------
   //
   //   Ces mutateurs sont privés pour diminuer le risque de bugs difficiles 
   //   à trouver.
   //

   /** 
    * Modifie le registre qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpDirect
    */
   private void setRegistre() {
      throw new ErreurOperande(
         "setRegistre impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie le déplacement qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpIndirect ou
    *    this.getNature() == NatureOperande.OpIndexe
    */
   private void setDeplacement(int deplacement) {
      throw new ErreurOperande(
         "setDeplacement impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie le registre de base qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpIndirect ou
    *    this.getNature() == NatureOperande.OpIndexe
    */
   private void setRegistreBase(Registre regBase) {
      throw new ErreurOperande(
         "setRegistreBase impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie le registre d'index qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpIndexe
    */
   private void setRegistreIndex(Registre regIndex) {
      throw new ErreurOperande(
         "setRegistreIndex impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie l'entier qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature == NatureOperande.OpEntier
    */
   private void setEntier(int valEntier) {
      throw new ErreurOperande(
         "setEntier impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie le réel qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpReel
    */
   private void setReel(int valReel) {
      throw new ErreurOperande(
         "setReel impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie la chaîne qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpChaine
    */
   private void setChaine(String valChaine) {
      throw new ErreurOperande(
         "setChaine impossible sur opérande de nature " + nature);
   }

   /**
    * Modifie l'étiquette qui correspond à cet opérande.
    * Précondition : 
    *    this.getNature() == NatureOperande.OpEtiq
    */
   private void setEtiq(Etiq valEtiq) {
      throw new ErreurOperande(
         "setEtiq impossible sur opérande de nature " + nature);
   }

}


