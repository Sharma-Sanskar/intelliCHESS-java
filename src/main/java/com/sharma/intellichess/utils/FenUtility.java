   package com.sharma.intellichess.utils;

import com.sharma.intellichess.bitboard.BitboardUtils;

public class FenUtility {
    /*
    * Translates FEN String to boardState
    * initial State = rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    * 1. Piece placement (BoardState)
    * 2. Active Turn 'w' for white 'b' for black
    * 3. Available Castling K = White king side available, q = Black queen side
    * available
    * 4. en Passant Target -> Shows skipped square of a pawn that moved 2 squares
    * 5. HalfMove Clock -> This counts how many moves have passed since the last
    * pawn move or piece capture.
    * 6. Full Move Counter -> counts total turns, increment 1 after black's turn
    */

    /* --- 1. THE VISUAL BOARD --- */
    public char[][] boardState = new char[8][8];

    /* --- 2. THE BITBOARDS --- */
    // White Pieces
    public long WP, WN, WB, WR, WQ, WK;
    // Black Pieces
    public long BP, BN, BB, BR, BQ, BK;

    /* --- Game Variables --- */
    public boolean whiteTurn;
    public boolean castleWK, castleWQ, castleBk, castleBq;
    public String enPassantTarget;
    public int halfMoveClock;
    public int fullMoveCounter;

    public void parseFEN(String FEN) {
        // Reset everything before parsing
        clearState();

        String[] parts = FEN.split(" ");

        // 1. Parse Board Layout
        parseBoard(parts[0]);

        // 2. Parse Game Info
        whiteTurn = parts[1].equals("w");
        String castling = parts[2];
        castleWK = castling.contains("K");
        castleWQ = castling.contains("Q");
        castleBk = castling.contains("k");
        castleBq = castling.contains("q");
        enPassantTarget = parts[3];

        try {
            halfMoveClock = Integer.parseInt(parts[4]);
            fullMoveCounter = Integer.parseInt(parts[5]);
        } catch (Exception e) {
            halfMoveClock = 0;
            fullMoveCounter = 1;
        }
    }

    private void parseBoard(String boardPart) {
        int row = 0;
        int col = 0;

        for (int i = 0; i < boardPart.length(); i++) {
            char c = boardPart.charAt(i);

            if (c == '/') {
                row++;
                col = 0;
            } else if (Character.isDigit(c)) {
                col += (c - '0');
            } else {
                // UPDATE 1: Update the Visual Array
                boardState[row][col] = c;

                // UPDATE 2: Update the Bitboards
                // We map (row, col) to a 0-63 index (A1=0, H8=63)
                int rank = 7 - row;
                int file = col;
                int squareIndex = rank * 8 + file;

                setBit(c, squareIndex);

                col++;
            }
        }
    }

    // Helper to flip the correct bit on the correct board
    private void setBit(char piece, int squareIndex) {
        long bit = 1L << squareIndex;

        switch (piece) {
            case 'P': WP |= bit; break;
            case 'N': WN |= bit; break;
            case 'B': WB |= bit; break;
            case 'R': WR |= bit; break;
            case 'Q': WQ |= bit; break;
            case 'K': WK |= bit; break;
            
            case 'p': BP |= bit; break;
            case 'n': BN |= bit; break;
            case 'b': BB |= bit; break;
            case 'r': BR |= bit; break;
            case 'q': BQ |= bit; break;
            case 'k': BK |= bit; break;
        }
    }

    private void clearState() {
        // Reset bitboards to 0
        WP=0; WN=0; WB=0; WR=0; WQ=0; WK=0;
        BP=0; BN=0; BB=0; BR=0; BQ=0; BK=0;
        
        // Clear array
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) boardState[i][j] = ' '; 
        }
    }
    
    // Debug method to verify Bitboards are working
    public void printBitboardStats() {
        System.out.println("--- Bitboard Check ---");
        System.out.println("White Pawns (WP): " + Long.toBinaryString(WP));
        System.out.println("Black Kings (BK): " + Long.toBinaryString(BK));
    }
    public static void main(String[] args) {
    FenUtility game = new FenUtility();
    game.parseFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

    // 1. Check Visual Array (Should show 'P' at [6][0])
    System.out.println("Visual check: " + game.boardState[6][0]); 

    // 2. Check Bitboard (Should show a huge number)
    game.printBitboardStats();
    System.out.println("Checking White Pawns:");
    BitboardUtils.printBitboard(game.WP);
}
}