package com.sharma.intellichess.bitboard;

import com.sharma.intellichess.movegen.BishopRays;
import com.sharma.intellichess.movegen.RookRays;

public class BitboardUtils {

    // --- 1. COORDINATE CONVERTERS ---

    /**
     * Converts a square name (e.g., "e4") to a bitboard index (0-63).
     */
    public static int squareToBit(String square) {
        char fileChar = square.charAt(0); // 'e'
        char rankChar = square.charAt(1); // '4'
        
        int file = fileChar - 'a';
        int rank = rankChar - '1';
        
        return rank * 8 + file;
    }

    /**
     * Converts a bitboard index (0-63) to a square name (e.g., "e4").
     */
    public static String bitToSquare(int bit) {
        int rankIndex = bit / 8;
        int fileIndex = bit % 8;

        char file = (char) ('a' + fileIndex);
        char rank = (char) ('1' + rankIndex);

        return "" + file + rank;
    }

    // --- 2. MOVE GENERATION HELPERS ---

    public static long getQueenRays(int square) {
        return RookRays.getRay(square) | BishopRays.getRay(square);
    }

    // --- 3. VISUALIZATION TOOLS ---

    /**
     * Prints the bitboard using '1' for set bits.
     */
    public static void printBitboard(long bitboard) {
        printBitboard(bitboard, '1'); // Delegate to the main method
    }

    /**
     * Prints the bitboard using a custom character for set bits.
     */
    public static void printBitboard(long bitboard, char symbol) {
        System.out.println("   A  B  C  D  E  F  G  H");
        
        for (int rank = 7; rank >= 0; rank--) {
            System.out.printf("%d ", rank + 1); // Print Rank Number (Left)
            
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                long mask = 1L << square;

                if ((bitboard & mask) != 0) {
                    System.out.printf(" %c ", symbol);
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println(); // New line after every rank
        }
        System.out.println(); // Extra spacing at bottom
    }

    /**
     * Prints the 0-63 index of every square (Useful for debugging).
     */
    public static void printBitNumbers() {
        System.out.println("   A   B   C   D   E   F   G   H");
        
        for (int rank = 7; rank >= 0; rank--) {
            System.out.printf("%d ", rank + 1);
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.printf("%3d ", square);
            }
            System.out.println();
        }
        System.out.println();
    }
}