package model;

import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Mouse",1));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[0][2].setPiece(new ChessPiece(PlayerColor.RED, "trapRed",0));
        grid[0][4].setPiece(new ChessPiece(PlayerColor.RED, "trapRed",0));
        grid[1][3].setPiece(new ChessPiece(PlayerColor.RED, "trapRed",0));
        grid[0][3].setPiece(new ChessPiece(PlayerColor.RED, "homeRed",0));

        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Mouse",1));
        grid[8][2].setPiece(new ChessPiece(PlayerColor.RED, "trapBlue",0));
        grid[8][4].setPiece(new ChessPiece(PlayerColor.RED, "trapBlue",0));
        grid[7][3].setPiece(new ChessPiece(PlayerColor.RED, "trapBlue",0));
        grid[8][3].setPiece(new ChessPiece(PlayerColor.RED, "homeBlue",0));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
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
        ChessboardComponent chessboardComponent = new ChessboardComponent(1);
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if(calculateDistance(src, dest) == 1) {
            if(getChessPieceAt(src).getName().equals("Mouse") ) {
                return true;
            }
            else if(!getChessPieceAt(src).getName().equals("Mouse"))
            {
                return !chessboardComponent.getRiverCell().contains(new ChessboardPoint(dest.getRow(), dest.getCol()));
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
        ChessboardComponent chessboardComponent = new ChessboardComponent(1);
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
