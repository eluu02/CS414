package com.tco.misc;

public class Knight extends ChessPiece {
    public Knight(String team) {
        super(team);
        this.name = "Knight";
        this.initial = 'N';
    }

    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY, GameBoard board) {
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
            Tile destinationTile = board.getTileAt(endX, endY);
            if (!destinationTile.isOccupied() || destinationTile.isEnemy(this.team)) {
                return true;  
            }
        }
        return false;
    }
}   