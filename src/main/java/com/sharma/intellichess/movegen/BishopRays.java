package com.sharma.intellichess.movegen;

import com.sharma.intellichess.bitboard.BitboardUtils;

public class BishopRays {
    public static final long[][] RAYS = new long[64][4];

    // Directions Constants
    public static final int NORTH_EAST = 0;
    public static final int NORTH_WEST = 1;
    public static final int SOUTH_EAST = 2;
    public static final int SOUTH_WEST = 3;

    static {
        initRays();
    }

    private static void initRays(){
        for (int square = 0; square < 64; square++) {
            RAYS[square][NORTH_EAST] = generateNorthEastRay(square);
            RAYS[square][NORTH_WEST] = generateNorthWestRay(square);
            RAYS[square][SOUTH_EAST] = generateSouthEastRay(square);
            RAYS[square][SOUTH_WEST] = generateSouthWestRay(square);

        }
    }
    // Public Getter
    public static long getRay(int square, int direction){
        return RAYS[square][direction];
    }

    // Public Getter for RAYS of all Directions
    public static long getRay(int square){
        return RAYS[square][NORTH_EAST] | RAYS[square][NORTH_WEST] |
               RAYS[square][SOUTH_EAST] | RAYS[square][SOUTH_WEST];
    }

    private static long generateNorthEastRay(int square) {
        long ray = 0L;
        int rank = square / 8;
        int file = square % 8;

        // Move NE: Rank increases, File increases
        for (int r = rank + 1, f = file + 1; r < 8 && f < 8; r++, f++) {
            ray |= (1L << (r * 8 + f));
        }
        return ray;
    }

    private static long generateNorthWestRay(int square) {
        long ray = 0L;
        int rank = square / 8;
        int file = square % 8;

        // Move NW: Rank increases, File decreases
        for (int r = rank + 1, f = file - 1; r < 8 && f >= 0; r++, f--) {
            ray |= (1L << (r * 8 + f));
        }
        return ray;
    }

    private static long generateSouthEastRay(int square) {
        long ray = 0L;
        int rank = square / 8;
        int file = square % 8;

        // Move SE: Rank decreases, File increases
        for (int r = rank - 1, f = file + 1; r >= 0 && f < 8; r--, f++) {
            ray |= (1L << (r * 8 + f));
        }
        return ray;
    }

    private static long generateSouthWestRay(int square) {
        long ray = 0L;
        int rank = square / 8;
        int file = square % 8;

        // Move SW: Rank decreases, File decreases
        for (int r = rank - 1, f = file - 1; r >= 0 && f >= 0; r--, f--) {
            ray |= (1L << (r * 8 + f));
        }
        return ray;
    }

    // Sanity Check
    public static void main(String[] args) {
        int e4 = BitboardUtils.squareToBit("e4");
        System.out.println("Bishop Rays from e4:");
        
        System.out.println("North East:");
        BitboardUtils.printBitboard(RAYS[e4][NORTH_EAST], 'X');
        
        System.out.println("South West:");
        BitboardUtils.printBitboard(RAYS[e4][SOUTH_WEST], 'X');

        System.out.println("Full X:");
        BitboardUtils.printBitboard(getRay(e4), 'B');
    }
}
