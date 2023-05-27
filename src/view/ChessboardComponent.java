package view;


import controller.GameController;
import model.*;
import view.AnimalChessComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;
import static view.ChessGameFrame.*;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JLabel
{
    public CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapBlue = new HashSet<>();
    private final Set<ChessboardPoint> trapRed = new HashSet<>();
    private final Set<ChessboardPoint> homeBlue = new HashSet<>();
    private final Set<ChessboardPoint> homeRed = new HashSet<>();
    private GameController gameController;

    public Stack<RegretNode> regretStack = new Stack<>();
    public ChessboardComponent(int chessSize) {
        this.setOpaque(false);
        this.setLayout(null);
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
//        setLayout(null); // Use absolute layout.
        setSize(width, height);
//        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        this.setVisible(true);
        initiateGridComponents();
    }
    public void regret(Chessboard chessboard)
    {
        GameController.gameController.remove_Footprint();
        if(regretStack.isEmpty())
        {
            new ErrorFrame("6");
        }
        else
        {

            RegretNode regretNode = regretStack.pop();
            if(regretNode.type==1)//移动
            {
                chessboard.moveChessPiece(regretNode.chessboardPoint2,regretNode.chessboardPoint1);
                setChessComponentAtGrid(regretNode.chessboardPoint1, removeChessComponentAtGrid(regretNode.chessboardPoint2));
                repaint();
            }
            else//吃子
            {
                chessboard.removeChessPiece(regretNode.chessboardPoint1);
                chessboard.removeChessPiece(regretNode.chessboardPoint2);
                chessboard.setChessPiece(regretNode.chessboardPoint1,regretNode.chessPiece1);
                chessboard.setChessPiece(regretNode.chessboardPoint2,regretNode.chessPiece2);
                AnimalChessComponent c2= removeChessComponentAtGrid(regretNode.chessboardPoint2);
                setChessComponentAtGrid(regretNode.chessboardPoint1,c2);
                setChessComponentAtGrid(regretNode.chessboardPoint2,regretNode.chessComponent2);
                repaint();
            }
        }
    }
    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        trapRed.add(new ChessboardPoint(0,2));
        trapRed.add(new ChessboardPoint(0,4));
        trapRed.add(new ChessboardPoint(1,3));

        trapBlue.add(new ChessboardPoint(8,2));
        trapBlue.add(new ChessboardPoint(8,4));
        trapBlue.add(new ChessboardPoint(7,3));

        homeRed.add(new ChessboardPoint(0,3));
        homeBlue.add(new ChessboardPoint(8,3));
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++)
        {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++)
            {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp))
                {
                    cell = new CellComponent(new ImageIcon("resource\\River.png").getImage(),
                            j*CHESS_SIZE+3,i*CHESS_SIZE+3,CHESS_SIZE-6,CHESS_SIZE-6);
                    this.add(cell);
                }
                else if(trapBlue.contains(temp) || trapRed.contains(temp) )
                {
                    cell = new CellComponent(new ImageIcon("resource\\Trap.png").getImage(),
                            j*CHESS_SIZE+3,i*CHESS_SIZE+3,CHESS_SIZE-6,CHESS_SIZE-6);
//                    cell.label.setIcon(new ImageIcon("java_project/resource/trap.jpeg"));
                    this.add(cell);
                }
                else if(homeRed.contains(temp))
                {
                    cell = new CellComponent(new ImageIcon("resource\\HomeRed.png").getImage(),
                            j*CHESS_SIZE+3,i*CHESS_SIZE+3,CHESS_SIZE-6,CHESS_SIZE-6);
//                    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Elephant-red.png")));
//                    cell.image.setIcon(icon);
                    this.add(cell);
                }
                else if(homeBlue.contains(temp))
                {
                    cell = new CellComponent(new ImageIcon("resource\\HomeBlue.png").getImage(),
                            j*CHESS_SIZE+3,i*CHESS_SIZE+3,CHESS_SIZE-6,CHESS_SIZE-6);
//                    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Elephant-red.png")));
//                    cell.image.setIcon(icon);
                    this.add(cell);
                }
                else
                {
                    cell = new CellComponent(new ImageIcon("resource\\Normal.png").getImage(),
                            j*CHESS_SIZE+3,i*CHESS_SIZE+3,CHESS_SIZE-6,CHESS_SIZE-6);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }

    }

    public void initiateChessComponent(Chessboard chessboard)
    {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++)
        {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++)
            {
                // TODO: Implement the initialization checkerboard

                gridComponents[i][j].removeAll();
                if (grid[i][j].getPiece() != null)
                {
                    ChessPiece chessPiece = grid[i][j].getPiece();
//                    System.out.println(chessPiece.getOwner());
                    if (grid[i][j].getPiece().getName()=="Elephant" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "象",
                                        new ImageIcon("resource\\ElephantBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Elephant" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "象",
                                        new ImageIcon("resource\\ElephantRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Lion" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狮",
                                        new ImageIcon("resource\\LionRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Lion" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狮",
                                        new ImageIcon("resource\\LionBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Tiger" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "虎",
                                        new ImageIcon("resource\\TigerBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Tiger" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "虎",
                                        new ImageIcon("resource\\/TigerRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Leopard" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "豹",
                                        new ImageIcon("resource\\LeopardRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Leopard" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "豹",
                                        new ImageIcon("resource\\LeopardBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Wolf" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狼",
                                        new ImageIcon("resource\\WolfBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Wolf" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狼",
                                        new ImageIcon("resource\\WolkRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Dog" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狗",
                                        new ImageIcon("resource\\DogBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Dog" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狗",
                                        new ImageIcon("resource\\DogRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Cat" & chessPiece.getOwner().equals(PlayerColor.BLUE)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "猫",
                                        new ImageIcon("resource\\CatBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Cat" & chessPiece.getOwner().equals(PlayerColor.RED)) {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "猫",
                                        new ImageIcon("resource\\CatRed.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Mouse" & chessPiece.getOwner().equals(PlayerColor.BLUE))
                    {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "鼠",
                                        new ImageIcon("resource\\MouseBlue.png").getImage()));
                    }
                    else if (grid[i][j].getPiece().getName()=="Mouse" & chessPiece.getOwner().equals(PlayerColor.RED))
                    {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "鼠",
                                        new ImageIcon("resource\\MouseRed.png").getImage()));
                    }
                }
            }
        }
    }

    public void registerController(GameController gameController)
    {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, AnimalChessComponent chess)
    {
        getGridComponentAt(point).add(chess);
    }

    public AnimalChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
//        System.out.println("remove");
        AnimalChessComponent chess=(AnimalChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    public Set<ChessboardPoint> getRiverCell(){
        return riverCell;
    }
    public Set<ChessboardPoint> getTrapBlue(){
        return trapBlue;
    }
    public Set<ChessboardPoint> getTrapRed(){
        return trapRed;
    }
    public Set<ChessboardPoint> getHomeBlue(){
        return homeBlue;
    }
    public Set<ChessboardPoint> getHomeRed(){
        return homeRed;
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e)
    {
        if (e.getID() == MouseEvent.MOUSE_PRESSED)
        {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0)
            {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            }
            else
            {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (AnimalChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
}
