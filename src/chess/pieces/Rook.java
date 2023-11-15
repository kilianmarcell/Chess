package chess.pieces;

import chess.Piece;

public class Rook extends Piece {
    private boolean movedBefore;

    public Rook(String color) {
        super(color);
        this.movedBefore = false;
    }
}
