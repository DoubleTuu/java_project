package model;

import view.ChessboardComponent;
import model.Save_and_Load.*;
import javax.swing.*;
import java.awt.*;

import static model.Save_and_Load.addarchive_num;
import static model.Save_and_Load.getrank;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    public Cell[][] grid;

    public Chessboard()
    {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        initGrid();
        initPieces();
    }
//    public Chessboard Chessboard()
//    {
//        Save_and_Load.work();
//        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
//        initGrid();
//        initPiecesload();
//        return this;
//    }
    private void initGrid()
    {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++)
        {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++)
            {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                grid[i][j].setPiece(null);
            }
        }
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Mouse",1));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));

        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Mouse",1));
    }

    public void initPiecesload()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                grid[i][j].setPiece(null);
            }
        }
        for(int i=0;i<16;i++)
        {
            grid[Save_and_Load.row[i]][Save_and_Load.col[i]].setPiece(new ChessPiece(Save_and_Load.getowner(i)=="Red"?PlayerColor.RED:PlayerColor.BLUE,Save_and_Load.name[i],getrank(Save_and_Load.name[i])));
        }
    }
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
//        if (!isValidMove(src, dest)) {
//            throw new IllegalArgumentException("Illegal chess move!");
//        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
        removeChessPiece(dest);
        setChessPiece(dest, removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessboardComponent chessboardComponent = new ChessboardComponent(72);
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if(calculateDistance(src, dest) == 1) {
            if( !(getChessPieceOwner(src).equals(PlayerColor.BLUE) & (dest.getRow()==8 & dest.getCol()==3)) &
                    !(getChessPieceOwner(src).equals(PlayerColor.RED) & (dest.getRow()==0 & dest.getCol()==3))) {
                if (getChessPieceAt(src).getName().equals("Mouse")) {
                    return true;
                } else if (!getChessPieceAt(src).getName().equals("Mouse")) {
                    return !chessboardComponent.getRiverCell().contains(new ChessboardPoint(dest.getRow(), dest.getCol()));
                }
            }
        }
        //over the river
        if(getChessPieceAt(src).getName() == "Lion" || getChessPieceAt(src).getName() == "Tiger") {
            if (src.getRow() == dest.getRow() && !chessboardComponent.getRiverCell().contains(dest)) {
                int big, small;
                if (src.getCol() > dest.getCol()) {
                    big = src.getCol();
                    small = dest.getCol();
                } else {
                    big = dest.getCol();
                    small = src.getCol();
                }
                boolean test = true;
                for (int i = small+1; i <big; i++) {
                    if ( !(chessboardComponent.getRiverCell().contains(new ChessboardPoint(dest.getRow(), i))
                            && getChessPieceAt(new ChessboardPoint(dest.getRow(), i)) == null)){
                        test = false;
                    }
                }
                return test;
            } else if (src.getCol() == dest.getCol() && !chessboardComponent.getRiverCell().contains(dest)) {
                int big, small;
                if (src.getRow() > dest.getRow()) {
                    big = src.getRow();
                    small = dest.getRow();
                } else {
                    small = src.getRow();
                    big = dest.getRow();
                }
                boolean test = true;
                for (int i = small+1; i < big; i++) {
                    if ( !(chessboardComponent.getRiverCell().contains(new ChessboardPoint(i, dest.getCol()))
                            && getChessPieceAt(new ChessboardPoint(i, dest.getCol())) == null)){
                        test = false;
                    }
                }
                return test;
            }
        }
        return false;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        ChessboardComponent chessboardComponent = new ChessboardComponent(72);
        if(getChessPieceAt(src).getOwner()!=getChessPieceAt(dest).getOwner()){
            if (calculateDistance(src, dest) == 1) {
                if(chessboardComponent.getRiverCell().contains(src) ^ chessboardComponent.getRiverCell().contains(dest)){
                    return false;
                }
                else if((getChessPieceAt(dest).getOwner().equals(PlayerColor.BLUE) && chessboardComponent.getTrapRed().contains(dest))
                         ||(getChessPieceAt(dest).getOwner().equals(PlayerColor.RED) && chessboardComponent.getTrapBlue().contains(dest))){
                    return true;
                }
                else{
                    return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
                }
            }
            else {
                if (getChessPieceAt(src).getName().equals("Lion") || getChessPieceAt(src).getName().equals("Tiger")) {
                    if (src.getRow() == dest.getRow() && !chessboardComponent.getRiverCell().contains(dest)) {
                        int big, small;
                        if (src.getCol() > dest.getCol()) {
                            big = src.getCol();
                            small = dest.getCol();
                        } else {
                            big = dest.getCol();
                            small = src.getCol();
                        }
                        boolean test = true;
                        for (int i = small + 1; i < big; i++) {
                            if (!(chessboardComponent.getRiverCell().contains(new ChessboardPoint(dest.getRow(), i))
                                    && getChessPieceAt(new ChessboardPoint(dest.getRow(), i)) == null)) {
                                test = false;
                            }
                        }
                        return test && getChessPieceAt(src).canCapture(getChessPieceAt(dest));
                    } else if (src.getCol() == dest.getCol() && !chessboardComponent.getRiverCell().contains(dest)) {
                        int big, small;
                        if (src.getRow() > dest.getRow()) {
                            big = src.getRow();
                            small = dest.getRow();
                        } else {
                            small = src.getRow();
                            big = dest.getRow();
                        }
                        boolean test = true;
                        for (int i = small + 1; i < big; i++) {
                            if (!(chessboardComponent.getRiverCell().contains(new ChessboardPoint(i, dest.getCol()))
                                    && getChessPieceAt(new ChessboardPoint(i, dest.getCol())) == null)) {
                                test = false;
                            }
                        }
                        return test && getChessPieceAt(src).canCapture(getChessPieceAt(dest));
                    }
                    else{
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }
}
