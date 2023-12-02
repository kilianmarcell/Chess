package chess.moves;

import chess.Square;

import java.util.ArrayList;
import java.util.List;

public class RookMoves {
    public static List<Square> rookPossibleMoves(Square squares[][], int positionX, int positionY) {
        List<Square> possibleMoves = new ArrayList<>();
        int x = positionX;
        int y = positionY;

        //Moving vertically
        while(--x >= 0) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(squares[x][y].getPiece() != null && !squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                x = 0;
            }
            else x = 0;
        }
        x = positionX;
        while(++x <= 7) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(squares[x][y].getPiece() != null && !squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                x = 7;
            }
            else x = 7;
        }
        x = positionX;

        //Moving horizontally
        while(--y >= 0) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(squares[x][y].getPiece() != null && !squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                y = 0;
            }
            else y = 0;
        }
        y = positionY;
        while(++y <= 7) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(squares[x][y].getPiece() != null && !squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                y = 7;
            }
            else y = 7;
        }
        return possibleMoves;
    }
}
