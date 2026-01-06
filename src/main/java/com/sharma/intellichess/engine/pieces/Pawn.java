package com.sharma.intellichess.engine.pieces;

import com.sharma.intellichess.engine.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(String color, int[] position){
        super(color,position);
        this.hasMoved = false;
    }

    @Override
    public List<int[]> getLegalMoves(Board board){
        int row = position[0];
        int col = position[1];

        if(!hasMoved){
            // if noy moved, move 2 blocks
            

            hasMoved = true;
        }


        return new ArrayList<>();
    }
}
