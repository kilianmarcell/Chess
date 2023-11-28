package chess.tests;

import chess.Board;
import chess.Square;
import chess.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    public void testBoardCreate() { //Testing create method and squares getter
        Board board = new Board(0);
        board.create();
        Square table[][] = board.getSquares();
        table[0][0].getPiece().getColor();
        assertEquals("black", table[0][0].getPiece().getColor());
        assertEquals(Rook.class, table[0][0].getPiece().getClass());
        assertEquals("white", table[7][7].getPiece().getColor());
        assertEquals(Rook.class, table[7][7].getPiece().getClass());
    }
}
