package com.sharma.intellichess.engine;

import java.util.Arrays;

public class Board {

    
    // 12 bitboards (0-5 White, 6-11 Black)
    public long[] bitboards = new long[12];
    
    // Mailbox for fast lookup (-1 if empty)
    public int[] squarePiece = new int[64];
    
    // Turn tracker
    public boolean whiteToMove = true;

    public Board() {
        Arrays.fill(squarePiece, Piece.NONE);
    }

    public void addPiece(int square, int piece) {
        // 1. Set the bit in the bitboard
        bitboards[piece] |= (1L << square);
        // 2. Set the ID in the array
        squarePiece[square] = piece;
    }
    
    // Call this if you need to clear the board
    public void clear() {
        Arrays.fill(bitboards, 0L);
        Arrays.fill(squarePiece, Piece.NONE);
        whiteToMove = true;
    }
    
    // Quick helper to get occupancy
    public long getOccupancy() {
        long occ = 0L;
        for (long bb : bitboards) occ |= bb;
        return occ;
    }

    // Add these to com.sharma.intellichess.engine.Board

    public long getWhiteOccupancy() {
        return bitboards[Piece.WHITE_PAWN] | bitboards[Piece.WHITE_KNIGHT] |
               bitboards[Piece.WHITE_BISHOP] | bitboards[Piece.WHITE_ROOK] |
               bitboards[Piece.WHITE_QUEEN] | bitboards[Piece.WHITE_KING];
    }

    public long getBlackOccupancy() {
        return bitboards[Piece.BLACK_PAWN] | bitboards[Piece.BLACK_KNIGHT] |
               bitboards[Piece.BLACK_BISHOP] | bitboards[Piece.BLACK_ROOK] |
               bitboards[Piece.BLACK_QUEEN] | bitboards[Piece.BLACK_KING];
    }
}