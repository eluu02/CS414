package com.tco.misc;

public class Queen extends ChessPiece {
    public Queen(String team) {
        super(team);
        this.name = "Queen";
        this.initial = 'Q';
    }

    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY,  GameBoard board) {
        // Combine logic from Rook and Bishop
        Rook rook = new Rook(this.team);
        Bishop bishop = new Bishop(this.team);
        return rook.isLegalMove(startX, startY, endX, endY, board) || bishop.isLegalMove(startX, startY, endX, endY, board);
/* 
        //check is queen is in same position
        if (startX == endX && startY == endY) {
            return false;
        }

        //vertical movement
        if (startX == endX && startY != endY) {
            return true;
        }

        //horizontal movement
        if (startX != endX && startY == endY) {
            return true;
        }

        //diagonal movement
        if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
            return true;
        }
        return false;
        */
    }
}