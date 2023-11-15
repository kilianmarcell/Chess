package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class PieceSet {
    private List<Piece> pieces = new ArrayList<>();

    public PieceSet(String color) {
        for (int i = 0; i < 8; i++) pieces.add(new Pawn(color));
        pieces.add(new King(color));
        pieces.add(new Queen(color));
        for (int i = 0; i < 2; i++) {
            pieces.add(new Knight(color));
            pieces.add(new Bishop(color));
            pieces.add(new Rook(color));
        }
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
