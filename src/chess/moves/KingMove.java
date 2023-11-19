package chess.moves;

import chess.Square;

import java.util.ArrayList;
import java.util.List;

public class KingMove {
    public static List<Square> kingPossibleMoves(Square squares[][], int x, int y) {
        List<Square> possibleMoves = new ArrayList<>();

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if(i >= 0 && i <= 7 && j >= 0 && j <= 7) {
                    if(i != x || j != y) {
                        if(squares[i][j].getPiece() == null) possibleMoves.add(squares[i][j]);
                        else if(!squares[i][j].getPiece().getColor().equals(squares[x][y].getPiece().getColor())) possibleMoves.add(squares[i][j]);
                    }
                }
            }
        }
        return possibleMoves;
    }
}
