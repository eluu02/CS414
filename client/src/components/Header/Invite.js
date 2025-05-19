import React, { useState } from 'react';
import {
    Button,
    Col,
    Container,
    Input,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader,
    Row,
} from 'reactstrap';
import { sendAPIRequest, getOriginalServerUrl } from '../../utils/restfulAPI';

export default function MatchInvitePopup({ showPopup, togglePopup, userID }) {
    const [opponents, setOpponents] = useState(['']); // Dynamic list of opponent usernames

    async function handleCreateInvitation() {
        // Logic for creating the match invitation
        console.log('Creating match invitation:', { opponents, userID });
        const opponent = opponents.at(0);
        const serverUrl = getOriginalServerUrl();
        const requestBody = {
            requestType: 'createinv',
            userID,
            opponent,
        };

        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            if (response && response.sucess) {
                togglePopup();
            } else {
                setErrorMessage(response.response);
            }
        } catch (err) {
            console.error('Error sending invite request:', err);
            setErrorMessage('Failed to connect to the server.');
        }
        togglePopup(); // Close the popup after creating the invitation
    };

    const handleAddOpponent = () => {
        setOpponents([...opponents, '']); // Add an empty input for a new opponent
    };

    const handleOpponentChange = (index, value) => {
        const updatedOpponents = [...opponents];
        updatedOpponents[index] = value || ''; // Ensure the value is never undefined
        setOpponents(updatedOpponents);
    };


    return (
        <Modal isOpen={showPopup} toggle={togglePopup}>
            <ModalHeader toggle={togglePopup}>Create Match Invitation</ModalHeader>
            <ModalBody>
                <Container>
                    {opponents.map((opponent, index) => (
                        <Row key={index} className="my-2">
                            <Col xs={3}>{index === 0 ? 'Opponent:' : ''}</Col>
                            <Col xs={9}>
                                <Input
                                    type="text"
                                    placeholder={`Enter opponent's username ${index + 1}`}
                                    value={opponent}
                                    onChange={(e) => handleOpponentChange(index, e.target.value)}
                                />
                            </Col>
                        </Row>
                    ))}
                    <Row className="my-2">
                        <Col xs={12} className="text-right">
                            <Button color="success" size="sm" onClick={handleAddOpponent}>
                                + Add Opponent
                            </Button>
                        </Col>
                    </Row>
                </Container>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={togglePopup}>
                    Cancel
                </Button>
                <Button color="primary" onClick={handleCreateInvitation}>
                    Create Invitation
                </Button>
            </ModalFooter>
        </Modal>
    );
}
