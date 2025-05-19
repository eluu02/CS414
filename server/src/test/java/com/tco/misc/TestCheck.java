package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCheck {
    private GameBoard board;
    private int kingX;
    private int kingY;

    @BeforeEach
    public void setUp() {
        board = new GameBoard();
        kingX = 4;
        kingY = 7;
    }

    @Test
    @DisplayName("ejpitera: test putting the king in check")
    public void testCheckLogic() {
        board.movePiece(4, 1, 4, 3);
        board.movePiece(3, 6, 3, 4);
        board.movePiece(5, 0, 1, 4);
        assertTrue(board.isCheck("Black", kingX, kingY));

        // black blocks the check with pawn
        board.movePiece(2, 6, 2, 5);
        assertFalse(board.isCheck("Black", kingX, kingY));
    }

    @Test
    @DisplayName("ejpitera: ensure that player must not be in check at end of their turn")
    public void testAvoidCheck() {
        board.movePiece(4, 1, 4, 3);
        board.movePiece(3, 6, 3, 4);
        board.movePiece(5, 0, 1, 4);

        assertFalse(board.movePiece(4, 6, 4, 4));
        assertTrue(board.movePiece(2, 6, 2, 5));

        // now check if player can put themselves back in check
        board.movePiece(0, 1, 0, 2);
        assertFalse(board.movePiece(2, 5, 2, 4));
    }
}
