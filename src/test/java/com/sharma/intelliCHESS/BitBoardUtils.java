package com.sharma.intelliCHESS;

public class BitBoardUtils
{
    // Files
    static final long A_FILE = 0x0101010101010101L;
    static final long B_FILE = 0x0202020202020202L;
    static final long AB_FILE = 0x0303030303030303L;
    static final long G_FILE = 0x4040404040404040L;
    static final long H_FILE = 0x8080808080808080L;
    static final long GH_FILE = 0xC0C0C0C0C0C0C0C0L;

    // NOT FILES
    static final long NOT_A_FILE = ~A_FILE;
    static final long NOT_B_FILE = ~B_FILE;
    static final long NOT_AB_FILE = ~AB_FILE;
    static final long NOT_G_FILE = ~G_FILE;
    static final long NOT_H_FILE = ~H_FILE;
    static final long NOT_GH_FILE = ~GH_FILE;

    static final long KNIGHT_ATTACKS[] = new long[64];

    static {
        initKnightAttacks();
    }

    private static void initKnightAttacks(){
        for (int square = 0; square < 64; square++) {
            long knightBit = 1L << square;
            KNIGHT_ATTACKS[square] = generateKnightAttacks(knightBit);
        }
    }

    public static long getKnightAttacks(int square){
        return KNIGHT_ATTACKS[square];
    }

    // Faster lookup for multiple knights
    public static long getKnightsAttacks(long knights){
        long attacks = 0L;
        while (knights != 0){
            int square = Long.numberOfTrailingZeros(knights); // find the lowest set bit
            attacks |= KNIGHT_ATTACKS[square];
            knights &= knights -1; // update knight
        }
        return attacks;
    }
    public static int squareToBit(String square) {
        // Extract characters
        char fileChar = square.charAt(0); // 'e'
        char rankChar = square.charAt(1); // '2'
        int result;
        // Convert to numbers (0-7)
        // YOUR CODE: How do you turn 'e' into 4?
        int file = fileChar - 'a';
        int rank = rankChar - '1';
        // YOUR CODE: How do you turn '2' into 1?
        result = rank * 8 + file;
        // YOUR CODE: Apply the formula you wrote
        return result;
    }

    public static String bitToSquare(int bit) {
        // Step 1: Get rank and file indices (0-7)
        int rankIndex = bit / 8;
        int fileIndex = bit % 8;

        // Step 2: Convert to characters
        // YOUR CODE: How do you convert fileIndex (4) to 'e'?
        char file = (char)(fileIndex + 'a');
        // YOUR CODE: How do you convert rankIndex (1) to '2'?
        char rank = (char)(rankIndex + '1');
        // Step 3: Build and return the string
        // YOUR CODE: Combine file char + rank char into a String
        return "" + file + rank; // ""+ forces java to treat it like string
    }

    public static void printBitBoard(long bitboard){

        System.out.println("   A  B  C  D  E  F  G  H");
        for (int rank = 7; rank >= 0; rank--) {
            System.out.print(rank + 1 + " ");
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file; // calculate attack squares on each square
                long mask = 1L << square; // flip bits on, on a new bitboard mask
                if ((bitboard & mask) != 0){ // bitboard's position AND attack square pos
                    System.out.print(" 1 ");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }



    }

    public static long generateKnightAttacks(long knights) {
        long attacks = 0L;

        // === Moves that go 1 file RIGHT (can't start from H-file) ===
        attacks |= (knights & NOT_H_FILE) << 17;  // +2 ranks, +1 file
        attacks |= (knights & NOT_H_FILE) >> 15;  // -2 ranks, +1 file

        // === Moves that go 1 file LEFT (can't start from A-file) ===
        attacks |= (knights & NOT_A_FILE) << 15;  // +2 ranks, -1 file
        attacks |= (knights & NOT_A_FILE) >> 17;  // -2 ranks, -1 file

        // === Moves that go 2 files RIGHT (can't start from G or H) ===
        attacks |= (knights & NOT_GH_FILE) << 10; // +1 rank, +2 files
        attacks |= (knights & NOT_GH_FILE) >> 6;  // -1 rank, +2 files

        // === Moves that go 2 files LEFT (can't start from A or B) ===
        attacks |= (knights & NOT_AB_FILE) << 6;  // +1 rank, -2 files
        attacks |= (knights & NOT_AB_FILE) >> 10; // -1 rank, -2 files

        return attacks;
    }




    public static void printBitNumbers() {
        System.out.println("   A  B  C  D  E  F  G  H");
        for (int rank = 7; rank >= 0; rank--) {
            System.out.print((rank + 1) + " ");
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.printf("%2d ", square);  // Print bit number, 2 digits wide
            }
            System.out.println();
        }
        System.out.println();
    }


    private static void testKnight(String square, int expectedMoves) {
        long knight = 1L << squareToBit(square);
        long attacks = generateKnightAttacks(knight);

        System.out.println("Knight position:");
        printBitBoard(knight);
        System.out.println("Attacks:");
        printBitBoard(attacks);

        // Count set bits
        int count = Long.bitCount(attacks);
        System.out.println("Generated " + count + " attacks (expected " + expectedMoves + ")");

        if (count == expectedMoves) {
            System.out.println("✅ PASS");
        } else {
            System.out.println("❌ FAIL");
        }
    }
    public static void main(String[] args) {
        System.out.println("=== Testing Lookup Table ===\n");

        // Test 1: Single square lookup
        System.out.println("E4 knight attacks (lookup):");
        printBitBoard(getKnightAttacks(28));

        // Test 2: Multiple knights
        System.out.println("\nTwo knights (E4 + B1):");
        long twoKnights = (1L << 28) | (1L << 1);
        printBitBoard(getKnightsAttacks(twoKnights));

        // Test 3: Verify it matches old method
        System.out.println("\n=== Verification: Lookup vs Calculation ===");
        for (int square = 0; square < 64; square++) {
            long lookup = KNIGHT_ATTACKS[square];
            long calculated = generateKnightAttacks(1L << square);
            if (lookup != calculated) {
                System.out.println("❌ MISMATCH at square " + square);
                return;
            }
        }
        System.out.println("✅ All 64 squares match!");
    }







}
