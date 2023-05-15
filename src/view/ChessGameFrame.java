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
    private static JTextField tfCount = new JTextField("Rounds:1");
    private static JTextField currentColor = new JTextField("Turn: Blue");

    public static JLabel trap;

    public static Image xuebao1=  Toolkit.getDefaultToolkit().getImage("java_project/resource/xuebao.jpeg");
    public static Image trap1=  Toolkit.getDefaultToolkit().getImage("java_project/resource/trap.jpeg");
    public static Image shouxue1=  Toolkit.getDefaultToolkit().getImage("java_project/resource/shouxue.png");
    public static Image dingzhen1=  Toolkit.getDefaultToolkit().getImage("java_project/resource/dingzhen.png");
//    Image trap1 = null;
    private ChessboardComponent chessboardComponent;

    static class ImagePanel extends JPanel
    {
        private Image image = null;

        public void paintImage(Image image)
        {
            this.image = image;
            this.repaint();
        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if (image == null)
            {
                System.out.println("Image is null");
            }
            else
            {
                System.out.println("Image width: " + image.getWidth(null) + ", height: " + image.getHeight(null));
                System.out.println(this.getWidth());
                g.drawImage(image,0 , 0, this.getWidth(), this.getHeight(), null);
                setVisible(true);
            }
        }
    }
    public ChessGameFrame(int width, int height) {
        setTitle("Welcome To Jungle"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


//        setComponentZOrder(this, 1);
        addChessboard();

        ImagePanel imagePanel1 = new ImagePanel();
        imagePanel1.setBounds(ONE_CHESS_SIZE*21/2, ONE_CHESS_SIZE*9/2,ONE_CHESS_SIZE,ONE_CHESS_SIZE);
        imagePanel1.paintImage(xuebao1);
        add(imagePanel1);
//        setVisible(true);
//        repaint();
//        ImagePanel imagePanel2 = new ImagePanel();
//        imagePanel2.setBounds(ONE_CHESS_SIZE*21/2, ONE_CHESS_SIZE*9/2,ONE_CHESS_SIZE,ONE_CHESS_SIZE);
//        imagePanel2.paintImage(dingzhen1);
//        add(imagePanel2);
//        setVisible(true);
//        repaint();
//        trap = new JLabel();
////        trap.setIcon(new ImageIcon("java_project/resource/Elephant-red.png"));
////        JPanel panel =new JPanel();
////        panel.add(trap);
////        add(panel);
////        setVisible(true);

        addLabel();
//        addResetButton();
        tfCount.setBounds(ONE_CHESS_SIZE/2, ONE_CHESS_SIZE*3/2, ONE_CHESS_SIZE*2, ONE_CHESS_SIZE);
        tfCount.setFont(new Font("Arial", Font.BOLD, 25));
        tfCount.setEditable(false);
        add(tfCount);
        currentColor.setBounds(ONE_CHESS_SIZE/2, ONE_CHESS_SIZE*3, ONE_CHESS_SIZE*2, ONE_CHESS_SIZE);
        currentColor.setFont(new Font("Arial", Font.BOLD, 25));
        currentColor.setEditable(false);
        currentColor.setForeground(Color.BLUE);
        add(currentColor);
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

    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.addActionListener((e) -> {
            UIManager.put("OptionPane.yesButtonText", "Yes");
            UIManager.put("OptionPane.noButtonText", "No");
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset?", "Reset", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),mainFrame);
                mainFrame.setVisible(true);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
//    private void addrounds() {
//        JLabel gg = new JLabel("Rounds:"+String.valueOf(Rounds));
//        gg.setLocation(HEIGTH, HEIGTH / 2);
//        gg.setSize(200, 60);
//        gg.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(gg);
//    }
    public static void setRounds(){
        tfCount.setText("Rounds:"+Rounds);
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
