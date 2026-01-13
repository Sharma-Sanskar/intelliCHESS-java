package com.sharma.intellichess.demo;

import com.sharma.intellichess.bitboard.BitboardUtils;
import com.sharma.intellichess.movegen.KingMoves;
import com.sharma.intellichess.movegen.KnightMoves;
import com.sharma.intellichess.movegen.PawnMoves;

public class Demo {

    public static void main(String[] args) {
        System.out.println("====== INTELLICHESS MOVE GENERATION TEST ======");

        testKnightMoves();
        testKingMoves();
        testPawnMechanics();
    }

    private static void testKnightMoves() {
        System.out.println("\n--- TEST 1: KNIGHT MOVES ---");
        
        // 1. Center Knight (D4)
        int d4 = BitboardUtils.squareToBit("d4");
        System.out.println("Knight on d4 (Should have 8 moves):");
        long moves = KnightMoves.getAttacks(d4);
        BitboardUtils.printBitboard(moves, 'N');

        // 2. Corner Knight (A1)
        int a1 = BitboardUtils.squareToBit("a1");
        System.out.println("Knight on a1 (Should have 2 moves):");
        moves = KnightMoves.getAttacks(a1);
        BitboardUtils.printBitboard(moves, 'N');
    }

    private static void testKingMoves() {
        System.out.println("\n--- TEST 2: KING MOVES ---");

        // 1. Side King (H4) - Should not wrap to A-file
        int h4 = BitboardUtils.squareToBit("h4");
        System.out.println("King on h4 (Edge test - should not wrap to left):");
        long moves = KingMoves.getAttacks(h4);
        BitboardUtils.printBitboard(moves, 'K');
    }

    private static void testPawnMechanics() {
        System.out.println("\n--- TEST 3: PAWN MECHANICS ---");

        // Scenario: White Pawn on E2, Black Pawn on E3 (Blocking), Black Pawn on D3 (Capture target)
        long whitePawns = (1L << BitboardUtils.squareToBit("e2"));
        long blackPieces = (1L << BitboardUtils.squareToBit("e3")) | (1L << BitboardUtils.squareToBit("d3"));
        
        // Calculate Occupancy (All pieces)
        long allPieces = whitePawns | blackPieces;
        long emptySquares = ~allPieces; // Bitwise NOT to get empty squares

        System.out.println("Scenario: White Pawn on E2.");
        System.out.println("Blocker on E3 (Should prevent push).");
        System.out.println("Enemy on D3 (Should allow capture).");

        // 1. Test Single Push
        long singlePush = PawnMoves.whiteSinglePush(whitePawns, emptySquares);
        System.out.print("\nResult - Single Push (Should be empty due to blocker): ");
        if (singlePush == 0) System.out.println("PASSED (Blocked)");
        else BitboardUtils.printBitboard(singlePush, 'P');

        // 2. Test Double Push (Only works if single push works)
        long doublePush = PawnMoves.whiteDoublePush(whitePawns, emptySquares);
        System.out.print("Result - Double Push: ");
        if (doublePush == 0) System.out.println("PASSED (Blocked)");
        else BitboardUtils.printBitboard(doublePush, 'P');

        // 3. Test Captures
    // White Pawn on E2. Enemy Piece on D3.
    // E2 index = 12. D3 index = 19.
    // NorthWest capture: 12 + 7 = 19. Matches!
    long captures = PawnMoves.whiteAttacks(whitePawns, blackPieces);
    
    System.out.println("\nResult - Captures (Should show X on D3):");
    if (captures == 0) System.out.println("FAILED: No captures generated.");
    else BitboardUtils.printBitboard(captures, 'X');
    }
}