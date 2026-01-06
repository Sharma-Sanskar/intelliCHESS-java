package com.sharma.intellichess.engine.pieces;

import java.util.List;
import com.sharma.intellichess.engine.Board;

public class Knight extends Piece{
    public Knight(String color, int[] pos,char name){
        super(color, pos);
        this.name = 'N';

    }

    @Override
    public List<int[]> getLegalMoves(Board board) {
        return List.of();
    }
}
