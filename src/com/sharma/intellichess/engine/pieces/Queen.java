package com.sharma.intellichess.engine.pieces;
import com.sharma.intellichess.engine.Board;

import java.util.List;
public class Queen extends Piece{
    public Queen(String color, int[] pos){
        super(color,pos);
    }

    @Override
    public List<int[]> getLegalMoves(Board board) {
        return List.of();
    }
}
