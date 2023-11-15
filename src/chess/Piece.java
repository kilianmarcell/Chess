package chess;

import java.util.List;

public class Piece {
    private Square position = null;
    private String color;

    public Piece(String color) {
        this.color = color;
    }

    public List<Square> possibleMoves(Square squares[][]) {
        return null;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public String getColor() {
        return color;
    }
}
