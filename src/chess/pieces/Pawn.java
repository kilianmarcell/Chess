package chess.pieces;

import chess.Piece;

public class Pawn extends Piece {
    private boolean changed;
    private Piece changedTo;
    private String moveDirection;
    private boolean movedBefore;

    public Pawn(String c) {
        this.changed = false;
        this.changedTo = null;
        this.moveDirection = c == "white" ? "up" : "down"; //If pawn is color white, it moves up, it its black it moves down
        this.movedBefore = false;
    }
}
