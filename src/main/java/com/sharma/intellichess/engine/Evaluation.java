package com.sharma.intellichess.engine;

public class Evaluation {

    // Standard piece values (Centipawns)
    public static final int PAWN_VAL = 100;
    public static final int KNIGHT_VAL = 320;
    public static final int BISHOP_VAL = 330;
    public static final int ROOK_VAL = 500;
    public static final int QUEEN_VAL = 900;
    public static final int KING_VAL = 20000;

    public static int evaluate(Board board) {
        int whiteScore = 0;
        int blackScore = 0;

        // 1. Count Material (The fast way: count set bits)
        whiteScore += Long.bitCount(board.bitboards[Piece.WHITE_PAWN]) * PAWN_VAL;
        whiteScore += Long.bitCount(board.bitboards[Piece.WHITE_KNIGHT]) * KNIGHT_VAL;
        whiteScore += Long.bitCount(board.bitboards[Piece.WHITE_BISHOP]) * BISHOP_VAL;
        whiteScore += Long.bitCount(board.bitboards[Piece.WHITE_ROOK]) * ROOK_VAL;
        whiteScore += Long.bitCount(board.bitboards[Piece.WHITE_QUEEN]) * QUEEN_VAL;

        blackScore += Long.bitCount(board.bitboards[Piece.BLACK_PAWN]) * PAWN_VAL;
        blackScore += Long.bitCount(board.bitboards[Piece.BLACK_KNIGHT]) * KNIGHT_VAL;
        blackScore += Long.bitCount(board.bitboards[Piece.BLACK_BISHOP]) * BISHOP_VAL;
        blackScore += Long.bitCount(board.bitboards[Piece.BLACK_ROOK]) * ROOK_VAL;
        blackScore += Long.bitCount(board.bitboards[Piece.BLACK_QUEEN]) * QUEEN_VAL;

        // 2. Return relative score (Positive if current side is winning)
        int score = whiteScore - blackScore;
        return board.whiteToMove ? score : -score;
    }
}