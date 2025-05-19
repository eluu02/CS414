package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.Tile;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTile {
    private Tile tile;

    @Test
    @DisplayName("ejpitera: test isOccupied method")
    public void testIsOccupied() {
        Pawn pawn = new Pawn("White");
        tile = new Tile(pawn, 0, 1);
        assertTrue(tile.isOccupied());
    }

    @Test
    @DisplayName("ejpitera: test isEnemy method")
    public void testIsEnemy() {
        Pawn pawn = new Pawn("White");
        tile = new Tile(pawn, 0, 1);
        assertTrue(tile.isEnemy("Black"));
    }

}
