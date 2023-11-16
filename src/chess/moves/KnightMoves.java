package chess.moves;

import chess.Square;

import java.util.ArrayList;
import java.util.List;

public class KnightMoves {
    public static List<Square> knightPossibleMoves(Square squares[][], int positionX, int positionY) {
        List<Square> possibleMoves = new ArrayList<>();
        int x = positionX;
        int y = positionY;

        //Moving left up on board
        while(--x >= 0 && --y >= 0) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(!squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                x = 0;
            }
            else x = 0;
        }
        x = positionX;
        y = positionY;

        //Moving left down on board
        while(++x <= 7 && --y >= 0) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(!squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                x = 7;
            }
            else x = 7;
        }
        x = positionX;
        y = positionY;

        //Moving right up on board
        while(--x >= 0 && ++y <= 7) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(!squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                x = 0;
            }
            else x = 0;
        }
        x = positionX;
        y = positionY;

        //Moving right down on board
        while(++x <= 7 && ++y <= 7) {
            if(squares[x][y].getPiece() == null) possibleMoves.add(squares[x][y]);
            else if(!squares[x][y].getPiece().getColor().equals(squares[positionX][positionY].getPiece().getColor())) {
                possibleMoves.add(squares[x][y]);
                x = 7;
            }
            else x = 7;
        }
        return possibleMoves;
    }
}
