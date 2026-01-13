package com.sharma.intellichess.demo;

import com.sharma.intellichess.utils.FenUtility;
import com.sharma.intellichess.bitboard.BitboardUtils;

public class FenTest {

    public static void main(String[] args) {
        System.out.println("--- TESTING FEN PARSER ---");

        FenUtility game = new FenUtility();
        
        // 1. Test the Standard Start Position
        String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("Parsing: " + startFEN);
        game.parseFEN(startFEN);

        // CHECK 1: The Visual Array (Did it populate the char[][]?)
        System.out.println("\n[1] Visual Board Check (char[][]):");
        printVisualBoard(game.boardState);

        // CHECK 2: The Bitboards (Did it populate the longs?)
        System.out.println("\n[2] Bitboard Check (White Pawns):");
        // This should show a row of 'P's on Rank 2
        BitboardUtils.printBitboard(game.WP, 'P');

        System.out.println("[3] Bitboard Check (Black Knights):");
        // This should show 'n' at B8 and G8
        BitboardUtils.printBitboard(game.BN, 'n');
        
        // 2. Test a Tricky "Puzzle" Position
        // (White King on E1, Black King on E8, One White Rook on A1)
        String puzzleFEN = "4k3/8/8/8/8/8/8/R3K3 w Q - 0 1";
        System.out.println("\n\n--- SWITCHING TO PUZZLE FEN ---");
        System.out.println("Parsing: " + puzzleFEN);
        game.parseFEN(puzzleFEN);
        
        System.out.println("\n[4] Bitboard Check (White Rooks):");
        // Should only see one Rook at bottom left (A1)
        BitboardUtils.printBitboard(game.WR, 'R');
    }

    // Helper to print the char[][] array quickly
    private static void printVisualBoard(char[][] board) {
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " "); // Rank number
            for (int j = 0; j < 8; j++) {
                System.out.print("[" + board[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println("   A  B  C  D  E  F  G  H");
    }
}