package view;

import java.util.Random;
import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;

import static view.ChessGameFrame.mainFrame;

public class aiFrame extends JFrame
{
    public static aiFrame AIframe;
    GameController gameController;
    ChessboardComponent chessboardComponent;
    Chessboard chessboard;
    public int aibegin = 0;
    boolean cheatStatus = false;
    public aiFrame()
    {
//        this.gameController = gameController;
//        this.chessboardComponent = chessboardComponent;
//        this.chessboard = chessboard;
        this.aibegin=0;
        this.setTitle("AI");
        this.setSize(310, 350);
        this.setLocationRelativeTo(null); // Center the window.
        this.setLayout(null);
        this.addnoobButton();
        this.addeasyButton();
        this.addnormalButton();
        this.addexpertButton();
    }
    public void common()
    {
        mainFrame.aiModeButton.setVisible(false);
        mainFrame.menubackGround.setVisible(false);
        mainFrame.classicModeButton.setVisible(false);
        mainFrame.exitButton.setVisible(false);
        mainFrame.musicButton.setVisible(false);
        mainFrame.userButton.setVisible(false);
        repaint();
        AIframe.spMode();
        repaint();
    }
    public void spMode()
    {

        GameController.gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
        GameController.gameController.resetbutton.setVisible(true);
        GameController.gameController.functionbutton.setVisible(true);
        GameController.gameController.backbutton.setVisible(true);
        mainFrame.addLabel();//欢迎来到斗兽棋
        mainFrame.addRoundButton();//rounds
        mainFrame.addPlayerButton();//who turn
    }
    private void addnoobButton()//ok
    {
        JButton button = new JButton("NOOB");
        button.setLocation(20, 20);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);
        button.addActionListener(e ->
        {
            aibegin=1;
            common();
            this.dispose();
        });
    }
    private void addeasyButton() //ok
    {
        JButton button = new JButton("EASY");
        button.setLocation(20, 60);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);
        button.addActionListener(e ->{
            aibegin=2;
            common();
            spMode();
            this.dispose();
        });
    }
    private void addnormalButton() //ok
    {
        JButton button = new JButton("NORMAL");
        button.setLocation(20, 100);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);

        button.addActionListener(e -> {
            aibegin=3;
            common();
            spMode();
            this.dispose();
        });
    }
    private void addexpertButton() //ok
    {
        JButton button = new JButton("EXPERT");
        button.setLocation(20, 140);
        button.setSize(260, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.GRAY);
        add(button);
        button.addActionListener(e -> {
            aibegin=4;
            common();
            spMode();
            this.dispose();
        });
    }
    public void aimove(int level)
    {
        if(level==1)
        {
            ChessboardPoint selectedPoint;
            ChessboardPoint selectedarray[]=new ChessboardPoint[2000];
            JComponent targetarray[]=new JComponent[2000];
            int targetx[]=new int[2000];
            int targety[]=new int[2000];
            int typearray[]=new int[2000];
            int which=-1;
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<7;j++)
                {
                    selectedPoint=new ChessboardPoint(i,j);
                    ChessPiece selectedPiece=GameController.gameController.model.getChessPieceAt(selectedPoint);
                    if(selectedPiece!=null)
                    {
                        if(selectedPiece.getOwner()== PlayerColor.RED)
                        {
                            for(int ii=0;ii<9;ii++)
                            {
                                for(int jj=0;jj<7;jj++)
                                {
                                    ChessboardPoint targetPoint=new ChessboardPoint(ii,jj);
                                    ChessPiece targetPiece=GameController.gameController.model.getChessPieceAt(targetPoint);
                                    if(targetPiece!=null)
                                    {
                                        if(targetPiece.getOwner()==PlayerColor.BLUE)
                                        {
                                            if(GameController.gameController.model.isValidCapture(selectedPoint,targetPoint))
                                            {
                                                JComponent clickedComponent = (JComponent) GameController.gameController.view.gridComponents[ii][jj];
                                                which++;
                                                selectedarray[which]=selectedPoint;
                                                targetarray[which]=clickedComponent;
                                                targetx[which]=ii;
                                                targety[which]=jj;
                                                typearray[which]=1;
                                            }
                                        }
                                    }
                                    else if(GameController.gameController.model.isValidMove(selectedPoint,targetPoint))
                                    {
                                        JComponent clickedComponent = (JComponent) GameController.gameController.view.gridComponents[ii][jj];
                                        which++;
                                        selectedarray[which]=selectedPoint;
                                        targetarray[which]=clickedComponent;
                                        targetx[which]=ii;
                                        targety[which]=jj;
                                        typearray[which]=2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Random random = new Random();
            int randomNumber = random.nextInt(which+1);
            System.out.println("random");
            System.out.println(which);
            System.out.println(randomNumber);
            selectedPoint=selectedarray[randomNumber];
            ChessboardPoint ttargetPoint=new ChessboardPoint(1,1);
            System.out.println(GameController.gameController.model.isValidMove(selectedPoint,ttargetPoint));
            if(selectedarray[randomNumber]==null)
            {
                System.out.println("null"+" "+which+" "+randomNumber);
                for(int i=0;i<9;i++)
                {
                    for(int j=0;j<7;j++)
                    {
                        ChessboardPoint targetPoint=new ChessboardPoint(i,j);
                        ChessPiece targetPiece=GameController.gameController.model.getChessPieceAt(targetPoint);
                        if(targetPiece!=null)
                        {
                            System.out.println(targetPiece.getName()+" "+targetPiece.getOwner()+" "+i+" "+j);
                        }
                    }
                }
            }
            System.out.println("type"+typearray[randomNumber]);
            System.out.println(selectedarray[randomNumber].getRow());
            System.out.println(selectedarray[randomNumber].getCol());
            System.out.println(targetx[randomNumber]);
            System.out.println(targety[randomNumber]);
            if(typearray[randomNumber]==1)
            {
                System.out.println("AI1");
                ChessboardPoint point=new ChessboardPoint(targetx[randomNumber],targety[randomNumber]);
                if(point==null)
                {
                    System.out.println();
                }
                AnimalChessComponent temp=GameController.gameController.view.removeChessComponentAtGrid(point);
                GameController.gameController.model.captureChessPiece(selectedarray[randomNumber], point);
                GameController.gameController.view.getGridComponentAt(point).removeAll();
                GameController.gameController.view.setChessComponentAtGrid(point, GameController.gameController.view.removeChessComponentAtGrid(selectedarray[randomNumber]));
//                GameController.gameController.onPlayerClickChessPiece(selectedarray[randomNumber], (AnimalChessComponent) targetarray[which].getComponents()[0]);
//                repaint();
            }
            else if(typearray[randomNumber]==2)
            {
                System.out.println("AI2");
                ChessboardPoint point=new ChessboardPoint(targetx[randomNumber],targety[randomNumber]);
                GameController.gameController.model.moveChessPiece(selectedarray[randomNumber], point);
                GameController.gameController.view.setChessComponentAtGrid(point, GameController.gameController.view.removeChessComponentAtGrid(selectedarray[randomNumber]));
//                GameController.gameController.swapColor();
//                GameController.gameController.onPlayerClickCell(selectedarray[randomNumber], (CellComponent) targetarray[which]);
//                repaint();
            }
        }
        else
        {

        }
    }
}
