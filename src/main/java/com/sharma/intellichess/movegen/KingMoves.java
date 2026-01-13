package com.sharma.intellichess.movegen;
// import com.sharma.intellichess.bitboard.BitboardUtils;
import com.sharma.intellichess.bitboard.Masks;

public class KingMoves {

    private static final long[] KING_ATTACKS = new long[64];

    static {
        initKingAttacks();
    }

    private static void initKingAttacks(){
        for (int square = 0; square < 64; square++) {
            long kingBit = 1L << square;
            KING_ATTACKS[square] = generateKingMoves(kingBit); 
        }
    }

    // NOTE: Do not Call, only for generating one time
    private static long generateKingMoves(long king){ 
        long attacks = 0L; //SHOULD BE 0L not 1L

        long NOT_A_FILE = Masks.NOT_A_FILE;
        long NOT_H_FILE = Masks.NOT_H_FILE;

        attacks |= (king & NOT_H_FILE) << 1; // East
        attacks |= (king & NOT_A_FILE) >> 1; // West

        attacks |= king << 8; // North
        attacks |= king >> 8; // South

        attacks |= (king & NOT_A_FILE) << 7; // Northwest
        attacks |= (king & NOT_H_FILE) >> 7; // Southwest
        attacks |= (king & NOT_H_FILE) << 9; // Northeast
        attacks |= (king & NOT_A_FILE) >> 9; // Southeast

        return attacks;

    }

    // Public API: Get attacks for one King
    public static long getAttacks(int square) {
        return KING_ATTACKS[square];
    }
    
    // Public API: Get attacks for multiple kings (though there's only 1 king per side in chess)
    public static long getAttacks(long kings) {
        long attacks = 0L;
        while (kings != 0) {
            int square = Long.numberOfTrailingZeros(kings);
            attacks |= KING_ATTACKS[square];
            kings &= kings - 1;
        }
        return attacks;
    }
}
