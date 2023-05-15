package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import javax.swing.*;
import java.awt.*;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

//    public int gameRounds=1;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    private ChessGameFrame chessGameFrame;

//    public int getGameRounds()
//    {
//        return gameRounds;
//    }
    public GameController(ChessboardComponent view, Chessboard model,ChessGameFrame chessGameFrame) {
        this.view = view;
        this.model = model;
        this.chessGameFrame = chessGameFrame;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.repaint();
        addResetButton();
    }
    private ChessGameFrame getChessGameFrame(){
        return this.chessGameFrame;
    }

    private void addResetButton(){
        JButton button = new JButton("Reset");
        button.setLocation(810, 810 / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        chessGameFrame.add(button);
        button.addActionListener((e) -> {
            UIManager.put("OptionPane.yesButtonText", "Yes");
            UIManager.put("OptionPane.noButtonText", "No");
            int choice = JOptionPane.showConfirmDialog(chessGameFrame, "Are you sure you want to reset?", "Reset", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                initialize();
            }
        });
    }

    private void initialize() {
        currentPlayer = PlayerColor.BLUE;
        model.initPieces();
        view.initiateChessComponent(model);
        view.repaint();
        chessGameFrame.setRounds(1);
        chessGameFrame.setRounds();
    }

    // after a valid move swap the player
    private void swapColor()
    {
        ChessGameFrame.setRounds();
        ChessGameFrame.setTurn();
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private boolean winBlue() {
        // TODO: Check the board if there is a winner
        boolean test = true;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(model.getChessPieceAt(new ChessboardPoint(i,j))!=null && model.getChessPieceOwner(new ChessboardPoint(i,j)).equals(PlayerColor.RED)) {
                    for (int k = i - 3; k <= i + 3; k++) {
                        if (k == i) {
                            continue;
                        }
                        if (k >= 0 && k < Constant.CHESSBOARD_ROW_SIZE.getNum()) {
                            if (model.getChessPieceAt(new ChessboardPoint(k,j)) == null) {
                                if (model.isValidMove(new ChessboardPoint(i, j), new ChessboardPoint(k, j))) {
                                    test = false;
                                }
                            } else {
                                if (model.isValidCapture(new ChessboardPoint(i, j), new ChessboardPoint(k, j))) {
                                    test = false;
                                }
                            }
                        }
                    }
                    for (int k = j - 3; k <= j + 3; k++) {
                        if (k == j) {
                            continue;
                        }
                        if (k >= 0 && k < Constant.CHESSBOARD_COL_SIZE.getNum()) {
                            if (model.getChessPieceAt(new ChessboardPoint(i,k)) == null) {
                                if (model.isValidMove(new ChessboardPoint(i, j), new ChessboardPoint(i, k))) {
                                    test = false;
                                }
                            } else {
                                if (model.isValidCapture(new ChessboardPoint(i, j), new ChessboardPoint(i, k))) {
                                    test = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return test || ( model.getChessPieceAt(new ChessboardPoint(0,3))!=null && model.getChessPieceOwner(new ChessboardPoint(0,3)).equals(PlayerColor.BLUE));
    }
    private boolean winRed() {
        // TODO: Check the board if there is a winner
        boolean test = true;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(model.getChessPieceAt(new ChessboardPoint(i,j))!=null && model.getChessPieceOwner(new ChessboardPoint(i,j)).equals(PlayerColor.BLUE)) {
                    for (int k = i - 3; k <= i + 3; k++) {
                        if (k == i) {
                            continue;
                        }
                        if (k >= 0 && k < Constant.CHESSBOARD_ROW_SIZE.getNum()) {
                            if (model.getChessPieceAt(new ChessboardPoint(k,j)) == null) {
                                if (model.isValidMove(new ChessboardPoint(i, j), new ChessboardPoint(k, j))) {
                                    test = false;
                                }
                            } else {
                                if (model.isValidCapture(new ChessboardPoint(i, j), new ChessboardPoint(k, j))) {
                                    test = false;
                                }
                            }
                        }
                    }
                    for (int k = j - 3; k <= j + 3; k++) {
                        if (k == j) {
                            continue;
                        }
                        if (k >= 0 && k < Constant.CHESSBOARD_COL_SIZE.getNum()) {
                            if (model.getChessPieceAt(new ChessboardPoint(i,k)) == null) {
                                if (model.isValidMove(new ChessboardPoint(i, j), new ChessboardPoint(i, k))) {
                                    test = false;
                                }
                            } else {
                                if (model.isValidCapture(new ChessboardPoint(i, j), new ChessboardPoint(i, k))) {
                                    test = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return test || ( model.getChessPieceAt(new ChessboardPoint(8,3))!=null && model.getChessPieceOwner(new ChessboardPoint(8,3)).equals(PlayerColor.RED));
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on
        }
        if(winBlue()){
            UIManager.put("OptionPane.yesButtonText", "Reset");
            UIManager.put("OptionPane.noButtonText", "Close");
            int choice = JOptionPane.showConfirmDialog(null, "Blue Side Wins!", "Blue Side Wins", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                initialize();
            }
            else{
                chessGameFrame.dispose();
            }
        }
        if(winRed()){
            UIManager.put("OptionPane.yesButtonText", "Reset");
            UIManager.put("OptionPane.noButtonText", "Close");
            int choice = JOptionPane.showConfirmDialog(null, "Red Side Wins!", "Red Side Wins", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                initialize();
            }
            else{
                chessGameFrame.dispose();
            }
        }
    }

    // click a cell with a chess
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
        if (selectedPoint == null)
        {
            if (model.getChessPieceOwner(point).equals(currentPlayer))
            {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();//重新画棋子
                System.out.println("asd");
            }
        }
        else if (selectedPoint.equals(point))
        {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        }
        // TODO: Implement capture function
        else if (model.isValidCapture(selectedPoint, point))
        {
            model.captureChessPiece(selectedPoint, point);
            view.getGridComponentAt(point).removeAll();
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
        }
    }
}
