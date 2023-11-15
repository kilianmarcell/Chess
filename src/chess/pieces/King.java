package chess.pieces;

import chess.Piece;

public class King extends Piece {
    private boolean movedBefore;
    public King() {
        this.movedBefore = false;
    }
}
