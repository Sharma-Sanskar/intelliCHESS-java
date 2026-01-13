package com.sharma.intellichess.utils;

import com.sharma.intellichess.engine.Board;
import com.sharma.intellichess.engine.Piece;

public class FenUtility {

    // Helper to translate FEN Char to Engine Piece ID
    private static int getPieceFromChar(char c) {
        switch (c) {
            case 'P': return Piece.WHITE_PAWN;   case 'p': return Piece.BLACK_PAWN;
            case 'N': return Piece.WHITE_KNIGHT; case 'n': return Piece.BLACK_KNIGHT;
            case 'B': return Piece.WHITE_BISHOP; case 'b': return Piece.BLACK_BISHOP;
            case 'R': return Piece.WHITE_ROOK;   case 'r': return Piece.BLACK_ROOK;
            case 'Q': return Piece.WHITE_QUEEN;  case 'q': return Piece.BLACK_QUEEN;
            case 'K': return Piece.WHITE_KING;   case 'k': return Piece.BLACK_KING;
            default: return Piece.NONE;
        }
    }

    // The Bridge: Loads FEN string directly into the Board object
    public static void loadFen(Board board, String fen) {
        // 1. Clear existing board
        board.clear();

        String[] parts = fen.split(" ");
        String position = parts[0];

        int row = 0;
        int col = 0;

        for (int i = 0; i < position.length(); i++) {
            char c = position.charAt(i);

            if (c == '/') {
                row++;
                col = 0;
            } else if (Character.isDigit(c)) {
                col += Character.getNumericValue(c);
            } else {
                int piece = getPieceFromChar(c);
                
                // Convert (Row, Col) to Engine Square Index
                // Row 0 is Rank 8. Row 7 is Rank 1.
                int rank = 7 - row;
                int file = col;
                int square = rank * 8 + file;

                board.addPiece(square, piece);
                col++;
            }
        }

        // 2. Set Turn
        board.whiteToMove = parts[1].equals("w");
        
        // (Optional) Parse Castling rights here later if you add flags to Board.java
    }
}