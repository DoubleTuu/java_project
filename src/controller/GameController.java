package controller;


import listener.GameListener;
import model.*;
import view.*;
import view.SaveAndLoadFrame;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;
import static view.SaveAndLoadFrame.ans;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {

    public static GameController gameController;
    public static int cheat=0;
    public Chessboard model;
    public ChessboardComponent view;
    public PlayerColor currentPlayer;

    public static JButton button;

//    public int gameRounds=1;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public ChessGameFrame chessGameFrame;
    public static JButton functionbutton = new JButton("function");
    public JLabel[][] WhereToMove_Jl = new JLabel[9][7];
    public JLabel[][] FootPrint_Jl = new JLabel[9][7];

//    public static Stack<RegretNode> regretStack = new Stack<>();
//    public int getGameRounds()
//    {
//        return gameRounds;
//    }
    public GameController(ChessboardComponent view, Chessboard model,ChessGameFrame chessGameFrame) {
//        ans.append("9 7 ");
        this.view = view;
        this.model = model;
        this.chessGameFrame = chessGameFrame;
        this.currentPlayer = PlayerColor.BLUE;


        view.registerController(this);
        initialize();
        addFunctionButton();
        addResetButton();
        view.repaint();
        SaveAndLoadFrame.map1=model.grid;
        initWhereToMove_Jl();
        initFootPrint_Jl();
    }

    private ChessGameFrame getChessGameFrame(){
        return this.chessGameFrame;
    }

    public void addFunctionButton()
    {
        functionbutton.setLocation(810, 810 / 10*3);
        functionbutton.setSize(150, 60);
        functionbutton.setBackground(new Color(246, 245, 238));
        functionbutton.setFont(new Font("Rockwell", Font.BOLD, 20));
        functionbutton.setVisible(true);
        chessGameFrame.add(functionbutton);
        functionbutton.setVisible(false);
        functionbutton.addActionListener(e ->//底下两个button没了！！！
        {
            MenuFrame menuFrame = new MenuFrame(view,model,this);
            menuFrame.setVisible(true);
        });
    }
    private void addResetButton()
    {
        button = new JButton("Reset");
        button.setLocation(810, 810/10*2);
        button.setBackground(new Color(246, 245, 238));
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        chessGameFrame.add(button);
        button.setVisible(false);
        button.addActionListener((e) ->
        {
            UIManager.put("OptionPane.yesButtonText", "Yes");
            UIManager.put("OptionPane.noButtonText", "No");
            int choice = JOptionPane.showConfirmDialog(chessGameFrame, "Are you sure you want to reset?", "Reset", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
            {
                initialize();
                ChessGameFrame.initTurn();
            }
        });
    }

    public void initialize() {

        remove_Move();
        remove_Footprint();
        currentPlayer = PlayerColor.BLUE;
        selectedPoint = null;
        model.initPieces();
        view.initiateChessComponent(model);
        view.repaint();
        chessGameFrame.setRounds(1);
        ChessGameFrame.setRounds();
        SaveAndLoadFrame.turn=1;
        ans.setLength(0);
        ans.append("9 7 ");
        SaveAndLoadFrame.turnturn=0;
        SaveAndLoadFrame.map1=model.grid;
//        System.out.println(currentPlayer);
    }

    // after a valid move swap the player
    public void swapColor()
    {
        if(cheat==0)
        {
            ChessGameFrame.setTurn();
            currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
            SaveAndLoadFrame.turnturn^=1;
            SaveAndLoadFrame.map1=model.grid;
            SaveAndLoadFrame.turn++;
        }
        else
        {
            cheat--;
        }
        ChessGameFrame.setRounds();
    }

    public boolean winBlue() {
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
    public boolean winRed() {
        // TODO: Check the board if there is a winner
        boolean test = true;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(model.getChessPieceAt(new ChessboardPoint(i,j))!=null && model.getChessPieceOwner(new ChessboardPoint(i,j)).equals(PlayerColor.BLUE)) {
                    for (int k = i - 4; k <= i + 4; k++) {
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
                    for (int k = j - 4; k <= j + 4; k++) {
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
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component)
    {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point))
        {
//            System.out.println(selectedPoint.getRow());
//            System.out.println(selectedPoint.getCol());
            ChessboardPoint selectedPoint1 = selectedPoint;
            ans.append(selectedPoint.getRow()+" "+selectedPoint.getCol()+" "+point.getRow()+" "+point.getCol()+" ");
            view.regretStack.push(new RegretNode(1,model.getChessPieceAt(selectedPoint),selectedPoint,model.getChessPieceAt(selectedPoint),point));
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on
            remove_Move();
            remove_Footprint();
            addFootprint(selectedPoint1, point);
//            chessGameFrame.setVisible(true);
        }
        judgeWin();
    }

    // click a cell with a chess
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component)
    {
        if (selectedPoint == null)
        {
            if (model.getChessPieceOwner(point).equals(currentPlayer))
            {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();//重新画棋子
                System.out.println("asd");
                whereToMove(selectedPoint);
//                chessGameFrame.setVisible(true);
            }
        }
        else if (selectedPoint.equals(point))
        {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            remove_Move();
//            chessGameFrame.setVisible(true);
        }
        // TODO: Implement capture function
        else if (model.isValidCapture(selectedPoint, point))
        {
            ChessboardPoint selectedPoint1=selectedPoint;
            ans.append(selectedPoint.getRow()+" "+selectedPoint.getCol()+" "+point.getRow()+" "+point.getCol()+" ");
            AnimalChessComponent temp=view.removeChessComponentAtGrid(point);
            view.regretStack.push(new RegretNode(2,model.getChessPieceAt(selectedPoint),selectedPoint,model.getChessPieceAt(point),point,temp));
            model.captureChessPiece(selectedPoint, point);
            view.getGridComponentAt(point).removeAll();
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            remove_Move();
            remove_Footprint();
            addFootprint(selectedPoint1, point);
        }
        judgeWin();
    }
    public void judgeWin(){
        if(winBlue())
        {
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
        if(winRed())
        {
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
    public void whereToMove(ChessboardPoint selectedPoint){
        int i=selectedPoint.getRow();
        int j=selectedPoint.getCol();
        for (int k = i - 4; k <= i + 4; k++) {
            if (k == i) {
                continue;
            }
            if (k >= 0 && k < Constant.CHESSBOARD_ROW_SIZE.getNum()) {
                if (model.getChessPieceAt(new ChessboardPoint(k,j)) == null) {
                    if (model.isValidMove(new ChessboardPoint(i, j), new ChessboardPoint(k, j))) {
                        addMove(k,j);
                    }
                } else {
                    if (model.isValidCapture(new ChessboardPoint(i, j), new ChessboardPoint(k, j))) {
                        addMove(k,j);
                    }
                }
            }
        }
        for (int k = j - 4; k <= j + 4; k++) {
            if (k == j) {
                continue;
            }
            if (k >= 0 && k < Constant.CHESSBOARD_COL_SIZE.getNum()) {
                if (model.getChessPieceAt(new ChessboardPoint(i,k)) == null) {
                    if (model.isValidMove(new ChessboardPoint(i, j), new ChessboardPoint(i, k))) {
                        addMove(i,k);
                    }
                } else {
                    if (model.isValidCapture(new ChessboardPoint(i, j), new ChessboardPoint(i, k))) {
                        addMove(i,k);
                    }
                }
            }
        }
    }
    public void initWhereToMove_Jl(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                WhereToMove_Jl[i][j] = new JLabel();
                WhereToMove_Jl[i][j].setBounds(810/5+72+ j*72+3,810/10+ i*72+3,72-6,72-6);
            }
        }
    }
    public void addMove(int i,int j){
        ImageIcon icon = new ImageIcon("resource\\Move.png");;
        icon.setImage(icon.getImage().getScaledInstance(72-6,72-6,Image.SCALE_DEFAULT));
        WhereToMove_Jl[i][j].setIcon(icon);
        chessGameFrame.add(WhereToMove_Jl[i][j]);
        chessGameFrame.getLayeredPane().add(WhereToMove_Jl[i][j],Integer.valueOf(Integer.MAX_VALUE));
        chessGameFrame.repaint();
    }
    public void remove_Move(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++) {
                if (WhereToMove_Jl[i][j] != null) {
                    chessGameFrame.getLayeredPane().remove(WhereToMove_Jl[i][j]);
                }
            }
        }
        chessGameFrame.repaint();
    }
    public void initFootPrint_Jl(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                FootPrint_Jl[i][j] = new JLabel();
                FootPrint_Jl[i][j].setBounds(810/5+72+ j*72+3,810/10+ i*72+3,72-6,72-6);
            }
        }
    }
    public void addFootprint(ChessboardPoint selectedPoint, ChessboardPoint point){
        int i=selectedPoint.getRow();
        int j=selectedPoint.getCol();
        int k=point.getRow();
        int l=point.getCol();
        ImageIcon icon;
        if(i==k){
            if(j>l){
                icon = new ImageIcon("resource\\FootprintLeft.png");
            }
            else{
                icon = new ImageIcon("resource\\FootprintRight.png");
            }
        }
        else{
            if(i>k){
                icon = new ImageIcon("resource\\FootprintUp.png");
            }
            else{
                icon = new ImageIcon("resource\\FootprintDown.png");
            }
        }
        icon.setImage(icon.getImage().getScaledInstance(72-6,72-6,Image.SCALE_DEFAULT));
        FootPrint_Jl[i][j].setIcon(icon);
        chessGameFrame.add(FootPrint_Jl[i][j]);
        chessGameFrame.getLayeredPane().add(FootPrint_Jl[i][j],Integer.valueOf(Integer.MAX_VALUE));
        chessGameFrame.repaint();
    }
    public void remove_Footprint()
    {
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++)
        {
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++)
            {
                if (FootPrint_Jl[i][j] != null)
                {
                    chessGameFrame.getLayeredPane().remove(FootPrint_Jl[i][j]);
                }
            }
        }
        chessGameFrame.repaint();
    }
}
