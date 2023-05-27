import controller.GameController;
import model.Chessboard;
import model.Save_and_Load;
import view.ChessGameFrame;

import javax.swing.*;

import static view.ChessGameFrame.mainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
//            Save_and_Load.work("C:\\Users\\戴尔\\Desktop\\java_project\\resource\\file\\save8.txt");
//            System.out.println("begin");
            mainFrame = new ChessGameFrame(1100, 810);
            GameController.gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
            mainFrame.setVisible(true);
        });
    }
}
