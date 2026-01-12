package com.sharma.intellichess;

import com.sharma.intellichess.bitboard.BitboardUtils;
import com.sharma.intellichess.bitboard.Masks;
import com.sharma.intellichess.movegen.*;

public class DebugMain {

    public static void main(String[] args) {
        System.out.println("=== INTELLICHESS SYSTEM DIAGNOSTIC ===\n");

        testKnights();
        testKings();
        testPawnPushes();
        testSliderShadows(); // The Big Boss Test

        System.out.println("\n=== ALL SYSTEMS GREEN? ===");
    }

    // ==========================================
    // TEST 1: KNIGHTS (The Leapers)
    // ==========================================
    private static void testKnights() {
        System.out.println("--- Testing Knights ---");
        int e4 = BitboardUtils.squareToBit("e4");
        int h1 = BitboardUtils.squareToBit("h1"); // Edge case

        long e4Attacks = KnightMoves.getAttacks(e4);
        long h1Attacks = KnightMoves.getAttacks(h1);

        // Expected E4: 8 squares. Expected H1: 2 squares (F2, G3)
        System.out.println("Knight on E4 (Should be circle of 8):");
        BitboardUtils.printBitboard(e4Attacks, 'N');
        
        System.out.println("Knight on H1 (Should be only F2, G3):");
        BitboardUtils.printBitboard(h1Attacks, 'N');
        
        if (Long.bitCount(e4Attacks) == 8 && Long.bitCount(h1Attacks) == 2) {
            System.out.println(">> KNIGHTS: PASS ✅");
        } else {
            System.out.println(">> KNIGHTS: FAIL ❌");
        }
    }

    // ==========================================
    // TEST 2: KINGS (The Monarch)
    // ==========================================
    private static void testKings() {
        System.out.println("\n--- Testing Kings ---");
        int a1 = BitboardUtils.squareToBit("a1");
        long a1Attacks = KingMoves.getAttacks(a1);

        // Expected: A2, B1, B2 (3 squares)
        System.out.println("King on A1 (Corner Check):");
        BitboardUtils.printBitboard(a1Attacks, 'K');

        if (Long.bitCount(a1Attacks) == 3) {
            System.out.println(">> KINGS: PASS ✅");
        } else {
            System.out.println(">> KINGS: FAIL ❌");
        }
    }

    // ==========================================
    // TEST 3: PAWN PUSHES (The Physics)
    // ==========================================
    private static void testPawnPushes() {
        System.out.println("\n--- Testing Pawns ---");
        
        // Scenario: White Pawns on A2, B2. Blocked on A3.
        long whitePawns = (1L << BitboardUtils.squareToBit("a2")) | (1L << BitboardUtils.squareToBit("b2"));
        long occupancy = (1L << BitboardUtils.squareToBit("a3")); // A3 is blocked!
        long emptySquares = ~occupancy;

        long singlePush = PawnMoves.whiteSinglePush(whitePawns, emptySquares);
        long doublePush = PawnMoves.whiteDoublePush(whitePawns, emptySquares);

        System.out.println("White Pawns: A2, B2. Blocker: A3.");
        System.out.println("Single Push Result (Should only be B3):");
        BitboardUtils.printBitboard(singlePush, 'P');

        System.out.println("Double Push Result (Should only be B4):");
        // A2 blocked by A3. B2 moves to B3 -> B4.
        BitboardUtils.printBitboard(doublePush, 'P');
        
        // Validation
        boolean a3Blocked = (singlePush & (1L << BitboardUtils.squareToBit("a3"))) == 0;
        boolean b4Reached = (doublePush & (1L << BitboardUtils.squareToBit("b4"))) != 0;

        if (a3Blocked && b4Reached) {
            System.out.println(">> PAWNS: PASS ✅");
        } else {
            System.out.println(">> PAWNS: FAIL ❌");
        }
    }

    // ==========================================
    // TEST 4: SLIDERS (The Shadow Logic)
    // ==========================================
    private static void testSliderShadows() {
        System.out.println("\n--- Testing Slider Shadows (Rook/Bishop) ---");

        // Scenario: Rook on D4. 
        // Blockers: D7 (North), D2 (South), G4 (East), B4 (West).
        int d4 = BitboardUtils.squareToBit("d4");
        long blockers = 0L;
        blockers |= (1L << BitboardUtils.squareToBit("d7"));
        blockers |= (1L << BitboardUtils.squareToBit("d2"));
        blockers |= (1L << BitboardUtils.squareToBit("g4"));
        blockers |= (1L << BitboardUtils.squareToBit("b4"));

        System.out.println("Rook on D4. Blockers at D7, D2, G4, B4.");
        long attacks = SliderAttacks.getRookAttacks(d4, blockers);
        BitboardUtils.printBitboard(attacks | blockers, 'X'); 
        // Note: I printed | blockers so you can see the 'X' hitting the wall.
        
        // CHECK NORTH: Should hit D5, D6, D7. Should NOT hit D8.
        boolean hitsD7 = (attacks & (1L << BitboardUtils.squareToBit("d7"))) != 0;
        boolean hitsD8 = (attacks & (1L << BitboardUtils.squareToBit("d8"))) != 0;

        // CHECK EAST: Should hit E4, F4, G4. Should NOT hit H4.
        boolean hitsG4 = (attacks & (1L << BitboardUtils.squareToBit("g4"))) != 0;
        boolean hitsH4 = (attacks & (1L << BitboardUtils.squareToBit("h4"))) != 0;

        if (hitsD7 && !hitsD8 && hitsG4 && !hitsH4) {
            System.out.println(">> SHADOW LOGIC: PASS ✅ (Rook rays stopped correctly)");
        } else {
            System.out.println(">> SHADOW LOGIC: FAIL ❌ (Light leaked through walls)");
            System.out.println("Hits D7 (Wall)? " + hitsD7);
            System.out.println("Hits D8 (Behind Wall)? " + hitsD8);
        }
    }
}