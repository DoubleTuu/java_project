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

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapBlue = new HashSet<>();
    private final Set<ChessboardPoint> trapRed = new HashSet<>();
    private final Set<ChessboardPoint> homeBlue = new HashSet<>();
    private final Set<ChessboardPoint> homeRed = new HashSet<>();
    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
//        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
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
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                else if(trapBlue.contains(temp) || trapRed.contains(temp) )
                {
                    cell = new CellComponent(Color.orange,calculatePoint(i,j),CHESS_SIZE);
                    this.add(cell);
                }
                else if(homeBlue.contains(temp) || homeRed.contains(temp))
                {
                    cell = new CellComponent(Color.PINK,calculatePoint(i,j),CHESS_SIZE);
//                    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Elephant-red.png")));
//                    cell.image.setIcon(icon);
                    this.add(cell);
                }
                else
                {
                    cell = new CellComponent(Color.white, calculatePoint(i, j), CHESS_SIZE);
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

                if (grid[i][j].getPiece() != null)
                {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    if (grid[i][j].getPiece().getName()=="Elephant") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "象"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Lion") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狮"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Tiger") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "虎"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Leopard") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "豹"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Wolf") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狼"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Dog") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "狗"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Cat") {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "猫"));
                    }
                    else if (grid[i][j].getPiece().getName()=="Mouse")
                    {
                        gridComponents[i][j].add(
                                new AnimalChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE,
                                        "鼠"));
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
        AnimalChessComponent chess = (AnimalChessComponent) getGridComponentAt(point).getComponents()[0];
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (AnimalChessComponent) clickedComponent.getComponents()[0]);
            }
//            if(gameController.winBlue()){
////                ChessGameFrame.setBlueWin();
//            }
        }
    }
}
