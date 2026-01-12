package com.sharma.intellichess.movegen;

import com.sharma.intellichess.bitboard.BitboardUtils;
public class RookRays {

    public static final long[][] RAYS = new long[64][4];
    // Directions Constant
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    static {
        initRays();
    }

    private static void initRays(){
        for (int square = 0; square < 64; square++) {
            RAYS[square][NORTH] = generateNorthRay(square);
            RAYS[square][SOUTH] = generateSouthRay(square);
            RAYS[square][EAST] = generateEastRay(square);
            RAYS[square][WEST] = generateWestRay(square);
        }
    }

    // Public getter
    public static long getRay(int square, int direction) {
        return RAYS[square][direction];
    }

    public static long getRay(int square){
        return RAYS[square][NORTH] | RAYS[square][SOUTH] | RAYS[square][EAST] | RAYS[square][WEST];
    }


    private static long generateNorthRay(int square){
        long ray = 0L;

        int rank = (square / 8);
        int file = (square % 8) ;
        for (int i = rank + 1; i <= 7; i++) {
            int targetSquare = i * 8 + file; 
            ray |= (1L << targetSquare);
        }

        return ray;
    }

    private static long generateSouthRay(int square){
        long ray = 0L;

        int rank = (square / 8);
        int file = (square % 8) ;
        for (int i = rank -1; i >= 0; i--) {
            int targetSquare = i * 8 + file; 
            ray |= (1L << targetSquare);
        }

        return ray;
    }

    private static long generateEastRay(int square){
        long ray = 0L;
        int rank = (square / 8);
        int file = (square % 8) ;

        for (int i = file + 1; i <= 7; i++) {
            int targetSquare = rank * 8 + i; 
            ray |= (1L << targetSquare);
        }

        return ray;
    }

    private static long generateWestRay(int square){
        long ray = 0L;

        int rank = (square / 8);
        int file = (square % 8) ;
        for (int i = file - 1; i >= 0; i--) {
            int targetSquare = rank * 8 + i; 
            ray |= (1L << targetSquare);
        }

        return ray;
    }
    // Test it with main method
    public static void main(String[] args) {
        // Test lookup from precomputed table
        System.out.println("Precomputed rays from e4:");
        BitboardUtils.printBitboard(RAYS[28][NORTH], 'N');
        BitboardUtils.printBitboard(RAYS[28][SOUTH], 'S');
        BitboardUtils.printBitboard(RAYS[28][EAST], 'E');
        BitboardUtils.printBitboard(RAYS[28][WEST], 'W');

        BitboardUtils.printBitboard((getRay(28)),'N');
    }
}
