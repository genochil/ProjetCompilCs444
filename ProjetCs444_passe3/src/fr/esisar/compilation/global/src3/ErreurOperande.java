package fr.esisar.compilation.global.src3;

/**
 * Exception levée lorsqu'une précondition concernant les opérandes n'est 
 * pas respectée.
 */

public class ErreurOperande extends RuntimeException {
   public ErreurOperande(String message) {
      super(message);
   }
}

