package view;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    Image image;
    public BackgroundPanel(Image image){
        this.image = image;
        this.setOpaque(true);
    }
    public void paintComponent (Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }
}
