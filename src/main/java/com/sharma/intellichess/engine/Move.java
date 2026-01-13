package com.sharma.intellichess.engine;

import java.util.List;

import com.sharma.intellichess.bitboard.BitboardUtils;
import com.sharma.intellichess.movegen.KingMoves;
import com.sharma.intellichess.movegen.KnightMoves;

public class Move {
    public final int sourceSquare;
    public final int targetSquare;
    public final int pieceMoved;
    public final int pieceCaptured; // Piece.NONE if no capture
    
    // Flags
    public final boolean isCapture;
    
    // Constructor
    public Move(int source, int target, int piece, int captured) {
        this.sourceSquare = source;
        this.targetSquare = target;
        this.pieceMoved = piece;
        this.pieceCaptured = captured;
        this.isCapture = (captured != Piece.NONE);
    }

    @Override
    public String toString() {
        // Prints "e2e4"
        return BitboardUtils.bitToSquare(sourceSquare) +
               BitboardUtils.bitToSquare(targetSquare);
    }

    
}