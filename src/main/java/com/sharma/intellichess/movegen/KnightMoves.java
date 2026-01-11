package com.sharma.intellichess.movegen;
import com.sharma.intellichess.bitboard.Masks;

public class KnightMoves {
    // precomputed look up table
    static final long[] KNIGHT_ATTACKS = new long[64];

    // initialize on class load;
    static {
        initKnightAttacks();
    }

    // Initialize attack board for all positions
    private static void initKnightAttacks(){
        for (int square = 0; square < 64; square++) {
            long knightBit = 1L << square;
            KNIGHT_ATTACKS[square] = generateKnightAttacks(knightBit);
        }
    }

    // Public API: Get Attack table for One Knight
    public static long getAttacks(int square){
        return KNIGHT_ATTACKS[square];
    }

    // Public API: Faster lookup for multiple Knights
    public static long getAttacks(long knights){
        long attacks = 0L;
        while (knights != 0){
            int square = Long.numberOfTrailingZeros(knights); // find the lowest set bit
            attacks |= KNIGHT_ATTACKS[square];
            knights &= knights -1; // update knight
        }
        return attacks;
    }

    private static long generateKnightAttacks(long knights) {
        long attacks = 0L;

        // === Moves that go 1 file RIGHT (can't start from H-file) ===
        attacks |= (knights & Masks.NOT_H_FILE) << 17;  // +2 ranks, +1 file
        attacks |= (knights & Masks.NOT_H_FILE) >> 15;  // -2 ranks, +1 file

        // === Moves that go 1 file LEFT (can't start from A-file) ===
        attacks |= (knights & Masks.NOT_A_FILE) << 15;  // +2 ranks, -1 file
        attacks |= (knights & Masks.NOT_A_FILE) >> 17;  // -2 ranks, -1 file

        // === Moves that go 2 files RIGHT (can't start from G or H) ===
        attacks |= (knights & Masks.NOT_GH_FILE) << 10; // +1 rank, +2 files
        attacks |= (knights & Masks.NOT_GH_FILE) >> 6;  // -1 rank, +2 files

        // === Moves that go 2 files LEFT (can't start from A or B) ===
        attacks |= (knights & Masks.NOT_AB_FILE) << 6;  // +1 rank, -2 files
        attacks |= (knights & Masks.NOT_AB_FILE) >> 10; // -1 rank, -2 files

        return attacks;
    }

}
