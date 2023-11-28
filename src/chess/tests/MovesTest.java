package chess.tests;

import chess.Board;
import chess.Square;
import chess.moves.Move;
import chess.pieces.Rook;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static chess.moves.KingMove.kingPossibleMoves;
import static chess.moves.KnightMoves.knightPossibleMoves;
import static chess.moves.RookMoves.rookPossibleMoves;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovesTest {
    @Test
    public void testBoardCreate() { //Testing create method and squares getter
        Board board = new Board(0);
        board.create();
        Square table[][] = board.getSquares();
        List<Square> rookMoves = new ArrayList<>();
        List<Square> kingMoves = new ArrayList<>();
        List<Square> knightMoves = new ArrayList<>();
        Move move = new Move(table[0][0], table[5][5]);

        assertEquals(rookPossibleMoves(table, 7, 7), rookMoves);
        assertEquals(kingPossibleMoves(table, 7, 4), kingMoves);
        assertEquals(knightPossibleMoves(table, 0, 2), knightMoves);
        assertEquals(move.getFromSquare(), table[0][0]);
        assertEquals(move.getToSquare(), table[5][5]);
        move.setToSquare(table[7][5]);
        assertEquals(move.getToSquare(), table[7][5]);
    }
}
