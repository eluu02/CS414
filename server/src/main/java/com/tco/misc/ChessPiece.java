package com.tco.misc;

public abstract class ChessPiece {
    protected String name;
    protected String team;
    protected char initial;

    public ChessPiece(String team) {
        this.team = team;
    }

    public abstract boolean isLegalMove(int startX, int startY, int endX, int endY, GameBoard board);

    public String getTeam() {
        return this.team;
    }
}