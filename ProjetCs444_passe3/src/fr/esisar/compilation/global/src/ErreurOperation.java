package fr.esisar.compilation.global.src;

/**
* Exception levée lorsqu'une opération est incorrecte.
*/


class ErreurOperation extends RuntimeException { 
  public ErreurOperation(String message) {
     super(message);
  }
}