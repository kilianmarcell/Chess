package chess.pieces;

import chess.Piece;
import chess.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(String color) {
        super(color);
    }

    public List<Square> possibleMoves(Square squares[][]) {
        List<Square> possibleMoves = new ArrayList<>();
        int x = getPosition().getRow();
        int y = getPosition().getColumn();

        System.out.println("x= " + x + " y= " + y);

        if(x >= 2 && y >= 1) {
            if(squares[x - 2][y - 1].getPiece() == null) possibleMoves.add(squares[x - 2][y - 1]);
            else if(!squares[x - 2][y - 1].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x - 2][y - 1]);
        }
        if(x >= 1 && y >= 2) {
            if(squares[x - 1][y - 2].getPiece() == null) possibleMoves.add(squares[x - 1][y - 2]);
            else if(!squares[x - 1][y - 2].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x - 1][y - 2]);
        }
        if(x >= 2 && y <= 6) {
            if(squares[x - 2][y + 1].getPiece() == null) possibleMoves.add(squares[x - 2][y + 1]);
            else if(!squares[x - 2][y + 1].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x - 2][y + 1]);
        }
        if(x >= 1 && y <= 5) {
            if(squares[x - 1][y + 2].getPiece() == null) possibleMoves.add(squares[x - 1][y + 2]);
            else if(!squares[x - 1][y + 2].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x - 1][y + 2]);
        }
        if(x <= 5 && y >= 1) {
            if(squares[x + 2][y - 1].getPiece() == null) possibleMoves.add(squares[x + 2][y - 1]);
            else if(!squares[x + 2][y - 1].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x + 2][y - 1]);
        }
        if(x <= 6 && y >= 2) {
            if(squares[x + 1][y - 2].getPiece() == null) possibleMoves.add(squares[x + 1][y - 2]);
            else if(!squares[x + 1][y - 2].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x + 1][y - 2]);
        }
        if(x <= 5 && y <= 6) {
            if(squares[x + 2][y + 1].getPiece() == null) possibleMoves.add(squares[x + 2][y + 1]);
            else if(!squares[x + 2][y + 1].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x + 2][y + 1]);
        }
        if(x <= 6 && y <= 5) {
            if(squares[x + 1][y + 2].getPiece() == null) possibleMoves.add(squares[x + 1][y + 2]);
            else if(!squares[x + 1][y + 2].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x + 1][y + 2]);
        }
        return possibleMoves;
    }
}
