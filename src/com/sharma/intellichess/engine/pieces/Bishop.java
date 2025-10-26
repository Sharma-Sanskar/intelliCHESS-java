package com.sharma.intellichess.engine.pieces;

import com.sharma.intellichess.engine.Board;

import java.util.List;

public class Bishop extends Piece{
    @Override
    public List<int[]> getLegalMoves(Board board) {
        return List.of();
    }

    public Bishop(String color, int[] pos){
        super(color,pos);
    }

}
