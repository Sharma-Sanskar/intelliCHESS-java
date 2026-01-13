package com.sharma.intellichess;

import com.sharma.intellichess.engine.Board;
import com.sharma.intellichess.engine.Move;
import com.sharma.intellichess.engine.Piece;
import com.sharma.intellichess.movegen.KingMoves;
import com.sharma.intellichess.movegen.KnightMoves;
import com.sharma.intellichess.movegen.MoveGenerator;
import com.sharma.intellichess.bitboard.BitboardUtils; // Assuming you have this from before

import java.util.List;

public class SanityCheck {
    public static void main(String[] args) {
        System.out.println("--- SYSTEM DIAGNOSTIC START ---");

        // 1. Test Board Creation
        Board board = new Board();
        System.out.println("[PASS] Board instantiated.");

        // 2. Test Piece Placement (The "Bridge" Logic)
        // Place White Rook (ID 3) on E4 (Square 28)
        board.addPiece(28, Piece.WHITE_ROOK);
        // Place Black King (ID 11) on E8 (Square 60)
        board.addPiece(60, Piece.BLACK_KING);
        
        System.out.println("[PASS] Pieces added manually.");

        // 3. Verify Bitboard Update
        if ((board.bitboards[Piece.WHITE_ROOK] & (1L << 28)) != 0) {
            System.out.println("[PASS] White Rook Bitboard updated correctly.");
        } else {
            System.out.println("[FAIL] Bitboard logic is broken.");
        }

        // 4. Verify Mailbox Update
        if (board.squarePiece[28] == Piece.WHITE_ROOK) {
            System.out.println("[PASS] Mailbox array updated correctly.");
        } else {
            System.out.println("[FAIL] Mailbox logic is broken.");
        }

        // 5. Test Move Generator Connection
        System.out.println("Attempting to generate moves...");
        List<Move> moves = MoveGenerator.generateMoves(board);
        System.out.println("[PASS] MoveGenerator ran without crashing.");
        System.out.println("Moves found: " + moves.size() + " (Expected: 0, as logic is commented out)");

        System.out.println("--- SYSTEM GREEN. GO TO SLEEP. ---");
    }

    
}