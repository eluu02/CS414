package com.tco.misc;

public class King extends ChessPiece {
    public King(String team) {
        super(team);
        this.name = "King";
        this.initial = 'K';
    }

    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY, GameBoard board) {
        //check if king is in same position
        if (startX == endX && startY == endY) {
            return false;
        }

        //valid move if king moved one square in any direction
        if (Math.abs(endX - startX) <= 1 && Math.abs(endY - startY) <= 1) {
            Tile destinationTile = board.getTileAt(endX, endY);
            if (!destinationTile.isOccupied() || destinationTile.isEnemy(this.team))
            return true;
        }

        return false;
    }
}