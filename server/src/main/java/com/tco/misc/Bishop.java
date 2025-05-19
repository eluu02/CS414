package com.tco.misc;

public class Bishop extends ChessPiece {
    public Bishop(String team) {
        super(team);
        this.name = "Bishop";
        this.initial = 'B';
    }

    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY, GameBoard board) {
        //check if bishop is in same position
        if (startX == endX && startY == endY) {
            return false;
        }

        //bishop moves diagonally, so space moved vertically and horizontally must be the same
        if (Math.abs(startX - endX) != Math.abs(startY - endY)) {
            return false;
        }

        int xDirection = (endX - startX) > 0 ? 1 : -1;
        int yDirection = (endY - startY) > 0 ? 1 : -1;

        //check if the path is blocked
        int x = startX + xDirection;
        int y = startY + yDirection;
        while (x != endX && y != endY) {
            if (board.getTileAt(x, y).isOccupied()) {
                return false; 
            }
            x += xDirection;
            y += yDirection;
        }
        
        // can't capture your own piece
        Tile destinationTile = board.getTileAt(endX, endY);
        if (destinationTile.isOccupied() && !destinationTile.isEnemy(this.team)) {
            return false;
        }

        return true;
    }
}