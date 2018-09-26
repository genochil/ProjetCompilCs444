package fr.esisar.compilation.global.src3;

/**
 * Le type des opérations de la machine abstraite.
 */

public enum Operation {

   // Opération d'arité 0 
   RTS(0), RNL(0), RINT(0), RFLOAT(0), WINT(0),
   WFLOAT(0), WNL(0), HALT(0),
   
   // Opération d'arité 1, avec un opérande de type Etiq
   BSR(1,true), BRA(1,true),
   BEQ(1,true), BNE(1,true),
   BGT(1,true), BLT(1,true),
   BGE(1,true), BLE(1,true),
   BOV(1,true),

   // Opération d'arité 1, avec opérande qui n'est pas de type Etiq
   SEQ(1), SNE(1), SGT(1), SLT(1), SGE(1), SLE(1), SOV(1),
   ADDSP(1), SUBSP(1), 
   PEA(1), PUSH(1), POP(1),
   TSTO(1),
   WSTR(1),

   // Opération d'arité 2
   LOAD(2), STORE(2), LEA(2),
   ADD(2), SUB(2), MUL(2), OPP(2), DIV(2), MOD(2),
   CMP(2), INT(2), FLOAT(2);


   private int arite; // L'arité de l'opération (le nombre d'opérande)
   private boolean etiq; 
      // Vrai ssi cette opération attend un opérande de type Etiq

   /**
    * Constructeur d'opération d'arité spécifiée.
    * Par défaut, etiq = false.
    */
   Operation (int arite) {
      this.arite = arite;
      etiq = false;
   }

   /**
    * Constructeur d'opération d'arité et de contrainte d'étiquette spécifiées.
    */
   Operation (int arite, boolean etiq) {
      this.arite = arite;
      this.etiq = etiq;
   }

   /**
    * L'arité de cette opération. L'arité correspond au nombre 
    * d'opérandes attendu par cette opération.
    */
   public int getArite() {
      return arite;
   }

   /**
    * getEtiq() est vrai ssi l'opération attend un opérande de type Etiq.
    */
   boolean getEtiq() {
      return etiq;
   }
}
