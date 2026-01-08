package com.sharma.intelliCHESS;

public class BitBoardUtils
{
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
                int square = rank * 8 + file;
                long mask = 1L << square;
                if ((bitboard & mask) != 0){
                    System.out.print(" 1 ");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }

    }
    public static void main(String[] args) {
        System.out.println("=== Test 1: Single pawn on E2 ===");
        printBitBoard(1L << 12);

        System.out.println("\n=== Test 2: Two pawns (E2 and D2) ===");
        long twoPawns = (1L << 12) | (1L << 11);
        printBitBoard(twoPawns);

        System.out.println("\n=== Test 3: Full rank 2 (white pawns) ===");
        printBitBoard(0xFF00L);

        System.out.println("\n=== Test 4: All white starting pieces ===");
        printBitBoard(0x000000000000FFFFL);

        System.out.println("\n=== Test 5: Knight on E4 ===");
        printBitBoard(1L << squareToBit("e4"));
    }



}
