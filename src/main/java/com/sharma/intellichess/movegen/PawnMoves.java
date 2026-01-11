package com.sharma.intellichess.movegen;

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
        long singlePush = (pushOnRank2 & emptySquares) >> 8;
        long doublePush = (singlePush & emptySquares) >> 8;
        return doublePush;
    }
}
