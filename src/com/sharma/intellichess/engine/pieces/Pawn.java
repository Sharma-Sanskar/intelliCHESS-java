package com.sharma.intellichess.engine.pieces;

import com.sharma.intellichess.engine.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(String color, int[] pos){
        super(color,pos);
        this.hasMoved = false;
    }

    @Override
    public List<int[]> getLegalMoves(Board board){

        return new ArrayList<>();
    }
}
