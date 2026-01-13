package com.sharma.intellichess.gui;

import com.sharma.intellichess.engine.Board;
import com.sharma.intellichess.engine.Move;
import com.sharma.intellichess.engine.Piece;
import com.sharma.intellichess.movegen.MoveGenerator;
import com.sharma.intellichess.bitboard.BitboardUtils;
import com.sharma.intellichess.utils.FenUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessGUI extends JFrame {

    private final Board board;
    private final JPanel boardPanel;
    private final JTextArea logArea;
    private final JLabel[] squareLabels = new JLabel[64];
    private final EvalBar evalBar; // <--- NEW COMPONENT

    // Interaction State
    private int selectedSquare = -1;
    private List<Move> legalMovesForSelectedPiece = new ArrayList<>();
    private static final String START_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    // Assets
    private final Image[] pieceImages = new Image[12];
    private boolean useImages = false;

    private static final String[] PIECE_ICONS = {
        "♙", "♘", "♗", "♖", "♕", "♔", // White
        "♟", "♞", "♝", "♜", "♛", "♚"  // Black
    };
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color HIGHLIGHT_COLOR = new Color(106, 176, 76); 
    private static final Color MOVE_DEST_COLOR = new Color(106, 176, 76, 100); 

    public ChessGUI() {
        this.board = new Board();
        loadAssets();
        FenUtility.loadFen(board, START_FEN);

        setTitle("IntelliChess - Pro Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- LEFT: EVAL BAR ---
        evalBar = new EvalBar();
        add(evalBar, BorderLayout.WEST);

        // --- CENTER: BOARD ---
        boardPanel = new JPanel(new GridLayout(8, 8));
        initBoardSquares();
        add(boardPanel, BorderLayout.CENTER);

        // --- RIGHT: SIDEBAR ---
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(300, 700));
        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setText("--- INTELLICHESS SYSTEM ---\nAssets Loaded: " + useImages + "\nEngine Ready.\n");
        sidePanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10)); 
        
        JButton btnAI = new JButton("COMPUTER MOVE (AI)");
        btnAI.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAI.setBackground(new Color(50, 50, 50));
        btnAI.setForeground(Color.BLUE);
        btnAI.setOpaque(true);
        btnAI.addActionListener(e -> makeBestMove());
        buttonPanel.add(btnAI);

        JButton btnReset = new JButton("RESET BOARD");
        btnReset.addActionListener(e -> {
            FenUtility.loadFen(board, START_FEN);
            selectedSquare = -1;
            legalMovesForSelectedPiece.clear();
            logArea.append(">> Board Reset.\n");
            evalBar.updateScore(0); // Reset Bar
            refreshBoard();
        });
        buttonPanel.add(btnReset);

        sidePanel.add(buttonPanel, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.EAST);

        refreshBoard();
        setVisible(true);
    }

    // --- THE BRAIN (Greedy Search) ---
    private void makeBestMove() {
        long start = System.nanoTime();
        List<Move> moves = MoveGenerator.generateMoves(board);
        
        if (moves.isEmpty()) {
            logArea.append("GAME OVER (No moves)\n");
            return;
        }

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : moves) {
            int currentScore = 0;
            
            // 1. Material Evaluation (Greedy Capture)
            if (move.isCapture) {
                int victimValue = getPieceValue(move.pieceCaptured);
                int attackerValue = getPieceValue(move.pieceMoved);
                // Aggressive heuristic: Gain material, risk cheap pieces
                currentScore = victimValue - (attackerValue / 10);
            } 
            
            // 2. Randomness (To avoid identical games)
            currentScore += new Random().nextInt(10);

            if (currentScore > bestScore) {
                bestScore = currentScore;
                bestMove = move;
            }
        }
        
        long time = System.nanoTime() - start;
        logArea.append("AI Eval: " + bestScore + " (Time: " + (time / 1000) + "µs)\n");
        
        // Update Bar: Need score from White's perspective
        // If it's White's turn, a high score means White is winning.
        // If it's Black's turn, a high score means Black is winning (so White is losing).
        int whitePerspectiveScore = board.whiteToMove ? bestScore : -bestScore;
        // Scale it up for visuals if it's just small capture logic
        evalBar.updateScore(whitePerspectiveScore * 5); 

        if (bestMove != null) {
            executeMove(bestMove);
        }
    }

    private int getPieceValue(int piece) {
        int type = piece % 6; 
        switch (type) {
            case 0: return 100;   // Pawn
            case 1: return 320;   // Knight
            case 2: return 330;   // Bishop
            case 3: return 500;   // Rook
            case 4: return 900;   // Queen
            case 5: return 20000; // King
            default: return 0;
        }
    }

    // --- ASSETS & GUI LOGIC ---
    private void loadAssets() {
        String[] whiteNames = {"wP", "wN", "wB", "wR", "wQ", "wK"};
        String[] blackNames = {"bP", "bN", "bB", "bR", "bQ", "bK"};
        try {
            for (int i = 0; i < 6; i++) {
                pieceImages[i] = new ImageIcon("assets/" + whiteNames[i] + ".png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                pieceImages[i + 6] = new ImageIcon("assets/" + blackNames[i] + ".png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            }
            useImages = true;
        } catch (Exception e) {
            System.err.println("Assets not found. Using Unicode.");
            useImages = false;
        }
    }

    private void initBoardSquares() {
        for (int i = 0; i < 64; i++) {
            JLabel lbl = new JLabel("", SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setFont(new Font("Serif", Font.BOLD, 50));
            squareLabels[i] = lbl;
            boardPanel.add(lbl);
            final int visualIndex = i;
            lbl.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { handleSquareClick(visualIndex); }
            });
        }
    }
    
    private int visualToEngine(int visualIndex) {
        int row = visualIndex / 8;
        int col = visualIndex % 8;
        return (7 - row) * 8 + col;
    }

    private void handleSquareClick(int visualIndex) {
        int engineSquare = visualToEngine(visualIndex);
        if (selectedSquare != -1) {
            for (Move m : legalMovesForSelectedPiece) {
                if (m.targetSquare == engineSquare) {
                    executeMove(m);
                    return;
                }
            }
        }
        int pieceAtSquare = board.squarePiece[engineSquare];
        if (pieceAtSquare != Piece.NONE && Piece.isWhite(pieceAtSquare) == board.whiteToMove) {
            selectedSquare = engineSquare;
            generateLegalMovesForSelection();
            refreshBoard();
            return;
        }
        selectedSquare = -1;
        legalMovesForSelectedPiece.clear();
        refreshBoard();
    }

    private void generateLegalMovesForSelection() {
        legalMovesForSelectedPiece.clear();
        List<Move> allMoves = MoveGenerator.generateMoves(board);
        for (Move m : allMoves) {
            if (m.sourceSquare == selectedSquare) legalMovesForSelectedPiece.add(m);
        }
        logArea.append("Selected " + BitboardUtils.bitToSquare(selectedSquare) + "\n");
    }

    private void executeMove(Move m) {
        board.squarePiece[m.sourceSquare] = Piece.NONE;
        board.bitboards[m.pieceMoved] &= ~(1L << m.sourceSquare);
        if (m.isCapture) {
            board.bitboards[m.pieceCaptured] &= ~(1L << m.targetSquare);
            logArea.append(">> Capture! \n");
        }
        board.squarePiece[m.targetSquare] = m.pieceMoved;
        board.bitboards[m.pieceMoved] |= (1L << m.targetSquare);
        selectedSquare = -1;
        legalMovesForSelectedPiece.clear();
        board.whiteToMove = !board.whiteToMove;
        logArea.append("Moved: " + m.toString() + "\n");
        refreshBoard();
    }

    private void refreshBoard() {
        for (int i = 0; i < 64; i++) {
            int engineSquare = visualToEngine(i);
            JLabel lbl = squareLabels[i];
            int rank = engineSquare / 8;
            int file = engineSquare % 8;
            lbl.setBackground((rank + file) % 2 != 0 ? LIGHT_SQUARE : DARK_SQUARE);
            if (engineSquare == selectedSquare) lbl.setBackground(HIGHLIGHT_COLOR);
            for (Move m : legalMovesForSelectedPiece) if (m.targetSquare == engineSquare) lbl.setBackground(MOVE_DEST_COLOR);
            
            int piece = board.squarePiece[engineSquare];
            if (piece != Piece.NONE) {
                if (useImages) {
                    lbl.setIcon(new ImageIcon(pieceImages[piece]));
                    lbl.setText("");
                } else {
                    lbl.setIcon(null);
                    lbl.setText(PIECE_ICONS[piece]);
                    lbl.setForeground(Piece.isWhite(piece) ? Color.WHITE : Color.BLACK);
                }
            } else {
                lbl.setIcon(null);
                lbl.setText("");
            }
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(ChessGUI::new); }
}