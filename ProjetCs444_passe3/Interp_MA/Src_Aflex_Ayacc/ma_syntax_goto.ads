package Ma_Syntax_Goto is

    type Small_Integer is range -32_000 .. 32_000;

    type Goto_Entry is record
        Nonterm  : Small_Integer;
        Newstate : Small_Integer;
    end record;

  --pragma suppress(index_check);

    subtype Row is Integer range -1 .. Integer'Last;

    type Goto_Parse_Table is array (Row range <>) of Goto_Entry;

    Goto_Matrix : constant Goto_Parse_Table :=
       ((-1,-1)  -- Dummy Entry.
-- State  0
,(-2, 1)
-- State  1
,(-4, 2),(-3, 4)
-- State  2
,(-16, 16)
,(-15, 15),(-14, 14),(-12, 13),(-10, 12)
,(-9, 11),(-6, 56),(-5, 55)
-- State  3

-- State  4

-- State  5
,(-17, 58)
,(-13, 57),(-8, 59),(-7, 65)
-- State  6

-- State  7
,(-8, 67)

-- State  8
,(-8, 68)
-- State  9

-- State  10

-- State  11
,(-17, 58),(-13, 57),(-8, 59)
,(-7, 71)
-- State  12
,(-17, 74),(-11, 75),(-8, 72)

-- State  13
,(-13, 76),(-8, 59)
-- State  14

-- State  15

-- State  16

-- State  17

-- State  18

-- State  19

-- State  20

-- State  21

-- State  22

-- State  23

-- State  24

-- State  25

-- State  26

-- State  27

-- State  28

-- State  29

-- State  30

-- State  31

-- State  32

-- State  33

-- State  34

-- State  35

-- State  36

-- State  37

-- State  38

-- State  39

-- State  40

-- State  41

-- State  42

-- State  43

-- State  44

-- State  45

-- State  46

-- State  47

-- State  48

-- State  49

-- State  50

-- State  51

-- State  52

-- State  53

-- State  54

-- State  55

-- State  56

-- State  57

-- State  58

-- State  59

-- State  60

-- State  61

-- State  62

-- State  63

-- State  64

-- State  65

-- State  66

-- State  67

-- State  68

-- State  69

-- State  70

-- State  71

-- State  72

-- State  73

-- State  74

-- State  75

-- State  76

-- State  77

-- State  78

-- State  79

-- State  80

-- State  81

-- State  82

-- State  83
,(-18, 93)
-- State  84

-- State  85
,(-8, 95)

-- State  86

-- State  87

-- State  88

-- State  89

-- State  90

-- State  91

-- State  92

-- State  93

-- State  94

-- State  95

-- State  96

-- State  97

-- State  98

-- State  99

-- State  100

-- State  101

-- State  102

-- State  103

);
--  The offset vector
GOTO_OFFSET : array (0.. 103) of Integer :=
( 0,
 1, 3, 11, 11, 11, 15, 15, 16, 17, 17,
 17, 21, 24, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
 26, 26, 26, 27, 27, 28, 28, 28, 28, 28,
 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
 28, 28, 28);

subtype Rule        is Natural;
subtype Nonterminal is Integer;

   Rule_Length : array (Rule range  0 ..  70) of Natural := ( 2,
 0, 2, 2, 3, 0, 2, 2, 4,
 4, 4, 2, 2, 2, 4, 4, 4,
 2, 2, 1, 2, 2, 1, 1, 1,
 1, 1, 1, 1, 1, 1, 1, 1,
 1, 1, 1, 1, 1, 1, 1, 1,
 1, 1, 1, 1, 1, 1, 1, 1,
 1, 1, 1, 1, 1, 1, 1, 1,
 1, 1, 1, 1, 1, 1, 1, 4,
 6, 1, 1, 1, 1, 1);
   Get_LHS_Rule: array (Rule range  0 ..  70) of Nonterminal := (-1,
-2,-2,-3,-3,-4,-4,-6,-5,
-5,-5,-5,-5,-5,-5,-5,-5,
-5,-5,-5,-5,-5,-9,-9,-9,
-9,-9,-9,-10,-10,-12,-14,-14,
-14,-14,-14,-14,-14,-14,-14,-16,
-16,-16,-16,-16,-16,-16,-15,-15,
-15,-15,-15,-15,-15,-15,-15,-7,
-7,-11,-11,-11,-13,-13,-13,-8,
-8,-17,-17,-18,-18,-18);
end Ma_Syntax_Goto;
