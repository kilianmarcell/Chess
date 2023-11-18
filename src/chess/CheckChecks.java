package chess;

import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class CheckChecks {
    //Check if white or black king is in check
    public static boolean checkCheck(Square squares[][], Piece king) {
        System.out.println(king.getPosition());
        if(diagonalCheck(squares, king)) {
            System.out.println("Sakkban lennél diagonal " + king.getColor());
            return true;
        }
        if(straightCheck(squares, king)) {
            System.out.println("Sakkban lennél straight " + king.getColor());
            return true;
        }
        return false;
    }

    public static boolean straightCheck(Square squares[][], Piece king) {
        int xHelp = king.getPosition().getRow();
        int yHelp = king.getPosition().getColumn();
        String color = king.getColor();
        int x = xHelp;
        int y = yHelp;

        //Moving vertically
        while(--x >= 0) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Rook.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) x = 0;
        }
        x = xHelp;
        while(++x <= 7) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Rook.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) x = 7;
        }
        x = xHelp;

        //Moving horizontally
        while(--y >= 0) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Rook.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) y = 0;
        }
        y = yHelp;
        while(++y <= 7) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Rook.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) y = 7;
        }
        return false;
    }
    public static boolean diagonalCheck(Square squares[][], Piece king) {
        int xHelp = king.getPosition().getRow();
        int yHelp = king.getPosition().getColumn();
        String color = king.getColor();
        int x = xHelp;
        int y = yHelp;

        //Moving left up on board
        while(--x >= 0 && --y >= 0) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Knight.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) x = 0;
        }
        x = xHelp;
        y = yHelp;

        //Moving left down on board
        while(++x <= 7 && --y >= 0) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Knight.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) x = 0;
        }
        x = xHelp;
        y = yHelp;

        //Moving right up on board
        while(--x >= 0 && ++y <= 7) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Knight.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) x = 0;
        }
        x = xHelp;
        y = yHelp;

        //Moving right down on board
        while(++x <= 7 && ++y <= 7) {
            Piece helpPiece = squares[x][y].getPiece();
            if(helpPiece != null && !squares[x][y].getPiece().getColor().equals(color) && (squares[x][y].getPiece().getClass() == Queen.class || squares[x][y].getPiece().getClass() == Knight.class)) return true;
            else if(helpPiece != null && squares[x][y].getPiece().getColor().equals(color)) x = 0;
        }
        return false;
    }
}
