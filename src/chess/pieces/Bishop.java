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

        if(x - 2 > 0) {
            if(y - 1 > 0 && !squares[x - 2][y - 1].getPiece().getColor().equals(getColor())) possibleMoves.add(squares[x - 2][y - 1]);
        }
        return possibleMoves;
    }
}
