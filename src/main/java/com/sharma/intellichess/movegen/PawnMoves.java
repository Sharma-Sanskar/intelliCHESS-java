package com.sharma.intellichess.movegen;

import com.sharma.intellichess.bitboard.Masks;

public class PawnMoves {
    
    public static final long RANK_2 = 0x000000000000FF00L;
    public static final long RANK_7 = 0x00FF000000000000L;

    /**
     * Generate single push moves for white pawns
     * @param pawns White pawn positions
     * @param emptySquares Bitboard of empty squares
     * @return Bitboard of target squares
     */
    public static long whiteSinglePush(long pawns, long emptySquares){
        
        long pushTargets = pawns << 8; 
        return pushTargets & emptySquares;
    }

    /**
     * Generate double push moves for white pawns
     * @param pawns White pawn positions
     * @param emptySquares Bitboard of empty squares
     * @return Bitboard of target squares
     */
    public static long whiteDoublePush(long pawns, long emptySquares){

        long pushOnRank2 = pawns & RANK_2; // check if pawns are on RANK 2
        long singlePush = (pushOnRank2 << 8) & emptySquares;
        long doublePush = (singlePush << 8 ) & emptySquares;
        return doublePush;
    }

    /**
     * Generate single push moves for black pawns
     * @param pawns Black pawn positions
     * @param emptySquares Bitboard of empty squares
     * @return Bitboard of target squares
     */

    public static long blackSinglePush(long pawns, long emptySquares){
        long pushTargets = pawns >> 8;
        return pushTargets & emptySquares;
    }

    /**
     * Generate double push moves for black pawns
     * @param pawns Black pawn positions
     * @param emptySquares Bitboard of empty squares
     * @return Bitboard of target squares
     */

    public static long blackDoublePush(long pawns, long emptySquares){
        long pushOnRank2 = pawns & RANK_7;
        long singlePush = (pushOnRank2 >> 8 ) & emptySquares;
        long doublePush = (singlePush >> 8 ) & emptySquares;
        return doublePush;
    }

    /**
     * Generate diagonal capture targets for WHITE pawns
     * @param pawns Bitboard of white pawns
     * @param blackPieces Bitboard of ALL black pieces (to check valid captures)
     */
    public static long whiteAttacks(long pawns, long blackPieces) {
        // 1. Capture Left (NorthWest): Shift up 8, left 1 (+7)
        // Must mask out File H (because moving "Left" from File A is impossible, 
        // but the shift logic handles the wrap. Wait, actually:
        // NorthWest (+7) from File A wraps to H? No.
        // 56 (A8) -> 63 (H8). Correct.
        // We actually need to prevent capturing FROM the A-File going "Left" (which wraps to H).
        // The shift +7 moves a bit from index 0 (A1) to 7 (H1). That is a WRAP.
        // So we must Mask NOT_A_FILE.
        
        long leftCaptures = (pawns & Masks.NOT_A_FILE) << 7;
        
        // 2. Capture Right (NorthEast): Shift up 8, right 1 (+9)
        // We must Mask NOT_H_FILE to prevent wrapping from H to A.
        long rightCaptures = (pawns & Masks.NOT_H_FILE) << 9;

        // 3. Combine them, then AND with enemy pieces
        // We only care if an enemy is actually standing there.
        return (leftCaptures | rightCaptures) & blackPieces;
    }

    /**
     * Generate diagonal capture targets for BLACK pawns
     * @param pawns Bitboard of black pawns
     * @param whitePieces Bitboard of ALL white pieces
     */
    public static long blackAttacks(long pawns, long whitePieces) {
        // 1. Capture "Right" (SouthEast from Black's perspective, but board direction is SouthEast)
        // Down 8, Right 1 (-7). Mask NOT_H_FILE.
        long rightCaptures = (pawns & Masks.NOT_H_FILE) >> 7;
        
        // 2. Capture "Left" (SouthWest). Down 8, Left 1 (-9). Mask NOT_A_FILE.
        long leftCaptures = (pawns & Masks.NOT_A_FILE) >> 9;

        return (rightCaptures | leftCaptures) & whitePieces;
    }
}
