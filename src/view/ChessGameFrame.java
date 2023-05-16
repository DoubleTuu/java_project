package view;
import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    public static int ONE_CHESS_SIZE;
    private GameController xx;
    public static int Rounds=2;
    private static JTextField roundsButton = new JTextField("Rounds:1");
    private static JTextField currentColor = new JTextField("Turn: Blue");

    private ChessboardComponent chessboardComponent;

    public ChessGameFrame(int width, int height) {
        setTitle("Welcome To Jungle"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addRoundButton();
        addPlayerButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public void  setRounds(int rounds){
        Rounds = rounds;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5 + ONE_CHESS_SIZE, HEIGTH / 10);
        add(chessboardComponent);
    }
    private void addRoundButton(){
        roundsButton.setBounds(ONE_CHESS_SIZE/2, ONE_CHESS_SIZE*3/2, ONE_CHESS_SIZE*2, ONE_CHESS_SIZE);
        roundsButton.setFont(new Font("Arial", Font.BOLD, 25));
        roundsButton.setEditable(false);
        add(roundsButton);
    }
    private void addPlayerButton(){
        currentColor.setBounds(ONE_CHESS_SIZE/2, ONE_CHESS_SIZE*3, ONE_CHESS_SIZE*2, ONE_CHESS_SIZE);
        currentColor.setFont(new Font("Arial", Font.BOLD, 25));
        currentColor.setEditable(false);
        currentColor.setForeground(Color.BLUE);
        add(currentColor);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("欢迎来到斗兽棋");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    public static void setRounds(){
        roundsButton.setText("Rounds:"+Rounds);
        Rounds++;
    }
    public static void setTurn(){
        if(currentColor.getText().equals("Turn: Blue"))
        {
            currentColor.setText("Turn: Red");
            currentColor.setForeground(Color.RED);
        }
        else{
            currentColor.setText("Turn: Blue");
            currentColor.setForeground(Color.BLUE);
        }
    }


//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}
