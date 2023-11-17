package chess.pieces;

import chess.Piece;
import chess.Square;

import java.util.List;

import static chess.moves.RookMoves.rookPossibleMoves;

public class Rook extends Piece {
    private boolean movedBefore;

    public Rook(String color) {
        super(color);
    }

    public List<Square> possibleMoves(Square squares[][]) {
        return rookPossibleMoves(squares, getPosition().getRow(), getPosition().getColumn());
    }
}
