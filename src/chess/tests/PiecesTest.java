package chess.tests;

import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PiecesTest {
    @Test
    public void testBoardCreate() { //Testing pieces method
        King king = new King("white");
        Queen queen = new Queen("white");
        Pawn pawn = new Pawn("black");

        assertEquals("white", king.getColor());
        assertEquals("white", queen.getColor());
        assertEquals("black", pawn.getColor());
        assertEquals("down", pawn.getMoveDirection());
    }
}
