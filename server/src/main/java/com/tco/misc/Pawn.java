package com.tco.misc;

public class Pawn extends ChessPiece {
    public Pawn(String team) {
        super(team);
        this.name = "Pawn";
        this.initial = 'P';
    }

    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY, GameBoard board) {
        //white pawns move up the board, black pawns move down the board
        int direction = this.team.equals("White") ? 1 : -1;
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        Tile destinationTile = board.getTileAt(endX, endY);

        // 1 square forward
        if (deltaX == 0 && deltaY == direction && !destinationTile.isOccupied()) {
            return true;
        }

        // Capture diagonally
        if (Math.abs(deltaX) == 1 && deltaY == direction && destinationTile.isEnemy(this.team)) {
            return true;
        }

        // 2 squares forward off starting tile
        if (startX == endX && (startY == (this.team.equals("White") ? 1 : 6) && (endY == startY + 2 * direction))) {
            Tile intermediateTile = board.getTileAt(startX, startY + direction);
            if (!intermediateTile.isOccupied() && !destinationTile.isOccupied()) {
                return true;
            }
        }

        return false;
    }
}