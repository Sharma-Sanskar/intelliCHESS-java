package com.sharma.intellichess.movegen;

public class SliderAttacks {

    public static long getRookAttacks(int square, long occupancy) {
        long attacks = 0L;

        // North (Positive Direction)
        attacks |= getPositiveRay(square, occupancy, RookRays.getRay(square, RookRays.NORTH), RookRays.NORTH, true);
        // South (Negative Direction)
        attacks |= getNegativeRay(square, occupancy, RookRays.getRay(square, RookRays.SOUTH), RookRays.SOUTH, true);
        // East (Positive)
        attacks |= getPositiveRay(square, occupancy, RookRays.getRay(square, RookRays.EAST), RookRays.EAST, true);
        // West (Negative)
        attacks |= getNegativeRay(square, occupancy, RookRays.getRay(square, RookRays.WEST), RookRays.WEST, true);

        return attacks;
    }

    public static long getBishopAttacks(int square, long occupancy) {
        long attacks = 0L;

        // NE (Positive: +9)
        attacks |= getPositiveRay(square, occupancy, BishopRays.getRay(square, BishopRays.NORTH_EAST), BishopRays.NORTH_EAST, false);
        // NW (Positive: +7)
        attacks |= getPositiveRay(square, occupancy, BishopRays.getRay(square, BishopRays.NORTH_WEST), BishopRays.NORTH_WEST, false);
        // SE (Negative: -7)
        attacks |= getNegativeRay(square, occupancy, BishopRays.getRay(square, BishopRays.SOUTH_EAST), BishopRays.SOUTH_EAST, false);
        // SW (Negative: -9)
        attacks |= getNegativeRay(square, occupancy, BishopRays.getRay(square, BishopRays.SOUTH_WEST), BishopRays.SOUTH_WEST, false);

        return attacks;
    }

    public static long getQueenAttacks(int square, long occupancy) {
        return getRookAttacks(square, occupancy) | getBishopAttacks(square, occupancy);
    }

    // --- The Shadow Logic Helpers ---

    private static long getPositiveRay(int square, long occupancy, long ray, int direction, boolean isRook) {
        long blockers = ray & occupancy;
        if (blockers != 0) {
            // Find first blocker (Lowest index for positive rays)
            int blockerSquare = Long.numberOfTrailingZeros(blockers);
            
            // Get the ray STARTING from the blocker (Shadow)
            long shadow;
            if (isRook) {
                shadow = RookRays.getRay(blockerSquare, direction);
            } else {
                shadow = BishopRays.getRay(blockerSquare, direction);
            }
            
            // XOR removes the shadow, leaving the path + capture
            return ray ^ shadow;
        }
        return ray;
    }

    private static long getNegativeRay(int square, long occupancy, long ray, int direction, boolean isRook) {
        long blockers = ray & occupancy;
        if (blockers != 0) {
            // Find first blocker (Highest index for negative rays)
            // 63 - LeadingZeros gives the index of the highest 1 bit
            int blockerSquare = 63 - Long.numberOfLeadingZeros(blockers);
            
            long shadow;
            if (isRook) {
                shadow = RookRays.getRay(blockerSquare, direction);
            } else {
                shadow = BishopRays.getRay(blockerSquare, direction);
            }
            
            return ray ^ shadow;
        }
        return ray;
    }
}