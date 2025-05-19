import React, { useState } from 'react';
import { Collapse } from 'reactstrap';
import { useToggle } from '../hooks/useToggle';
import Header from './Header/Header';
import Login from './Header/Login';
import SignUp from './Header/SignUp';
import Invite from './Header/Invite';
import './board.css';
import Chessboard from './Board';
import { useServerSettings } from '../hooks/useServerSettings';
import MatchHistory from './MatchHistory'; // Import MatchHistory
import { sendAPIRequest, getOriginalServerUrl } from '../utils/restfulAPI';


export default function Page(props) {
    const [serverSettings, processServerConfigSuccess] = useServerSettings(props.showMessage);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [showLogin, setShowLogin] = useState(true);
    const [showSignUp, setShowSignUp] = useState(false);
    const [showInvitePopup, setShowInvitePopup] = useState(false); // Centralize invite popup state
    const [matches, setMatches] = useState([]);
    const [selectedMatch, setSelectedMatch] = useState(null);
    const [sentInvites, setSentInvites] = useState([]);
    const [receivedInvites, setReceivedInvites] = useState([]);
    const [userID, setUserID] = useState(null);

    const toggleLogin = () => setShowLogin(!showLogin);
    const toggleSignUp = () => setShowSignUp(!showSignUp);
    const toggleInvitePopup = () => {
        setShowInvitePopup(!showInvitePopup); // Toggle popup visibility
        if (showInvitePopup) fetchMatchHistory(); // Fetch match history when closing
    };
    const fetchMatchHistory = async (id) => {
        const effectiveUserID = id || userID; // Fallback to state if id is not passed
        if (!effectiveUserID) {
            console.warn("UserID is not set. Cannot fetch match history.");
            return;
        }
        
        console.log("ID: ", id);
        
        const serverUrl = getOriginalServerUrl();
    
        try {
            // Fetch invite history
            const requestBody = {
                requestType: 'invhist',
                userID: effectiveUserID
            };
            
            const response = await sendAPIRequest(requestBody, serverUrl);
            console.log("Raw Invite History Response:", response);
    
            if (response) {
                // Parse sentInv and recInv from JSON strings
                const parsedSentInv = JSON.parse(response.sentInv || "[]");
                const parsedRecInv = JSON.parse(response.recInv || "[]");
    
                // Map over parsed data
                const formattedSentInvites = parsedSentInv.map(inv => {
                    return `Invite: ${inv.inviteID}\nTo: ${inv.invitedPlayersID.join(', ')}`;
                });
    
                
                const formattedReceivedInvites = parsedRecInv.map(inv => ({
                    text: `Invite: ${inv.inviteID}\nFrom: ${inv.ownerID}`, // Renderable text
                    inviteID: inv.inviteID, // ID for actions
                }));
                
                setSentInvites(formattedSentInvites);
                console.log("Updated Sent Invites:", formattedSentInvites);
                setReceivedInvites(formattedReceivedInvites);
                console.log("Updated Received Invites:", formattedReceivedInvites);
            } else {
                console.log("Failed to fetch invites.");
            }
    
            // Fetch match history
            const requestBody2 = {
                requestType: 'matchhist',
                userID: effectiveUserID,
            };
            const response2 = await sendAPIRequest(requestBody2, serverUrl);
            console.log("Raw Match History Response:", response2);
    
            if (response2) {
                // Parse matches from JSON string
                const parsedMatches = JSON.parse(response2.matches || "[]");
    
                // Map to desired format
                const formattedMatches = parsedMatches.map(match => ({
                    text: `Match: ${match.matchID} |\nWhite: ${match.ownerID}\nBlack: ${match.playerID}`,
                    matchID: match.matchID,
                }));
    
                setMatches(formattedMatches);
                console.log("Updated Matches:", formattedMatches);
            } else {
                console.log("Failed to fetch matches.");
                setMatches([]); // Fallback to an empty array
            }
        } catch (err) {
            console.error("Error fetching history:", err);
        }
    };
  
    const handleLoginSuccess = (id) => {
        setUserID(id);
        setIsLoggedIn(true);
        setShowLogin(false);
        fetchMatchHistory(id);
        
    };

    const handleAcceptInvite = async (inviteID) => {
        console.log(`Attempting to accept invite ${inviteID}`);

        const serverUrl = getOriginalServerUrl();
        const requestBody = {
            requestType: 'acceptinv',
            userID,
            inviteID,
        };
    
        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            if (response.success) {
                console.log(`Invite ${inviteID} accepted successfully.`);
                fetchMatchHistory(); // Refresh the invite list
            } else {
                console.error(`Failed to accept invite ${inviteID}`);
            }
        } catch (err) {
            console.error(`Error accepting invite ${inviteID}:`, err);
        }
            
    };
    
    const handleRejectInvite = async (inviteID) => {
        console.log(`Attempting to reject invite ${inviteID}`);

        
        const serverUrl = getOriginalServerUrl();
        const requestBody = {
            requestType: 'declineinv',
            userID,
            inviteID,
        };
    
        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            if (response.success) {
                console.log(`Invite ${inviteID} rejected successfully.`);
                fetchMatchHistory(); // Refresh the invite list
            } else {
                console.log(`Failed to reject invite ${inviteID}`);
            }
        } catch (err) {
            console.error(`Error rejecting invite ${inviteID}:`, err);
        }
            
    };

    const handleMatchOpen = async (matchID) => {
        console.log(`Match Opened: ${matchID}`);
        // Add any logic here for opening the match or navigating
        if(!matchID){
            return;
        }
        const serverUrl = getOriginalServerUrl();

        const requestBody = {
            requestType: 'openmatch',
            userID,
            matchID
        };
        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            console.log("Raw Match Response:", response);
    
            if (response && response.match) {
                // Parse the match from the JSON string
                const parsedMatch = JSON.parse(response.match);
    
                // Retain the original match object
                console.log("Parsed Match:", parsedMatch);
    
                // Update the state with the single match object
                setSelectedMatch(parsedMatch); // You can replace setMatches if you store this elsewhere
            } else {
                console.log("Failed to fetch match.");
            }
        } catch (err) {
            console.error(`Error finding match ${matchID}:`, err);
        }
    };
    
    const handleMove = async (move, matchID) => {
        console.log('Move triggered:', move);
        // Add logic to update the game state
        const serverUrl = getOriginalServerUrl();

        const requestBody = {
            requestType: 'movepiece',
            userID,
            matchID,
            move
        };
        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            console.log("Raw Match Response:", response);
    
            if (response) {
    
                // Retain the original match object
                console.log("Response:", response.response);
    
                handleMatchOpen(matchID);
            } else {
                console.log("Failed to fetch match.");
            }
        } catch (err) {
            console.error(`Error finding match ${matchID}:`, err);
        }
    }
    
    

    return (
        <>
            <Header {...{ serverSettings, processServerConfigSuccess }} />
            <MainContentArea
                matches={matches}
                sentInvites={sentInvites}
                receivedInvites={receivedInvites}
                toggleInvitePopup={toggleInvitePopup}
                showInvitePopup={showInvitePopup} // Pass popup state
                userID={userID}
                handleAcceptInvite={handleAcceptInvite}
                handleRejectInvite={handleRejectInvite}
                handleMatchOpen={handleMatchOpen}
                selectedMatch={selectedMatch}
                handleMove={handleMove}
            />
            {!isLoggedIn && (
                <Login
                    showLogin={showLogin}
                    toggleLogin={toggleLogin}
                    openSignUpModal={toggleSignUp}
                    onLoginSuccess={handleLoginSuccess}
                />
            )}
            {showSignUp && (
                <SignUp
                    showSignUp={showSignUp}
                    toggleSignUp={toggleSignUp}
                    openLoginModal={toggleLogin}
                    onSignUpSuccess={handleLoginSuccess}
                />
            )}
        </>
    );
}

function MainContentArea({ matches, sentInvites, receivedInvites, toggleInvitePopup, showInvitePopup, userID, handleAcceptInvite, handleRejectInvite, handleMatchOpen, selectedMatch, handleMove }) {
    return (
        <div className="main-content">
            <div className="content-container">
                <div className="left-container">
                    <button className="invite-button" onClick={toggleInvitePopup}>
                        Create Match Invitation
                    </button>
                    <MatchHistory 
                        matches={matches} 
                        sentInvites={sentInvites} 
                        receivedInvites={receivedInvites} 
                        onAccept={handleAcceptInvite}
                        onReject={handleRejectInvite}
                        onMatchClick={handleMatchOpen} 
                    />
                </div>
                <div className="right-container">
                    <Chessboard match={selectedMatch} onMove={handleMove} onRefresh={handleMatchOpen}  /> {/* Pass selected match to Chessboard */}
                </div>
            </div>
            {showInvitePopup && (
                <Invite
                    showPopup={showInvitePopup}
                    togglePopup={toggleInvitePopup}
                    userID={userID}
                />
            )}
        </div>
    );
}