package com.sharma.intellichess;

import com.sharma.intellichess.engine.Board;
import com.sharma.intellichess.engine.Move;
import com.sharma.intellichess.engine.Piece;
import com.sharma.intellichess.movegen.MoveGenerator;
import java.util.List;

public class SanityCheck {
    public static void main(String[] args) {
        System.out.println("=== TACTICAL SYSTEM CHECK ===");

        Board board = new Board();
        board.clear();
        
        // --- SCENARIO SETUP ---
        // 1. ROOK TEST: Friendly Fire (A1 blocked by A2)
        board.addPiece(0, Piece.WHITE_ROOK);
        board.addPiece(8, Piece.WHITE_KNIGHT);
        
        // 2. PAWN TEST: Pushes & Captures (E2 vs D3)
        board.addPiece(12, Piece.WHITE_PAWN);
        board.addPiece(19, Piece.BLACK_PAWN);
        
        // 3. KING TEST (H1)
        board.addPiece(7, Piece.WHITE_KING);

        System.out.println("Board Setup:");
        System.out.println(" - Rook on a1 (Blocked North by friendly Knight)");
        System.out.println(" - Pawn on e2 (Enemy Pawn on d3)");
        System.out.println(" - King on h1");

        // --- EXECUTE ---
        List<Move> moves = MoveGenerator.generateMoves(board);
        
        // --- VERIFICATION ---
        System.out.println("\nGenerated " + moves.size() + " moves. Analyzing...");
        
        boolean rookBlocked = true;
        boolean pawnPushFound = false;
        boolean pawnDoublePushFound = false;
        boolean pawnCaptureFound = false;

        for (Move m : moves) {
            String moveStr = m.toString(); // <--- THIS WAS MISSING
            System.out.println(" > " + moveStr); 
            
            // Rook Logic Check
            if (m.pieceMoved == Piece.WHITE_ROOK) {
                if (m.targetSquare == 8 || m.targetSquare == 16) {
                    System.out.println("❌ FAIL: Rook moved through friendly blocker! (" + moveStr + ")");
                    rookBlocked = false;
                }
            }
            
            // Pawn Logic Check
            if (m.pieceMoved == Piece.WHITE_PAWN) {
                if (moveStr.equals("e2e3")) pawnPushFound = true;
                if (moveStr.equals("e2e4")) pawnDoublePushFound = true;
                if (moveStr.equals("e2d3")) {
                    System.out.println("✅ PAWN CAPTURE FOUND: " + moveStr);
                    pawnCaptureFound = true;
                }
            }
        }

        System.out.println("\n--- FINAL REPORT ---");
        if (rookBlocked) System.out.println("✅ ROOK: Respects friendly blockers.");
        if (pawnPushFound) System.out.println("✅ PAWN: Single push working.");
        if (pawnDoublePushFound) System.out.println("✅ PAWN: Double push working.");
        if (pawnCaptureFound) System.out.println("✅ PAWN: Diagonal capture working.");
        
        if (rookBlocked && pawnPushFound && pawnDoublePushFound && pawnCaptureFound) {
            System.out.println("\n🎉 SYSTEM ALL GREEN. ENGINE IS LOGICALLY SOUND.");
        } else {
            System.out.println("\n⚠️ SYSTEM FAILURE. CHECK LOGS ABOVE.");
        }
    }
}