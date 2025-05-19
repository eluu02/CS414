import React from 'react';
import './matchHistory.css'; // CSS for styling the MatchHistory component

export default function MatchHistory({ matches, sentInvites, receivedInvites, onAccept, onReject, onMatchClick }) {
    return (
        <div className="match-history-container">
            <h3>Match History</h3>
            <div className="match-section">
                <h4>Matches</h4>
                <ul className="match-list">
                    {matches.length > 0 ? (
                        matches.map((match, index) => (
                            <li
                                key={index}
                                className="match-item"
                                onClick={() => onMatchClick(match.matchID)} // Use matchID for actions
                                style={{ cursor: 'pointer' }}
                            >
                                {match.text} {/* Render the formatted text */}
                            </li>
                        ))
                    ) : (
                        <li className="no-matches">No matches yet</li>
                    )}
                </ul>
            </div>
            <div className="invite-section">
                <h4>Sent Invites</h4>
                <ul className="invite-list">
                    {sentInvites.length > 0 ? (
                        sentInvites.map((invite, index) => (
                            <li key={index} className="invite-item">
                                {invite}
                            </li>
                        ))
                    ) : (
                        <li className="no-invites">No sent invites yet</li>
                    )}
                </ul>
            </div>
            <div className="invite-section">
                <h4>Received Invites</h4>
                <ul className="invite-list">
                    {receivedInvites.length > 0 ? (
                        receivedInvites.map((invite, index) => (
                            <li key={index} className="invite-item">
                                {invite.text}
                                <button
                                    className="accept-button"
                                    onClick={(e) => {
                                        e.stopPropagation(); // Prevent bubbling
                                        onAccept(invite.inviteID);
                                    }}
                                >
                                    ✔
                                </button>
                                <button
                                    className="reject-button"
                                    onClick={(e) => {
                                        e.stopPropagation(); // Prevent bubbling
                                        onReject(invite.inviteID);
                                    }}
                                >
                                    ✖
                                </button>
                            </li>
                        ))
                    ) : (
                        <li className="no-invites">No received invites yet</li>
                    )}
                </ul>
            </div>
        </div>
    );
}
