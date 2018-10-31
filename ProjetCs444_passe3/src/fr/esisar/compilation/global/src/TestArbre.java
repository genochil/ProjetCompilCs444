package fr.esisar.compilation.global.src;

class TestArbre {
   static void ok(boolean b) {
      if (b) {
         System.out.print("ok ");
      } else {
         System.out.print("erreur ");
      }
   }

   public static void main(String[] args) {
      test1();
      test2();
      test3();
      test4();
      test5();
      test6();
   }

   static void test1() {
      System.out.println("-------------------------------------------------");
      System.out.println("Test 1");
      System.out.println("-------------------------------------------------");
      Noeud noeud = Noeud.Vide;
      Arbre a1 = Arbre.creation0(Noeud.Vide, 1);
      ok(a1.getNoeud() == Noeud.Vide);
      ok(a1.getNumLigne() == 1);
      System.out.println();
      a1.afficher(0);
      System.out.println();
      try {
         Arbre a2 = a1.getFils(0);
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
         System.out.println();
      }
      try {
         Arbre a2 = a1.getFils(1);
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
         System.out.println();
      }
      try {
         Arbre a2 = a1.getFils(2);
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
         System.out.println();
      }
      try {
         Arbre a2 = a1.getFils1();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
         System.out.println();
      }


      try {
         int e = a1.getEntier();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
         System.out.println();
      }


      System.out.println("\n");
      
   }
   static void test2() {
      System.out.println("-------------------------------------------------");
      System.out.println("Test 2");
      System.out.println("-------------------------------------------------");
      Arbre a1 = Arbre.creationEntier(-5, 10);
      ok(a1.getNoeud() == Noeud.Entier);
      ok(a1.getEntier() == -5);
      ok(a1.getNumLigne() == 10);
      System.out.println();
      a1.afficher(0);
      System.out.println();
      try {
         Arbre a2 = a1.getFils1();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println();
      try {
         float f = a1.getReel();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println("\n");
   }
   
   


   static void test3() {
      System.out.println("-------------------------------------------------");
      System.out.println("Test 3");
      System.out.println("-------------------------------------------------");
      Arbre a1 = Arbre.creationReel(-5.5f, 10);
      ok(a1.getNoeud() == Noeud.Reel);
      ok(a1.getReel() == -5.5f);
      ok(a1.getNumLigne() == 10);
      System.out.println();
      a1.afficher(0);
      System.out.println();
      try {
         Arbre a2 = a1.getFils1();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println();
      try {
         int e = a1.getEntier();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println("\n");
   }

   static void test4() {
      System.out.println("-------------------------------------------------");
      System.out.println("Test 4");
      System.out.println("-------------------------------------------------");
      Arbre a1 = Arbre.creationChaine("une chaine", 10);
      ok(a1.getNoeud() == Noeud.Chaine);
      ok(a1.getChaine() == "une chaine");
      ok(a1.getNumLigne() == 10);
      System.out.println();
      a1.afficher(0);
      System.out.println();
      try {
         Arbre a2 = a1.getFils1();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println();
      try {
         int e = a1.getEntier();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println("\n");
   }
   
   static void test5() {
      System.out.println("-------------------------------------------------");
      System.out.println("Test 5");
      System.out.println("-------------------------------------------------");
      Arbre a1 = Arbre.creationIdent("var_idf", 10);
      ok(a1.getNoeud() == Noeud.Ident);
      ok(a1.getChaine() == "var_idf");
      ok(a1.getNumLigne() == 10);
      System.out.println();
      a1.afficher(0);
      System.out.println();
      try {
         Arbre a2 = a1.getFils1();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println();
      try {
         int e = a1.getEntier();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println("\n");
   }
   
   static void test6() { 
      System.out.println("-------------------------------------------------");
      System.out.println("Test 6");
      System.out.println("-------------------------------------------------");
      Arbre a1 = Arbre.creationEntier(1, 1);
      Arbre a2 = Arbre.creationIdent("a", 1);
      Arbre a3 = Arbre.creation2(Noeud.Plus, a1, a2, 1);
      ok(a3.getNoeud() == Noeud.Plus);
      ok(a3.getNumLigne() == 1);
      ok(a3.getFils1() == a1);
      ok(a3.getFils2() == a2);
      ok(a3.getFils(1) == a1);
      ok(a3.getFils(2) == a2);
      System.out.println();
      a3.afficher(0);
      System.out.println();
      try {
         a3.getFils(0);
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println();
      try {
         a3.getFils(3);
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }
      System.out.println();
      try {
         a3.getFils3();
         ok(false);
      } catch (ErreurArbre e) {
         System.out.print(e + " ... ");
         ok(true);
      }

      System.out.println("\n");
   }
}


