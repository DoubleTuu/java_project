package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Date;
import controller.GameController.*;

public class SaveAndLoadFrame extends JFrame
{
    public static int turnturn=0;
    public static int turn=1;
    public static Cell[][] map1;
    public static StringBuilder ans=new StringBuilder();
    public static String where;
    boolean SaveOrLoad; //true:save false:load
    public GameController gameController;
    FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name)
        {
            return name.endsWith(".txt");
        }
    };
    public Boolean check(String name)
    {
        if(name.equals("Elephant")||name.equals("Lion")||name.equals("Mouse")||name.equals("Tiger")||name.equals("Wolf")||name.equals("Leopard")||name.equals("Dog")||name.equals("Cat"))
            return true;
        else
            return false;

    }
    public SaveAndLoadFrame (boolean SaveOrLoad, GameController gameController) throws HeadlessException
    {
        this.SaveOrLoad = SaveOrLoad;
        this.gameController= gameController;
        setSize(510, 600);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        addSaveAndLoadChoice("resource\\file\\save1.json",30,10,1);
        addSaveAndLoadChoice("resource\\file\\save2.txt",30,50,2);
        addSaveAndLoadChoice("resource\\file\\save3.txt",30,90,3);
        addSaveAndLoadChoice("resource\\file\\save4.txt",30,130,4);
        addSaveAndLoadChoice("resource\\file\\save5.txt",30,170,5);
        addSaveAndLoadChoice("resource\\file\\save6.txt",30,210,6);
        addSaveAndLoadChoice("resource\\file\\save7.txt",30,250,7);
        addSaveAndLoadChoice("resource\\file\\save8.txt",30,290,8);
        addSaveAndLoadChoice("resource\\file\\save9.txt",30,330,9);
        addSaveAndLoadChoice("resource\\file\\save10.txt",30,370,10);
        addSaveAndLoadChoice("resource\\file\\save11.txt",30,410,11);
        addSaveAndLoadChoice("resource\\file\\save12.txt",30,450,12);
        addSaveAndLoadChoice("resource\\file\\save13.txt",30,490,13);
    }
    public void addSaveAndLoadChoice(String path, int x, int y, int n){
        File file = new File(path);
        JButton button = new JButton("File "+n+" "+new Date(file.lastModified()));
        button.setLocation(x,y);
        button.setSize(450, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e ->
        {
            System.out.println("Click File "+n);
            if(filter.accept(file,path)==false)
            {
                new ErrorFrame("1");
                return ;
            }
            else
            {
                if (SaveOrLoad)
                {
                    ans.insert(4,String.valueOf(turn)+' ');
                    ans.insert(4,turnturn==0?"blue ":"red ");
                    for(int i=0;i<9;i++)
                    {
                        for(int j=0;j<7;j++)
                        {
                            if(map1[i][j].getPiece()!=null)
                            {
                                Cell temp=map1[i][j];
                                ans.insert(4,String.valueOf(i)+' '+String.valueOf(j)+' ');
                                ans.insert(4,"1 ");
                                ans.insert(4,temp.getPiece().getOwner().toString()+' ');
                                ans.insert(4,temp.getPiece().getName()+' ');
                            }
                        }
                    }
                    System.out.println(ans);


                    try {
                        // 创建一个FileWriter对象
                        FileWriter writer = new FileWriter(path);

                        // 创建一个PrintWriter对象
                        PrintWriter printer = new PrintWriter(writer);

                        // 使用PrintWriter的方法将文件内容清空
                        printer.print("");

                        // 关闭PrintWriter和FileWriter对象
                        printer.close();
                        writer.close();

                        System.out.println("文件已清空");
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }


                    try
                    {
                        FileWriter writer = new FileWriter(file, true);
                        writer.write(ans.toString());
                        writer.close();
                        System.out.println("File modified successfully!");
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    ans.delete(4,ans.length()-8*(turn-1));
                }
                else
                {
                    Save_and_Load.work(path);
//                    System.out.println(Save_and_Load.turn);
                    if(Save_and_Load.Chessboard_row!=9||Save_and_Load.Chessboard_col!=7)
                    {
                        new ErrorFrame("2");
                        return ;
                    }
                    System.out.println("Load");
                    for(int i=0;i<Save_and_Load.which;i++) {
                        if (Save_and_Load.num[i] > 1 || Save_and_Load.num[i] < 0 || (check(Save_and_Load.name[i]) == false) || Save_and_Load.row[i] < 0 || Save_and_Load.row[i] > 8 || Save_and_Load.col[i] < 0 || Save_and_Load.col[i] > 6) {
                            new ErrorFrame("3");
                            return;
                        }
                    }
                    if(Save_and_Load.turn!="red"&&Save_and_Load.turn!="blue")
                    {
                        new ErrorFrame("4");
                        return ;
                    }
                    int tuen=Save_and_Load.rounds;
                    gameController.initialize();
//                    repaint();
//                    try {
//                        Thread.sleep(2000); // 暂停2秒钟
//                    } catch (InterruptedException e6) {
//                        e6.printStackTrace();
//                    }

                    for(int i=1;i<tuen;i++)
                    {
                        System.out.println("sleep");
                        //睡0.1秒
//                        try
//                        {
//                            Thread.sleep(500); // 暂停0.5秒钟
//                        }
//                        catch (InterruptedException e6)
//                        {
//                            e6.printStackTrace();
//                        }
                        if(gameController.model.grid[Integer.parseInt(Save_and_Load.step[i][1][0])][Integer.parseInt(Save_and_Load.step[i][1][1])].getPiece()==null)
                        {
                            if(gameController.model.isValidMove(new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][0][0]),Integer.parseInt(Save_and_Load.step[i][0][1])),new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][1][0]),Integer.parseInt(Save_and_Load.step[i][1][1]))))
                            {
                                ChessboardPoint selectedPoint=new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][0][0]),Integer.parseInt(Save_and_Load.step[i][0][1]));
                                ChessboardPoint point=new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][1][0]),Integer.parseInt(Save_and_Load.step[i][1][1]));
                                ans.append(selectedPoint.getRow()+" "+selectedPoint.getCol()+" "+point.getRow()+" "+point.getCol()+" ");
                                gameController.view.regretStack.push(new RegretNode(1,gameController.model.getChessPieceAt(selectedPoint),selectedPoint,gameController.model.getChessPieceAt(selectedPoint),point));
                                gameController.model.moveChessPiece(selectedPoint, point);
                                gameController.view.setChessComponentAtGrid(point, gameController.view.removeChessComponentAtGrid(selectedPoint));
                                gameController.swapColor();
                                gameController.view.repaint();
                            }
                            else
                            {
                                new ErrorFrame("5");
                                return ;
                            }
                            gameController.judgeWin();
                        }
                        else
                        {
                            if(gameController.model.isValidCapture(new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][0][0]),Integer.parseInt(Save_and_Load.step[i][0][1])),new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][1][0]),Integer.parseInt(Save_and_Load.step[i][1][1]))))
                            {
                                ChessboardPoint selectedPoint=new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][0][0]),Integer.parseInt(Save_and_Load.step[i][0][1]));
                                ChessboardPoint point=new ChessboardPoint(Integer.parseInt(Save_and_Load.step[i][1][0]),Integer.parseInt(Save_and_Load.step[i][1][1]));
                                ans.append(selectedPoint.getRow()+" "+selectedPoint.getCol()+" "+point.getRow()+" "+point.getCol()+" ");
                                AnimalChessComponent temp=gameController.view.removeChessComponentAtGrid(point);
                                gameController.view.regretStack.push(new RegretNode(2,gameController.model.getChessPieceAt(selectedPoint),selectedPoint,gameController.model.getChessPieceAt(point),point,temp));
                                gameController.model.captureChessPiece(selectedPoint, point);
                                gameController.view.getGridComponentAt(point).removeAll();
                                gameController.view.setChessComponentAtGrid(point, gameController.view.removeChessComponentAtGrid(selectedPoint));
                                gameController.swapColor();
                                gameController.view.repaint();
                            }
                            else
                            {
                                new ErrorFrame("5");
                                return ;
                            }
                            gameController.judgeWin();
                        }
                    }
                }
            }
            this.dispose();
        });
    }
}
