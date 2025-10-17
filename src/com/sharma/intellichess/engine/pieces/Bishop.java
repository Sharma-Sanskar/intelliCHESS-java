package com.sharma.intellichess.engine.pieces;

import java.util.List;

public class Bishop extends Piece{
    public Bishop(String color, int[] pos){
        super(color,pos);
    }

    @Override
    public List<int[]> getLegalMoves(Board board) {
        return List.of();
    }
}
