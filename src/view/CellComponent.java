//package view;
//
//import javax.swing.*;
//import java.awt.*;
//
///**
// * This is the equivalent of the Cell class,
// * but this class only cares how to draw Cells on ChessboardComponent
// */
//
//public class CellComponent extends JComponent {
//    private Color background;
//
//
//    public CellComponent(Color background, Point location, int size)
//    {
//        setLayout(new GridLayout(1,1));
//        setLocation(location);
//        setSize(size, size);
//        this.background = background;
//    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponents(g);
//        g.setColor(background);
//        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
//    }
//}
package view;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.ImageIcon;

public class CellComponent extends JComponent {
    private Image image;
    int x;
    int y;
    int width;
    int height;

    public CellComponent(Image image, int x, int y, int width, int height) {
        this.image = image;//.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBounds(x, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(image, 0,0, this);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            // Load the image from the resource folder
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}