package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class AnimalChessComponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;

    private String str;
    public Image img ;

    public AnimalChessComponent(PlayerColor owner, int size, String str, Image image) {
        this.owner = owner;
        this.selected = false;
        this.str = str;
        img = image;
        setSize(size-6, size-6);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
//        g2.drawString(str, getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        try {
            // Load the image from the resource folder
            g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
