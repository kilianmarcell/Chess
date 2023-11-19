package chess.pieces;

import chess.Piece;
import chess.Square;

import java.util.List;

import static chess.moves.KingMove.kingPossibleMoves;

public class King extends Piece {

    public King(String color) {
        super(color);
    }

    public List<Square> possibleMoves(Square squares[][]) {
        return kingPossibleMoves(squares, getPosition().getRow(), getPosition().getColumn());
    }
}
