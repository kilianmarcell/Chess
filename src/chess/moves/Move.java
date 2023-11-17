package chess.moves;

import chess.Piece;
import chess.Square;

public class Move {
    private Square fromSquare;
    private Square toSquare;
    private Piece kickedPiece;

    public Move(Square fromSquare, Square toSquare) {
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        kickedPiece = null;
    }

    public Move(Square fromSquare, Square toSquare, Piece kickedPiece) {
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.kickedPiece = kickedPiece;
    }

    public Square getFromSquare() {
        return fromSquare;
    }

    public void setFromSquare(Square fromSquare) {
        this.fromSquare = fromSquare;
    }

    public Square getToSquare() {
        return toSquare;
    }

    public void setToSquare(Square toSquare) {
        this.toSquare = toSquare;
    }

    public Piece getKickedPiece() {
        return kickedPiece;
    }

    public void setKickedPiece(Piece kickedPiece) {
        this.kickedPiece = kickedPiece;
    }
}
