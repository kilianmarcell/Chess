package chess.pieces;

import chess.Piece;
import chess.Square;

import java.util.List;

import static chess.moves.KnightMoves.knightPossibleMoves;


public class Knight extends Piece {
    public Knight(String color) {
        super(color);
    }

    public List<Square> possibleMoves(Square squares[][]) {
        return knightPossibleMoves(squares, getPosition().getRow(), getPosition().getColumn());
    }
}
