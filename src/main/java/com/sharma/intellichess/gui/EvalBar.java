package com.sharma.intellichess.gui;

import javax.swing.*;
import java.awt.*;

public class EvalBar extends JPanel {

    private int currentScore = 0; // Centipawns (White perspective)
    // +2000 means White is winning by a lot (Full bar)
    // -2000 means Black is winning by a lot (Empty bar)
    private static final int MAX_ADVANTAGE = 2000; 

    public EvalBar() {
        this.setPreferredSize(new Dimension(25, 0)); // Thin vertical bar
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        this.setBackground(new Color(20, 20, 20)); // Dark background
    }

    public void updateScore(int score) {
        this.currentScore = score;
        repaint(); // Trigger a redraw
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = getWidth();
        int h = getHeight();

        // Calculate percentage (0.0 to 1.0)
        double percent = 0.5 + ((double)currentScore / (MAX_ADVANTAGE * 2));
        
        // Clamp it (Don't go off screen)
        if (percent > 1.0) percent = 1.0;
        if (percent < 0.0) percent = 0.0;

        int whiteHeight = (int) (h * percent);

        // 1. Draw Black Background (Full)
        g.setColor(new Color(40, 40, 40)); 
        g.fillRect(0, 0, w, h);

        // 2. Draw White Bar (Growing from Bottom)
        g.setColor(new Color(220, 220, 220));
        g.fillRect(0, h - whiteHeight, w, whiteHeight);
        
        // 3. Draw Center Line (Equality Marker)
        g.setColor(Color.RED);
        g.fillRect(0, h/2 - 1, w, 2); // 2px thick line
    }
}