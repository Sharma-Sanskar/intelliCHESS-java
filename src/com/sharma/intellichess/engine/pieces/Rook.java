package com.sharma.intellichess.engine.pieces;

import com.sharma.intellichess.engine.Board;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(String color, int[] pos){
        super(color,pos);
    }


    @Override
    public List<int[]> getLegalMoves(Board board) {
        List<int[]> legalMovies = new ArrayList<>();
        int row = position[0];
        int col = position[1];

        int[][] directions = {
                {-1,0}, // up
                {1,0}, // down
                {0,-1}, // left
                {0,1} // right
        };


        return List.of();
    }

}
