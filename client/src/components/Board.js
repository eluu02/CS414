import React, { useState } from 'react';
import './board.css'; // Optional: Create a separate CSS file for styling the chessboard

export default function Chessboard({ match, onMove, onRefresh }) {
    const [selectedFrom, setSelectedFrom] = useState(null); // Track the selected "from" cell
    const [selectedTo, setSelectedTo] = useState(null); // Track the selected "to" cell

    const handleCellClick = (x, y, hasPiece) => {
        if (selectedFrom === null && hasPiece) {
            // Select a cell with a piece
            setSelectedFrom({ x, y });
        } else if (selectedFrom !== null) {
            // Select the target cell
            setSelectedTo({ x, y });
        }
    };

    const handleMove = () => {
        if (selectedFrom && selectedTo) {
            const moveString = `${selectedFrom.x}${selectedFrom.y}${selectedTo.x}${selectedTo.y}`;
            onMove(moveString, match.matchID); // Trigger the onMove function with the move string
            // Reset selections
            setSelectedFrom(null);
            setSelectedTo(null);
        }
    };

    const renderChessboard = () => {
        const gameboardMap = {}; // Initialize gameboardMap as an empty object

        // Populate gameboardMap only if match and gameboard exist
        if (match?.gameboard) {
            const pieces = match.gameboard.split(',').filter(piece => piece);
            pieces.forEach(piece => {
                const pieceType = piece[0]; // Piece type (R, N, B, Q, K, P)
                const pieceColor = piece[1]; // Piece color/team (W or B)
                const x = parseInt(piece[2], 10); // X-coordinate
                const y = parseInt(piece[3], 10); // Y-coordinate
                gameboardMap[`${x}-${y}`] = { pieceType, pieceColor };
            });
        }

        const rows = [];
        for (let row = 0; row < 8; row++) {
            const squares = [];
            for (let col = 0; col < 8; col++) {
                const isDark = (row + col) % 2 === 1;
                const pieceData = gameboardMap[`${col}-${row}`];
                const piece = pieceData ? pieceData.pieceType : '';
                const pieceColor = pieceData ? pieceData.pieceColor : '';
                const isSelectedFrom = selectedFrom?.x === col && selectedFrom?.y === row;
                const isSelectedTo = selectedTo?.x === col && selectedTo?.y === row;
                squares.push(
                    <div
                        key={`${row}-${col}`}
                        className={`square ${isDark ? 'dark' : 'light'} ${
                            isSelectedFrom ? 'selected-from' : ''
                        } ${isSelectedTo ? 'selected-to' : ''}`}
                        onClick={() => handleCellClick(col, row, !!pieceData)}
                    >
                        {piece && (
                            <span className={`piece ${pieceColor === 'W' ? 'white' : 'black'}`}>
                                {piece}
                            </span>
                        )}
                    </div>
                );
            }
            rows.push(
                <div key={row} className="row">
                    {squares}
                </div>
            );
        }
        return rows;
    };

    return (
        <div className="chessboard-container">
            <div className="chessboard">
                <h3>Match ID: {match?.matchID || 'No Match Selected'}</h3>
                {renderChessboard()}
            </div>
            <div className="button-container">
                <button
                    className="move-button"
                    onClick={handleMove}
                    disabled={!selectedFrom || !selectedTo}
                >
                    Move
                </button>
                <button className="refresh-button" onClick={() => onRefresh(match.matchID)}>
                    Refresh Game
                </button>
            </div>
        </div>
    );
}


/*
import React from 'react';
import './board.css'; // Optional: Create a separate CSS file for styling the chessboard

export default function Chessboard({ match }) {
    const renderChessboard = () => {
        const gameboardMap = {}; // Initialize gameboardMap as an empty object
    
        // Populate gameboardMap only if match and gameboard exist
        if (match?.gameboard) {
            const pieces = match.gameboard.split(',').filter(piece => piece);
            pieces.forEach(piece => {
                const pieceType = piece[0]; // Piece type (R, N, B, Q, K, P)
                const pieceColor = piece[1]; // Piece color/team (W or B)
                const x = parseInt(piece[2], 10); // X-coordinate
                const y = parseInt(piece[3], 10); // Y-coordinate
                gameboardMap[`${x}-${y}`] = { pieceType, pieceColor };
            });
        }
    
        // Render the 8x8 chessboard
        const rows = [];
        for (let row = 0; row < 8; row++) {
            const squares = [];
            for (let col = 0; col < 8; col++) {
                const isDark = (row + col) % 2 === 1;
                const pieceData = gameboardMap[`${col}-${row}`];
                const piece = pieceData ? pieceData.pieceType : '';
                const pieceColor = pieceData ? pieceData.pieceColor : '';
                squares.push(
                    <div
                        key={`${row}-${col}`}
                        className={`square ${isDark ? 'dark' : 'light'}`}
                    >
                        {piece && (
                            <span className={`piece ${pieceColor === 'W' ? 'white' : 'black'}`}>
                                {piece}
                            </span>
                        )}
                    </div>
                );
            }
            rows.push(
                <div key={row} className="row">
                    {squares}
                </div>
            );
        }
        return rows;
    };

    return (
        <div className="chessboard">
            <h3>Match ID: {match?.matchID || 'No Match Selected'}</h3>
            {renderChessboard()}
        </div>
    );
}
*/