package com.sharma.intelliCHESS;

public class GameStateTest {
    /* Setup Game Variables */
    // Initialize new Board State
    char[][] boardState = new char[8][8];
    boolean whiteTurn;  // Tracks Turns
    boolean castleWK, castleWQ, castleBk, castleBq;  // Tracks available Castling
    String enPassantTarget;  // Track en-Passant Square
    int halfMoveClock;
    int fullMoveCounter;

    public void initBoard () {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i][j] = '.';
            }
        }
    }

    public void printBoard ( char[][] boardState){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // prints indices
                // System.out.printf("[%d,%d] ",i,j);

                // prints empty 8x8 board
                System.out.printf("[%c]", boardState[i][j]);
            }
            System.out.println();
        }
    }

    public void parseFEN(String FEN) {
        /* Translates FEN String to boardState
           initial State = rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
           1. Piece placement (BoardState)
           2. Active Turn 'w' for white 'b' for black
           3. Available Castling K = White king side available, q = Black queen side available
           4. en Passant Target -> Shows skipped square of a pawn that moved 2 squares
           5. HalfMove Clock -> This counts how many moves have passed since the last pawn move or piece capture.
           6. Full Move Counter -> counts total turns, increment 1 after black's turn
        */

        // split FEN into 6 parts, Delimiter = 'space'
        String[] parts = FEN.split(" ");
        parseBoard(parts[0]); // initialize Board

        this.whiteTurn = parts[1].equals("w");

        String castling = parts[2];
        this.castleWK = castling.contains("K");
        this.castleWQ = castling.contains("Q");
        this.castleBk = castling.contains("k");
        this.castleBq = castling.contains("q");

        this.enPassantTarget = parts[3];

        try {
            this.halfMoveClock = Integer.parseInt(parts[4]);
            this.fullMoveCounter = Integer.parseInt(parts[5]);
        } catch (Exception e) {
            // Fallback if FEN is short/malformed
            this.halfMoveClock = 0;
            this.fullMoveCounter = 1;
        }

    }

    // Parse a FEN string into Board State
    public void parseBoard(String FEN){
        initBoard();
        int row = 0;
        int col = 0;

        for (int i = 0; i < FEN.length(); i++) {
            char c = FEN.charAt(i);
            // Check for '/' to update Row
            if (c == '/') {
                row++;
                col = 0;
                continue;
            } else if (Character.isDigit(c)) {
                int skip = (c - '0');
                col += skip;
            } else if (row < 8 && col < 8) {
                // update boardState
                boardState[row][col] = c;
                col++;
            }
        }


    }


    public void printGameState(){
        System.out.println("Turn -> "+ (whiteTurn? "White" : "Black"));

        System.out.println("Castling available -> ");
        System.out.println("White king side -> " + (castleWK? "Yes" : "No"));
        System.out.println("White queen side -> " + (castleWQ? "Yes" : "No"));
        System.out.println("Black king side -> " + (castleBk? "Yes" : "No"));
        System.out.println("Black queen side -> " + (castleBq? "Yes" : "No"));

        System.out.println("en-Passant Square -> "+ enPassantTarget);
        System.out.println("Half Move Clock -> "+ halfMoveClock);
        System.out.println("Full Move Counter -> "+ fullMoveCounter);

    }
    public static void main (String[]args){
        GameStateTest engine = new GameStateTest();
        String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        engine.parseFEN(FEN);
        engine.printBoard(engine.boardState);
        engine.printGameState();
    }
}