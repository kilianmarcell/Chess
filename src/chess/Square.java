package chess;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class Square extends JPanel {
    private int row;
    private int column;
    private Piece piece;

    public Square(int row, int column) {
        this.row = row;
        this.column = column;
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return (row + 1) + "" + (column +  1);
    }
}
