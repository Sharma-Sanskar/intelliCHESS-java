package com.sharma.intellichess.movegen;

import com.sharma.intellichess.engine.Board;
import com.sharma.intellichess.engine.Move;
import com.sharma.intellichess.engine.Piece;
import com.sharma.intellichess.bitboard.BitboardUtils;
import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

    public static List<Move> generateMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        
        // 1. Setup Phase
        boolean isWhite = board.whiteToMove;
        long myPieces = isWhite ? getWhitePieces(board) : getBlackPieces(board);
        
        // 2. The Big 4 Generators
        generatePawnMoves(moves, board, myPieces);  // <--- NOW DEFINED!
        generateKnightMoves(moves, board, myPieces);
        generateSliderMoves(moves, board, myPieces); 
        generateKingMoves(moves, board, myPieces);

        return moves;
    }

    // --- 1. PAWN LOGIC (The New Part) ---
    private static void generatePawnMoves(List<Move> moves, Board board, long myPieces) {
        if (board.whiteToMove) {
            generateWhitePawnMoves(moves, board);
        } else {
            generateBlackPawnMoves(moves, board);
        }
    }

    private static void generateWhitePawnMoves(List<Move> moves, Board board) {
        long pawns = board.bitboards[Piece.WHITE_PAWN];
        long emptySquares = ~board.getOccupancy();
        long enemyPieces = board.getBlackOccupancy();

        // A. Single Push (North +8)
        // Shift pawns UP 8. AND with empty squares to ensure we don't hit anything.
        long singlePush = (pawns << 8) & emptySquares;
        extractPawnMoves(moves, board, singlePush, -8, Piece.WHITE_PAWN); // -8 to find "from" square

        // B. Double Push (Rank 2 -> Rank 4)
        // Take the valid single pushes, filter for Rank 3 (index 16-23), shift UP 8 again.
        long doublePush = ((singlePush & BitboardUtils.RANK_3) << 8) & emptySquares;
        extractPawnMoves(moves, board, doublePush, -16, Piece.WHITE_PAWN); // -16 to find "from" square

        // C. Captures (NorthWest +7, NorthEast +9)
        // Note: We must mask out FILE_A and FILE_H to prevent wrapping around the board!
        long captureWest = (pawns << 7) & enemyPieces & ~BitboardUtils.FILE_H; 
        long captureEast = (pawns << 9) & enemyPieces & ~BitboardUtils.FILE_A;

        extractPawnMoves(moves, board, captureWest, -7, Piece.WHITE_PAWN);
        extractPawnMoves(moves, board, captureEast, -9, Piece.WHITE_PAWN);
    }

    private static void generateBlackPawnMoves(List<Move> moves, Board board) {
        long pawns = board.bitboards[Piece.BLACK_PAWN];
        long emptySquares = ~board.getOccupancy();
        long enemyPieces = board.getWhiteOccupancy();

        // A. Single Push (South -8)
        long singlePush = (pawns >>> 8) & emptySquares;
        extractPawnMoves(moves, board, singlePush, 8, Piece.BLACK_PAWN); // +8 to go back to source

        // B. Double Push (Rank 7 -> Rank 5)
        long doublePush = ((singlePush & BitboardUtils.RANK_6) >>> 8) & emptySquares;
        extractPawnMoves(moves, board, doublePush, 16, Piece.BLACK_PAWN);

        // C. Captures (SouthEast -7, SouthWest -9)
        long captureEast = (pawns >>> 7) & enemyPieces & ~BitboardUtils.FILE_A;
        long captureWest = (pawns >>> 9) & enemyPieces & ~BitboardUtils.FILE_H;

        extractPawnMoves(moves, board, captureEast, 7, Piece.BLACK_PAWN);
        extractPawnMoves(moves, board, captureWest, 9, Piece.BLACK_PAWN);
    }

    // Special extractor for pawns (calculates "Source" from "Target")
    private static void extractPawnMoves(List<Move> moves, Board board, long validMoves, int offsetToSource, int pieceType) {
        while (validMoves != 0) {
            int targetSquare = Long.numberOfTrailingZeros(validMoves);
            int sourceSquare = targetSquare + offsetToSource;
            int capturedPiece = board.squarePiece[targetSquare];
            
            moves.add(new Move(sourceSquare, targetSquare, pieceType, capturedPiece));
            validMoves &= validMoves - 1;
        }
    }

    // --- 2. SLIDER LOGIC ---
    private static void generateSliderMoves(List<Move> moves, Board board, long myPieces) {
        int rook   = board.whiteToMove ? Piece.WHITE_ROOK   : Piece.BLACK_ROOK;
        int bishop = board.whiteToMove ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP;
        int queen  = board.whiteToMove ? Piece.WHITE_QUEEN  : Piece.BLACK_QUEEN;

        generateMovesForSliderType(moves, board, rook, myPieces);
        generateMovesForSliderType(moves, board, bishop, myPieces);
        generateMovesForSliderType(moves, board, queen, myPieces);
    }

    private static void generateMovesForSliderType(List<Move> moves, Board board, int pieceType, long myPieces) {
        long pieces = board.bitboards[pieceType];
        long allOccupancy = board.getOccupancy(); 

        while (pieces != 0) {
            int sourceSquare = Long.numberOfTrailingZeros(pieces);
            long attacks = 0L;

            if (pieceType == Piece.WHITE_ROOK || pieceType == Piece.BLACK_ROOK) {
                attacks = SliderAttacks.getRookAttacks(sourceSquare, allOccupancy);
            } else if (pieceType == Piece.WHITE_BISHOP || pieceType == Piece.BLACK_BISHOP) {
                attacks = SliderAttacks.getBishopAttacks(sourceSquare, allOccupancy);
            } else { // Queen
                attacks = SliderAttacks.getQueenAttacks(sourceSquare, allOccupancy);
            }

            long validMoves = attacks & ~myPieces;
            extractMoves(moves, board, sourceSquare, pieceType, validMoves);
            pieces &= pieces - 1;
        }
    }

    // --- 3. STEPPER LOGIC (Knight/King) ---
    private static void generateKnightMoves(List<Move> moves, Board board, long myPieces) {
        int knightType = board.whiteToMove ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT;
        long knights = board.bitboards[knightType];
        while (knights != 0) {
            int sourceSquare = Long.numberOfTrailingZeros(knights);
            long attacks = KnightMoves.getAttacks(sourceSquare); 
            long validMoves = attacks & ~myPieces;
            extractMoves(moves, board, sourceSquare, knightType, validMoves);
            knights &= knights - 1;
        }
    }

    private static void generateKingMoves(List<Move> moves, Board board, long myPieces) {
        int kingType = board.whiteToMove ? Piece.WHITE_KING : Piece.BLACK_KING;
        long king = board.bitboards[kingType];
        if (king != 0) {
            int sourceSquare = Long.numberOfTrailingZeros(king);
            long attacks = KingMoves.getAttacks(sourceSquare);
            long validMoves = attacks & ~myPieces;
            extractMoves(moves, board, sourceSquare, kingType, validMoves);
        }
    }

    // --- HELPERS ---
    
    // Standard extractor for everything else
    private static void extractMoves(List<Move> moves, Board board, int sourceSquare, int piece, long validMoves) {
        while (validMoves != 0) {
            int targetSquare = Long.numberOfTrailingZeros(validMoves);
            int capturedPiece = board.squarePiece[targetSquare];
            moves.add(new Move(sourceSquare, targetSquare, piece, capturedPiece));
            validMoves &= validMoves - 1;
        }
    }
    
    private static long getWhitePieces(Board board) {
        return board.bitboards[Piece.WHITE_PAWN] | board.bitboards[Piece.WHITE_KNIGHT] |
               board.bitboards[Piece.WHITE_BISHOP] | board.bitboards[Piece.WHITE_ROOK] |
               board.bitboards[Piece.WHITE_QUEEN] | board.bitboards[Piece.WHITE_KING];
    }

    private static long getBlackPieces(Board board) {
        return board.bitboards[Piece.BLACK_PAWN] | board.bitboards[Piece.BLACK_KNIGHT] |
               board.bitboards[Piece.BLACK_BISHOP] | board.bitboards[Piece.BLACK_ROOK] |
               board.bitboards[Piece.BLACK_QUEEN] | board.bitboards[Piece.BLACK_KING];
    }
}