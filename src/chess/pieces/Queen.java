package chess.pieces;

import chess.Piece;
import chess.Square;

import java.util.List;

import static chess.moves.KnightMoves.knightPossibleMoves;
import static chess.moves.RookMoves.rookPossibleMoves;

public class Queen extends Piece {
    public Queen(String color) {
        super(color);
    }

    public List<Square> possibleMoves(Square squares[][]) {
        List<Square> possibleMoves = rookPossibleMoves(squares, getPosition().getRow(), getPosition().getColumn());
        possibleMoves.addAll(knightPossibleMoves(squares, getPosition().getRow(), getPosition().getColumn()));
        return possibleMoves;
    }
}
