package com.sharma.intellichess.engine.pieces;

import java.util.List;
import com.sharma.intellichess.engine.Board;

public class Knight extends Piece{
    public Knight(String color, int[] pos){
        super(color,pos);
    }

    @Override
    public List<int[]> getLegalMoves(Board board) {
        return List.of();
    }
}
