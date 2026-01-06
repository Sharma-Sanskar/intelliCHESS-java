package com.sharma.intellichess.engine;
import  com.sharma.intellichess.engine.pieces.*;
public class Board {
    private final Piece[][] grid;

    public Board(){
        this.grid = new Piece[8][8];
        setupBoard();
    }

    char[][] boardState = new char[8][8];
    public void initBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i][j] = '.';
            }
        }
    }
    public void printBoard(char[][] boardState){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(boardState[i][j]);
            }
            System.out.println();
        }
    }
    private void setupBoard(){
        // Place black pieces
        grid[0][0] = new Rook("black", new int[]{0, 0});
//        grid[0][1] = new Knight("black", new int[]{0, 1});
        grid[0][2] = new Bishop("black", new int[]{0, 2});
        grid[0][3] = new Queen("black", new int[]{0, 3});
        grid[0][4] = new King("black", new int[]{0, 4});
        grid[0][5] = new Bishop("black", new int[]{0, 5});
//        grid[0][6] = new Knight("black", new int[]{0, 6});
        grid[0][7] = new Rook("black", new int[]{0, 7});
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn("black", new int[]{1, i});
        }

        // Place white pieces
        grid[7][0] = new Rook("white", new int[]{7, 0});
//        grid[7][1] = new Knight("white", new int[]{7, 1});
        grid[7][2] = new Bishop("white", new int[]{7, 2});
        grid[7][3] = new Queen("white", new int[]{7, 3});
        grid[7][4] = new King("white", new int[]{7, 4});
        grid[7][5] = new Bishop("white", new int[]{7, 5});
//        grid[7][6] = new Knight("white", new int[]{7, 6});
        grid[7][7] = new Rook("white", new int[]{7, 7});
        for (int i = 0; i < 8; i++) {
            grid[6][i] = new Pawn("white", new int[]{6, i});
        }
    }

    public Piece[][] getGrid(){
        return this.grid;
    }


    public void displayBoard(){
        for(int row = 0; row <8; row++){
            System.out.print(8 - row+ " ");
            for(int col = 0; col < 8; col++){
                Piece piece = grid[row][col];
                System.out.print(getPieceSymbol(piece));
            }
            System.out.println();
        }
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    public String getPieceSymbol(Piece piece){
        if (piece == null)
            return "[  ]";

        char colorChar = piece.getColor().charAt(0);
        char typeChar = piece.getClass().getSimpleName().charAt(0);
        if (piece instanceof Knight){
            typeChar = 'N';
        }

        return String.format("[%c%c]",colorChar,typeChar);
    }

}



