package com.sharma.intellichess.engine.pieces;
import java.util.List;

public class King extends Piece {
    public King(String color, int[] pos) {
        super(color, pos);
    }

    @Override
    public List<int[]> getLegalMoves(Board board) {
        return List.of();
    }
}
