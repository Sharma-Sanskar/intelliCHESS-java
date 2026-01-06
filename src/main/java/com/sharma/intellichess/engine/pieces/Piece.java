package com.sharma.intellichess.engine.pieces;

import com.sharma.intellichess.engine.Board;

import java.util.List;

public abstract class Piece {
    protected String color;
    protected int[] position;
    protected char name;

    public abstract List<int[]> getLegalMoves(Board board);

    public Piece(String color, int[] position){
        this.color = color;
        this.position = position;
    }

    public String getColor(){
        return this.color;
    }

}
