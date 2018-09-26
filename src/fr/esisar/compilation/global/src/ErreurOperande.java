package fr.esisar.compilation.global.src;

/**
* Exception levée lorsqu'un opérande n'est pas correct.
*/

class ErreurOperande extends RuntimeException { 
  public ErreurOperande(String message) {
     super(message);
  }
}
