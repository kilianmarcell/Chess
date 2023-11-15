package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class PieceSet {
    private List<Piece> pieces = new ArrayList<>();
    private String color;

    public PieceSet(String color) {
        for (int i = 0; i < 8; i++) pieces.add(new Pawn(color));
        pieces.add(new King());
        pieces.add(new Queen());
        for (int i = 0; i < 2; i++) {
            pieces.add(new Knight());
            pieces.add(new Bishop());
            pieces.add(new Rook());
        }
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public String getColor() {
        return color;
    }
}
