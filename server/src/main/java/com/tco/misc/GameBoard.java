package com.tco.misc;
import java.util.ArrayList;

import com.tco.misc.Bishop;
import com.tco.misc.King;
import com.tco.misc.Knight;
import com.tco.misc.Pawn;
import com.tco.misc.PiecePosition;
import com.tco.misc.Queen;
import com.tco.misc.Rook;

public class GameBoard {
    private ArrayList<Tile> board;

    public GameBoard() {
        this.board = new ArrayList<>();
        populateBoard();
    }

    public GameBoard(String dbString) {
        this.board = new ArrayList<>();
        loadBoard(dbString);
    }

    public void loadBoard(String dbString) {
        initializeEmptyBoard();
        String[] locations = dbString.split(",");
        for (String location : locations) {
            if (location.length() >= 4) {
                char initial = location.charAt(0);
                char color = location.charAt(1);
                char xCoord = location.charAt(2);
                char yCoord = location.charAt(3);

                String team = (color == 'W') ? "White" : "Black";
                int x = xCoord - '0';
                int y = yCoord - '0';
                ChessPiece piece = createPieceFromInitial(initial, team);
                getTileAt(x, y).setPiece(piece);
            }
        }
    }

    private void populateBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                String team = null;
                if (y == 0 || y == 1) {
                    team = "White";
                } else if (y == 6 || y == 7) {
                    team = "Black";
                }
            
                ChessPiece piece = null;
                if (team != null) {
                    piece = pieceSetup(x, y, team);
                }
                Tile t = new Tile(piece, x, y);
                board.add(t); 
            }
        }
    }

    public String saveBoard() {
        String dbString = "";
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = getTileAt(x, y);
                if (tile.isOccupied()) {
                    ChessPiece piece = tile.getPiece();
                    char initial = piece.initial;
                    char color = piece.getTeam().charAt(0);
                    char xCoord = (char)(x + '0');
                    char yCoord = (char)(y + '0');
                    dbString = dbString + initial + color + xCoord + yCoord + ',';
                }
                
            }
        }
        return dbString;
    }

    private ChessPiece createPieceFromInitial(char initial, String team) {
        switch (initial) {
            case 'P': return new Pawn(team);
            case 'R': return new Rook(team);
            case 'N': return new Knight(team);
            case 'B': return new Bishop(team);
            case 'Q': return new Queen(team);
            case 'K': return new King(team);
            default: return null;
        }
    }

    // Helper method to initialize an empty board
    private void initializeEmptyBoard() {
        board.clear();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile t = new Tile(null, x, y);
                board.add(t);
            }
        }
    }

    private ChessPiece pieceSetup(int x, int y, String team) {
        if ((y == 1 && team.equals("White")) || (y == 6 && team.equals("Black"))) {
            return new Pawn(team);
        }
    
        if ((y == 0 && team.equals("White")) || (y == 7 && team.equals("Black"))) {
            switch (x) {
                case 0: case 7: return new Rook(team);
                case 1: case 6: return new Knight(team);
                case 2: case 5: return new Bishop(team);
                case 3: return new Queen(team);
                case 4: return new King(team);
            }
        }
        return null;
    }

    public Tile getTileAt(int x, int y) {
        return board.get(y * 8 + x); 
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        // Logic to move a piece
        Tile startTile = getTileAt(startX, startY);
        Tile endTile = getTileAt(endX, endY);
        ChessPiece piece = startTile.getPiece();

        // empty tile
        if (piece == null) {
            return false;
        }

        String team = piece.getTeam();

        if (piece.isLegalMove(startX, startY, endX, endY, this)) {
            ChessPiece capturedPiece = endTile.getPiece();
            endTile.setPiece(piece);
            startTile.setPiece(null);

            // find king
            int kingX = -1;
            int kingY = -1;
            boolean kingFound = false;
            for (int y = 0; y < 8 && !kingFound; y++) {
                for (int x = 0; x < 8; x++) {
                    ChessPiece p = getTileAt(x, y).getPiece();
                    if (p instanceof King && p.getTeam().equals(team)) {
                        kingX = x;
                        kingY = y;
                        kingFound = true;
                        break;
                    }
                }
            }

            // check if the player put himself in check with the move
            if (isCheck(team, kingX, kingY)) {
                // undo the move
                startTile.setPiece(piece);
                endTile.setPiece(capturedPiece);
                return false;
            }
            return true;
        }

        return false;
    }

    public boolean isCheck(String team, int kingX, int kingY) {
        // Check for threats from all enemy pieces
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = getTileAt(x, y);
                ChessPiece piece = tile.getPiece();
                if (tile.isEnemy(team)) {
                    if (piece.isLegalMove(x, y, kingX, kingY, this)) {
                        return true; // King is in check
                    }
                }
            }
        }

        return false;
    }

    public boolean isCheckmate(String team, int kingX, int kingY) {
        // check if king has legal moves to escape check
        if (canEscape(team, kingX, kingY)) {
            return false;
        }

        // check if any piece can block the check or capture attacker
        if (canBlockOrCapture(team, kingX, kingY)) {
            return false;
        }

        return true;
    }

    public boolean canEscape(String team, int kingX, int kingY) {
        // check possible legal moves for king
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < dx.length; i++) {
            int newX = kingX + dx[i];
            int newY = kingY + dy[i];

            if (isValidCoords(newX, newY)) {
                Tile destTile = getTileAt(newX, newY);
                ChessPiece destPiece = destTile.getPiece();

                if (!destTile.isOccupied() || destTile.isEnemy(team)) {
                    //simulate move
                    Tile kingTile = getTileAt(kingX, kingY);
                    destTile.setPiece(kingTile.getPiece());
                    kingTile.setPiece(null);

                    boolean stillInCheck = isCheck(team, newX, newY);

                    //undo the move
                    kingTile.setPiece(destTile.getPiece());
                    destTile.setPiece(destPiece);

                    if (!stillInCheck) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean canBlockOrCapture(String team, int kingX, int kingY) {
        ArrayList<PiecePosition> attackers = getAttackingPieces(team, kingX, kingY);

        // cannot block or capture to avoid check if multiple attackers
        if (attackers.size() > 1) {
            return false;
        }

        PiecePosition attackerPosition = attackers.get(0);
        ChessPiece attacker = attackerPosition.piece;
        int attackerX = attackerPosition.x;
        int attackerY = attackerPosition.y;

        // check if each friendly piece can block the attack or capture attacker
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = getTileAt(x, y);
                ChessPiece piece = tile.getPiece();
                if (piece != null && piece.getTeam().equals(team) && !(piece instanceof King)) {

                    // check if piece can capture attacker
                    if(piece.isLegalMove(x, y, attackerX, attackerY, this)) {
                        // simulate the move
                        Tile destTile = getTileAt(attackerX, attackerY);
                        destTile.setPiece(piece);
                        tile.setPiece(null);

                        boolean isStillCheck = isCheck(team, kingX, kingY);

                        //undo the move
                        tile.setPiece(piece);
                        destTile.setPiece(attacker);

                        if (!isStillCheck) {
                            return true;
                        }
                    }

                    // to do: check if piece can block the check
                }
            }
        }
        return false;
    }

    private ArrayList<PiecePosition> getAttackingPieces(String team, int kingX, int kingY) {
        ArrayList<PiecePosition> attackers = new ArrayList<>();

        // find enemy pieces attacking the king
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = getTileAt(x, y);
                ChessPiece piece = tile.getPiece();
                if (piece != null && tile.isEnemy(team)) {
                    if (piece.isLegalMove(x, y, kingX, kingY, this)) {
                        attackers.add(new PiecePosition(piece, x, y));
                    }
                }
            }
        }
        return attackers;
    }

    private boolean isValidCoords(int x, int y) {
        return x >= 0 && x < 7 && y >=0 && y < 8;
    }

    public int checkState(String team) {
        int kingX = -1;
        int kingY = -1;
        boolean kingFound = false;

        // find king
        for (int y = 0; y < 8 && !kingFound; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece piece = getTileAt(x, y).getPiece();
                if (piece instanceof King && piece.getTeam().equals(team)) {
                    kingX = x;
                    kingY = y;
                    kingFound = true;
                    break;
                }
            }
        }

        // 0 : nothing
        // 1 : check
        // 2 : checkmate
        if (isCheck(team, kingX, kingY)) {
            if (isCheckmate(team, kingX, kingY)) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public ArrayList<Tile> getBoard() {
        return this.board;
    }
}
