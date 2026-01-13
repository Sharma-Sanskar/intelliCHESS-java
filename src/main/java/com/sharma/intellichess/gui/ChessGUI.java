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

public class ChessGUI extends JFrame {

    private final Board board;
    private final JPanel boardPanel;
    private final JTextArea logArea;
    private final JLabel[] squareLabels = new JLabel[64];

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
    private static final Color HIGHLIGHT_COLOR = new Color(106, 176, 76); // Greenish
    private static final Color MOVE_DEST_COLOR = new Color(106, 176, 76, 100); // Transparent Green

    public ChessGUI() {
        this.board = new Board();
        
        // Load Assets
        loadAssets();
        
        // Load Game State
        FenUtility.loadFen(board, START_FEN);

        setTitle("IntelliChess - Pro Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Board Panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        initBoardSquares();
        add(boardPanel, BorderLayout.CENTER);

        // 2. Sidebar
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(300, 700));
        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setText("--- INTELLICHESS SYSTEM ---\nAssets Loaded: " + useImages + "\nEngine Ready.\n");
        sidePanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        // Reset Button
        JButton btnReset = new JButton("RESET BOARD");
        btnReset.addActionListener(e -> {
            FenUtility.loadFen(board, START_FEN);
            selectedSquare = -1;
            legalMovesForSelectedPiece.clear();
            logArea.append(">> Board Reset.\n");
            refreshBoard();
        });
        sidePanel.add(btnReset, BorderLayout.SOUTH);

        add(sidePanel, BorderLayout.EAST);

        refreshBoard();
        setVisible(true);
    }

    private void loadAssets() {
        String[] whiteNames = {"wP", "wN", "wB", "wR", "wQ", "wK"};
        String[] blackNames = {"bP", "bN", "bB", "bR", "bQ", "bK"};

        try {
            for (int i = 0; i < 6; i++) {
                // Load White (IDs 0-5)
                String wPath = "assets/" + whiteNames[i] + ".png";
                pieceImages[i] = new ImageIcon(wPath).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

                // Load Black (IDs 6-11)
                String bPath = "assets/" + blackNames[i] + ".png";
                pieceImages[i + 6] = new ImageIcon(bPath).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            }
            useImages = true;
        } catch (Exception e) {
            System.err.println("Could not load images. Using Unicode fallback.");
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
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleSquareClick(visualIndex);
                }
            });
        }
    }
    
    private int visualToEngine(int visualIndex) {
        int row = visualIndex / 8;
        int col = visualIndex % 8;
        int rank = 7 - row;
        return rank * 8 + col;
    }

    private void handleSquareClick(int visualIndex) {
        int engineSquare = visualToEngine(visualIndex);
        
        // 1. Check if clicking a move target
        if (selectedSquare != -1) {
            for (Move m : legalMovesForSelectedPiece) {
                if (m.targetSquare == engineSquare) {
                    executeMove(m);
                    return;
                }
            }
        }

        // 2. Selecting a Piece
        int pieceAtSquare = board.squarePiece[engineSquare];
        if (pieceAtSquare != Piece.NONE) {
            boolean isWhitePiece = Piece.isWhite(pieceAtSquare);
            if (isWhitePiece == board.whiteToMove) {
                selectedSquare = engineSquare;
                generateLegalMovesForSelection();
                refreshBoard();
                return;
            }
        }

        // 3. Deselect
        selectedSquare = -1;
        legalMovesForSelectedPiece.clear();
        refreshBoard();
    }

    private void generateLegalMovesForSelection() {
        legalMovesForSelectedPiece.clear();
        List<Move> allMoves = MoveGenerator.generateMoves(board);
        
        for (Move m : allMoves) {
            if (m.sourceSquare == selectedSquare) {
                legalMovesForSelectedPiece.add(m);
            }
        }
        logArea.append("Selected " + BitboardUtils.bitToSquare(selectedSquare) + 
                       ". Options: " + legalMovesForSelectedPiece.size() + "\n");
    }

    private void executeMove(Move m) {
        // Manual Move Update
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

            // Colors
            int rank = engineSquare / 8;
            int file = engineSquare % 8;
            boolean isLight = (rank + file) % 2 != 0;
            lbl.setBackground(isLight ? LIGHT_SQUARE : DARK_SQUARE);

            if (engineSquare == selectedSquare) lbl.setBackground(HIGHLIGHT_COLOR);
            
            for (Move m : legalMovesForSelectedPiece) {
                if (m.targetSquare == engineSquare) lbl.setBackground(MOVE_DEST_COLOR);
            }

            // Draw Piece (Image vs Unicode)
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}