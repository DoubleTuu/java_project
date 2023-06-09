package view;
import controller.GameController;
import model.Chessboard;
import user.UserFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.MalformedParametersException;

import static view.aiFrame.AIframe;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    public static ChessGameFrame mainFrame;
    private final int WIDTH;
    private final int HEIGTH;

    int ttt=0;
    public static int ONE_CHESS_SIZE;
    private GameController xx;
    public static int Rounds=2;
    public static int musictimes=0;
    public Clip clip;
    public JTextField roundsButton = new JTextField("Rounds:1");
    public JTextField currentColor = new JTextField("Turn: Blue");
    public JButton aiModeButton=new JButton();
    public JButton classicModeButton=new JButton();
    public JButton exitButton =new JButton();
    public JButton musicButton=new JButton();
    public static JButton userButton = new JButton("用户");
    public JLabel bg = new JLabel();
    public JLabel statusLabel= new JLabel();
    public BackgroundPanel menubackGround = new BackgroundPanel(new ImageIcon("resource\\background.jpg").getImage());
    public ChessboardComponent chessboardComponent;

//    private static JLabel blackScoreLabel = new JLabel();

    public ChessGameFrame(int width, int height) {
        setTitle("Welcome To Jungle"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        initiailUI();
    }
    public void addMusic() throws MalformedParametersException
    {
        try
        {
            File musicPath = new File("resource\\music\\Travelers encore.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public void addclassicModeButton()
    {
        classicModeButton = new JButton("经典模式");
        classicModeButton.setLocation(810, 810/10 );
        classicModeButton.setSize(200, 60);
        classicModeButton.setBackground(new Color(245, 226, 178));
        classicModeButton.setFont(new Font("华文行楷", Font.BOLD,40));
        classicModeButton.setBorderPainted(false);
        add(classicModeButton);
        classicModeButton.addActionListener(e ->
        {
            repaint();
            dispose();
            new ChessGameFrame(1024, 1024);
        });
    }
    public void addUserButton(){
        userButton.setLocation(810, 810/10*5);
        userButton.setSize(200, 60);
        userButton.setBackground(new Color(245, 226, 178));
        userButton.setFont(new Font("华文行楷", Font.BOLD,40));
        userButton.setBorderPainted(false);
        add(userButton);

        userButton.addActionListener(e -> {
            UserFrame userFrame=new UserFrame(chessboardComponent);
            userFrame.setVisible(true);
        });
    }
    public void addaiModeButton()
    {
        aiModeButton = new JButton("人机模式");
        aiModeButton.setLocation(810, 810/10*1 );
        aiModeButton.setSize(200, 60);
        aiModeButton.setBackground(new Color(245, 226, 178));
        aiModeButton.setFont(new Font("华文行楷", Font.BOLD,40));
        aiModeButton.setBorderPainted(false);
        add(aiModeButton);
        aiModeButton.addActionListener(e ->
        {
            System.out.println("Start AIMode");
            AIframe=new aiFrame();
            AIframe.setVisible(true);
            repaint();
//            dispose();
        });
    }
    public void initiailUI()
    {
        setmenuBackground();
        addaiModeButton();
        addUserButton();
//        JButton functionbutton = new JButton("功能");

        classicModeButton = new JButton("经典模式");
        classicModeButton.setLocation(810, 810/10*2 );
        classicModeButton.setSize(200, 60);
        classicModeButton.setBackground(new Color(245, 226, 178));
        classicModeButton.setFont(new Font("华文行楷", Font.BOLD,40));
        classicModeButton.setBorderPainted(false);
        add(classicModeButton);

        exitButton = new JButton("退出");
        exitButton.setLocation(810, 810/10*4 );
        exitButton.setSize(200, 60);
        exitButton.setBackground(new Color(245, 226, 178));
        exitButton.setFont(new Font("华文行楷", Font.BOLD,40));
        exitButton.setBorderPainted(false);
        this.add(exitButton);

        musicButton = new JButton("音乐");
        musicButton.setLocation(810, 810 / 10*3);
        musicButton.setSize(200, 60);
        musicButton.setBackground(new Color(245, 226, 178));
        musicButton.setFont(new Font("华文行楷", Font.BOLD,40));
        musicButton.setVisible(true);
        musicButton.setBorderPainted(false);
        add(musicButton);
        exitButton.addActionListener(e ->
        {
            repaint();
            System.exit(0);
            repaint();
        });

        classicModeButton.addActionListener(e ->
        {
            System.out.println("Start Classic Mode");
//            remove(menubackGround);
//            remove(classicModeButton);
//            remove(exitButton);
//            remove(musicButton);
//            GameController.functionbutton.setVisible(true);
            aiModeButton.setVisible(false);
            menubackGround.setVisible(false);
            classicModeButton.setVisible(false);
            exitButton.setVisible(false);
            musicButton.setVisible(false);
            userButton.setVisible(false);
            repaint();
            classicMode();
            repaint();
        });
        musicButton.addActionListener(e ->
        {
            try {
                musictimes++;
                if(musictimes%2==1)
                {
                    addMusic();
                }
                else
                {
                    clip.stop();
                }
            } catch (MalformedParametersException malformedParametersException) {
                malformedParametersException.printStackTrace();
            }
        });
        addChessboard();
        addChessboardbackground();
    }
    public ChessboardComponent getChessboardComponent() //存档要用
    {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) //读档要用
    {
        this.chessboardComponent = chessboardComponent;
    }
    public void classicMode()
    {
        ttt++;
        if(ttt==1)
        {
            GameController.gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
            GameController.gameController.resetbutton.setVisible(true);
            GameController.gameController.functionbutton.setVisible(true);
            GameController.gameController.backbutton.setVisible(true);
            addLabel();//欢迎来到斗兽棋
            addRoundButton();//rounds
            addPlayerButton();//who turn
        }
        else
        {
            GameController.gameController.resetbutton.setVisible(true);
            GameController.gameController.functionbutton.setVisible(true);
            GameController.gameController.backbutton.setVisible(true);
            this.statusLabel.setVisible(true);
            this.roundsButton.setVisible(true);
            this.currentColor.setVisible(true);
        }
    }
    public void setmenuBackground()
    {

        Container ct = this.getContentPane();
        menubackGround.setBounds(0,0,1100,810);
        ct.add(menubackGround);
    }
    public void addChessboardbackground()
    {
        bg = new JLabel();
        bg.setBounds(0,0,1100,810);
        ImageIcon icon = new ImageIcon("resource\\background3.jpg");
        Image img = icon.getImage().getScaledInstance(bg.getWidth(), bg.getHeight(), Image.SCALE_SMOOTH);
        bg.setIcon(new ImageIcon(img));
        this.getLayeredPane().add(bg,Integer.valueOf(Integer.MIN_VALUE));
        this.add(bg);
        bg.setVisible(true);
    }
    public void  setRounds(int rounds){
        Rounds = rounds;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard()
    {
        //把棋盘设置到最顶层
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5 + ONE_CHESS_SIZE, HEIGTH / 10);
        add(chessboardComponent);
    }


    public void addRoundButton()
    {
        roundsButton.setBounds(ONE_CHESS_SIZE/2, ONE_CHESS_SIZE*3/2, ONE_CHESS_SIZE*2, ONE_CHESS_SIZE);
        roundsButton.setFont(new Font("Arial", Font.BOLD, 25));
        roundsButton.setEditable(false);
        roundsButton.setVisible(true);
        add(roundsButton);
    }
    public void addPlayerButton()
    {
        currentColor.setBounds(ONE_CHESS_SIZE/2, ONE_CHESS_SIZE*3, ONE_CHESS_SIZE*2, ONE_CHESS_SIZE);
        currentColor.setFont(new Font("Arial", Font.BOLD, 25));
        currentColor.setEditable(false);
        currentColor.setForeground(Color.BLUE);
        currentColor.setVisible(true);
        add(currentColor);
    }

    /**
     * 在游戏面板中添加标签
     */
    public void addLabel() {
         statusLabel = new JLabel("欢迎来到斗兽棋");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    public  void setRounds()
    {
        roundsButton.setText("Rounds:"+Rounds);
        Rounds++;
    }
    public void setTurn()
    {
        if(currentColor.getText().equals("Turn: Blue"))
        {
            currentColor.setText("Turn: Red");
            currentColor.setForeground(Color.RED);
        }
        else
        {
            currentColor.setText("Turn: Blue");
            currentColor.setForeground(Color.BLUE);
        }
    }
    public void setcurrentplayer()
    {
        currentColor.setText("Turn: Blue");
    }
    public  void initTurn(){
        currentColor.setText("Turn: Blue");
        currentColor.setForeground(Color.BLUE);
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
