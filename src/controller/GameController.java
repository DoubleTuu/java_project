package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

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

//    public int getGameRounds()
//    {
//        return gameRounds;
//    }
    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
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
        ChessboardComponent chessboardComponent = new ChessboardComponent(1);
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
        ChessboardComponent chessboardComponent = new ChessboardComponent(1);
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
