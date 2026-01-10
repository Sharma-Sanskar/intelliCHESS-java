package com.sharma.intellichess.bitboard;

public class Masks {
    // Files
    public static final long A_FILE = 0x0101010101010101L;
    public static final long B_FILE = 0x0202020202020202L;
    public static final long AB_FILE = 0x0303030303030303L;
    public static final long G_FILE = 0x4040404040404040L;
    public static final long H_FILE = 0x8080808080808080L;
    public static final long GH_FILE = 0xC0C0C0C0C0C0C0C0L;

    // NOT Files
    public static final long NOT_A_FILE = ~A_FILE;
    public static final long NOT_B_FILE = ~B_FILE;
    public static final long NOT_AB_FILE = ~AB_FILE;
    public static final long NOT_G_FILE = ~G_FILE;
    public static final long NOT_H_FILE = ~H_FILE;
    public static final long NOT_GH_FILE = ~GH_FILE;
}
