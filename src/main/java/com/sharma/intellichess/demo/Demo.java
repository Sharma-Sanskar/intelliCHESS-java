package com.sharma.intellichess.demo;

import com.sharma.intellichess.bitboard.BitboardUtils;
import com.sharma.intellichess.movegen.KingMoves;
import com.sharma.intellichess.movegen.KnightMoves;
import com.sharma.intellichess.movegen.PawnMoves;

public class Demo {

    public static void main(String[] args) {
        // testKnightMoves();
        testPawnMoves();
    }
    private static void testPawnMoves() {
    System.out.println("=== PAWN MOVE GENERATION TEST ===\n");
    
    // Test 1: White single push from starting position
    System.out.println("Test 1: White pawn e2 - single push");
    long pawnE2 = 1L << 12;  // e2
    long emptyBoard = ~0L & ~pawnE2;  // All empty except pawn
    long singlePush = PawnMoves.whiteSinglePush(pawnE2, emptyBoard);
    BitboardUtils.printBitboard(pawnE2);
    System.out.println("Single push:");
    BitboardUtils.printBitboard(singlePush);
    System.out.println("Expected: 1 move (e3) | Got: " + Long.bitCount(singlePush));
    System.out.println(Long.bitCount(singlePush) == 1 ? "✅ PASS\n" : "❌ FAIL\n");
    
        // Add this BEFORE Test 2
    System.out.println("DEBUG: Pawn e2 bitboard:");
    BitboardUtils.printBitboard(pawnE2);
    System.out.println("DEBUG: RANK_2 mask:");
    long RANK_2 = 0x000000000000FF00L;
    BitboardUtils.printBitboard(RANK_2);
    System.out.println("DEBUG: pawnE2 & RANK_2:");
    BitboardUtils.printBitboard(pawnE2 & RANK_2);
    System.out.println();

    // Test 2: White double push from starting position
    System.out.println("Test 2: White pawn e2 - double push");
    long doublePush = PawnMoves.whiteDoublePush(pawnE2, emptyBoard);
    System.out.println("Double push:");
    BitboardUtils.printBitboard(doublePush);
    System.out.println("Expected: 1 move (e4) | Got: " + Long.bitCount(doublePush));
    System.out.println(Long.bitCount(doublePush) == 1 ? "✅ PASS\n" : "❌ FAIL\n");
    
    // Test 3: Blocked pawn (no moves)
    System.out.println("Test 3: White pawn e2 - blocked by piece on e3");
    long blockedBoard = ~0L & ~pawnE2 & ~(1L << 20);  // e3 occupied
    long blockedPush = PawnMoves.whiteSinglePush(pawnE2, blockedBoard);
    System.out.println("Blocked single push:");
    BitboardUtils.printBitboard(blockedPush);
    System.out.println("Expected: 0 moves | Got: " + Long.bitCount(blockedPush));
    System.out.println(Long.bitCount(blockedPush) == 0 ? "✅ PASS\n" : "❌ FAIL\n");
    
    // Test 4: All white pawns starting position
    System.out.println("Test 4: All white pawns - single push");
    long allWhitePawns = 0x000000000000FF00L;  // All on rank 2
    long emptyForAll = ~allWhitePawns;
    long allSinglePush = PawnMoves.whiteSinglePush(allWhitePawns, emptyForAll);
    BitboardUtils.printBitboard(allWhitePawns);
    System.out.println("All single pushes:");
    BitboardUtils.printBitboard(allSinglePush);
    System.out.println("Expected: 8 moves | Got: " + Long.bitCount(allSinglePush));
    System.out.println(Long.bitCount(allSinglePush) == 8 ? "✅ PASS\n" : "❌ FAIL\n");
}

   private static void testKnightMoves() {
    System.out.println("=== KNIGHT MOVE GENERATION TEST ===\n");
    
    // Test 1: Knight in center (e4 = square 28)
    System.out.println("Test 1: Knight on e4 (center)");
    long knightE4 = 1L << 28;
    long movesE4 = KnightMoves.getAttacks(knightE4);
    BitboardUtils.printBitboard(knightE4);
    System.out.println("Moves:");
    BitboardUtils.printBitboard(movesE4);
    System.out.println("Expected: 8 moves | Got: " + Long.bitCount(movesE4));
    System.out.println(Long.bitCount(movesE4) == 8 ? "✅ PASS\n" : "❌ FAIL\n");
    
    // Test 2: Knight in corner (a1 = square 0)
    System.out.println("Test 2: Knight on a1 (corner)");
    long knightA1 = 1L << 0;
    long movesA1 = KnightMoves.getAttacks(knightA1);
    BitboardUtils.printBitboard(knightA1);
    System.out.println("Moves:");
    BitboardUtils.printBitboard(movesA1);
    System.out.println("Expected: 2 moves | Got: " + Long.bitCount(movesA1));
    System.out.println(Long.bitCount(movesA1) == 2 ? "✅ PASS\n" : "❌ FAIL\n");
    
    // Test 3: Knight on edge (a4 = square 24)
    System.out.println("Test 3: Knight on a4 (edge)");
    long knightA4 = 1L << 24;
    long movesA4 = KnightMoves.getAttacks(knightA4);
    BitboardUtils.printBitboard(knightA4);
    System.out.println("Moves:");
    BitboardUtils.printBitboard(movesA4);
    System.out.println("Expected: 4 moves | Got: " + Long.bitCount(movesA4));
    System.out.println(Long.bitCount(movesA4) == 4 ? "✅ PASS\n" : "❌ FAIL\n");
}


}
