package fr.esisar.compilation.global.src3;

/**
 * Le type des registres de la machine abstraite.
 * Les registres R0 à R15 sont des registres banalisés.
 * Le registre GB est la base globale.
 * Le registre LB est la base locale (utile pour coder les fonctions ou 
 * procédures).
 */

public enum Registre {
   R0,
   R1,
   R2,
   R3,
   R4,
   R5,
   R6,
   R7,
   R8,
   R9,
   R10,
   R11,
   R12,
   R13,
   R14,
   R15,
   GB,
   LB;
}



