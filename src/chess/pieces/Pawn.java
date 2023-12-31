package chess.pieces;

import chess.Piece;
import chess.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private boolean changed;
    private Piece changedTo;
    private String moveDirection;

    public Pawn(String color) {
        super(color);
        this.changed = false;
        this.changedTo = null;
        this.moveDirection = color == "white" ? "up" : "down"; //If pawn is color white, it PieceMoves up, it its black it PieceMoves down
    }

    public List<Square> possibleMoves(Square squares[][]) {
        List<Square> possibleMoves = new ArrayList<>();
        int x = getPosition().getRow();
        int y = getPosition().getColumn();
        //White pawns move
        if(moveDirection == "up") {
            if(x > 0) {
                if(squares[x - 1][y].getPiece() == null) possibleMoves.add(squares[x - 1][y]);
                if(x == 6 && squares[x - 1][y].getPiece() == null && squares[x - 2][y].getPiece() == null) possibleMoves.add(squares[x - 2][y]);
                if(y > 0) {
                    if(squares[x - 1][y - 1].getPiece() != null && !squares[x - 1][y - 1].getPiece().getColor().equals(getColor())) {
                        possibleMoves.add(squares[x - 1][y - 1]);
                    }
                }
                if(y < 7) {
                    if (squares[x - 1][y + 1].getPiece() != null && !squares[x - 1][y + 1].getPiece().getColor().equals(getColor())) {
                        possibleMoves.add(squares[x - 1][y + 1]);
                    }
                }
            }
        }
        //Black pawns move
        else if(moveDirection == "down") {
            if(x < 7) {
                if(squares[x + 1][y].getPiece() == null) possibleMoves.add(squares[x + 1][y]);
                if(x == 1 && squares[x + 1][y].getPiece() == null && squares[x + 2][y].getPiece() == null) possibleMoves.add(squares[x + 2][y]);
                if(y > 0) {
                    if(squares[x + 1][y - 1].getPiece() != null && squares[x + 1][y - 1].getPiece().getColor() != getColor()) {
                        possibleMoves.add(squares[x + 1][y - 1]);
                    }
                }
                if(y < 7) {
                    if (squares[x + 1][y + 1].getPiece() != null && squares[x + 1][y + 1].getPiece().getColor() != getColor()) {
                        possibleMoves.add(squares[x + 1][y + 1]);
                    }
                }
            }
        }
        return possibleMoves;
    }

    public String getMoveDirection() {
        return moveDirection;
    }
}
