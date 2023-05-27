package view;

import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {
    public ErrorFrame(String num){
        setTitle("Error");
        setLayout(null);
        setLocationRelativeTo(null);
        setSize(400,120);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Label label = new Label();
        switch (num) {
            case "1" -> label.setText("101：wrong suffix!");
            case "2" -> label.setText("102: wrong chessboard!");
            case "3" -> label.setText("103: wrong component!");
            case "4" -> label.setText("104: missing player!");
            case "5" -> label.setText("105: wrong move!");
            default  -> label.setText("can't regret before first move!");
        }

        label.setSize(400,60);
        label.setFont(new Font("华文行楷", Font.BOLD, 20));
        label.setLocation(20,11);
        add(label);

        this.setVisible(true);

        try  { Thread.sleep(1500); }
        catch (InterruptedException e) { throw new RuntimeException(e);  }
        dispose();
    }
}
