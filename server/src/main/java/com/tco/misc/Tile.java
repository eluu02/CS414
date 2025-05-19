package com.tco.misc;
public class Tile {
    private ChessPiece piece;
    private int x; // Column (0-7)
    private int y; // Row (0-7)

    Tile(ChessPiece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public boolean isEnemy(String t) {
        return this.piece != null && !this.piece.getTeam().equals(t);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public String getTeam() {
        return this.piece.getTeam();
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
}
