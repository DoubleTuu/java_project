package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame
{
    GameController gameController;
    ChessboardComponent chessboardComponent;
    Chessboard chessboard;
    boolean cheatStatus = false;
    public MenuFrame(ChessboardComponent chessboardComponent,Chessboard chessboard,GameController gameController)
    {
        this.gameController = gameController;
        this.chessboardComponent = chessboardComponent;
        this.chessboard = chessboard;
        setTitle("Menu");
        setSize(310, 350);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addContinueButton();
        addRegretButton();
        addCheatButton();
        addSavaButton();
        addLoadButton();
        addQuitButton();
    }
    private void addContinueButton()//ok
    {
        JButton button = new JButton("Continue");
        button.setLocation(20, 20);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);
        button.addActionListener(e -> this.dispose());
    }
    private void addRegretButton() //ok
    {
        JButton button = new JButton("Regret");
        button.setLocation(20, 60);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e ->chessboardComponent.regret(chessboard));
    }
    private void addCheatButton() //ok
    {
        JButton button = new JButton("Cheat");
        button.setLocation(20, 100);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            GameController.cheat++;
            dispose();
        });
    }

    private void addSavaButton()//no
    {
        JButton button = new JButton("Save");
        button.setLocation(20, 140);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Saving game");
            SaveAndLoadFrame saveAndLoadFrame = new SaveAndLoadFrame(true,gameController);
            saveAndLoadFrame.setVisible(true);
            dispose();
        });
    }
    private void addLoadButton() //no
    {
        JButton button = new JButton("Load");
        button.setLocation(20, 180);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            SaveAndLoadFrame saveAndLoadFrame = new SaveAndLoadFrame(false,gameController);
            saveAndLoadFrame.setVisible(true);
            dispose();
        });
    }
    private void addQuitButton() //ok
    {
        JButton button = new JButton("Quit");
        button.setLocation(20, 260);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);
        button.addActionListener(e -> {
            System.exit(0);
        });
    }
}
