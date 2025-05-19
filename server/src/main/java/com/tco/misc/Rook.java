package com.tco.misc;

public class Rook extends ChessPiece {
    public Rook(String team) {
        super(team);
        this.name = "Rook";
        this.initial = 'R';
    }

    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY, GameBoard board) {

        //check if rook is in same position
        if (startX == endX && startY == endY) {
            return false;
        }

        if (startX != endX && startY != endY) {
            return false;
        }

        // -1 if end < start
        // 0 if end == start
        // 1 if end > start
        int xDirection = Integer.compare(endX, startX);
        int yDirection = Integer.compare(endY, startY);

        int x = startX + xDirection;
        int y = startY + yDirection;

        while (x != endX || y != endY) {
            if (board.getTileAt(x, y).isOccupied()) {
                return false; // The path is blocked by another piece
            }
            x += xDirection == 0 ? 0 : xDirection;
            y += yDirection == 0 ? 0 : yDirection;
        }

        Tile destinationTile = board.getTileAt(endX, endY);
        if (destinationTile.isOccupied() && !destinationTile.isEnemy(this.team)) {
            return false;
        }

        return true;
    }
}