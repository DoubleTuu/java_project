package model;

import view.AnimalChessComponent;

public class RegretNode
{
    public int type;
    public ChessPiece chessPiece1;
    public ChessboardPoint chessboardPoint1;
    public ChessPiece chessPiece2;
    public ChessboardPoint chessboardPoint2;
    public AnimalChessComponent chessComponent2;
    public RegretNode (int type,ChessPiece chessPiece1, ChessboardPoint chessboardPoint1, ChessPiece chessPiece2, ChessboardPoint chessboardPoint2, AnimalChessComponent chessComponent2)
    {
        this.type = type;
        this.chessPiece1 = chessPiece1;
        this.chessboardPoint1 = chessboardPoint1;
        this.chessPiece2 = chessPiece2;
        this.chessboardPoint2 = chessboardPoint2;
        this.chessComponent2 = chessComponent2;
    }
    public RegretNode (int type,ChessPiece chessPiece1, ChessboardPoint chessboardPoint1, ChessPiece chessPiece2, ChessboardPoint chessboardPoint2)
    {
        this.type = type;
        this.chessPiece1 = chessPiece1;
        this.chessboardPoint1 = chessboardPoint1;
        this.chessPiece2 = chessPiece2;
        this.chessboardPoint2 = chessboardPoint2;
    }

}
