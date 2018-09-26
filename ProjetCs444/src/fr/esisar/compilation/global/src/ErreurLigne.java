package fr.esisar.compilation.global.src;

/**
* Exception levée lorsqu'une ligne de code de machine abstraite n'est pas correcte.
*/

class ErreurLigne extends RuntimeException { 
  public ErreurLigne(String message) {
     super(message);
  }
}
