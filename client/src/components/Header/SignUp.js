import React, { useState } from 'react';
import { sendAPIRequest, getOriginalServerUrl } from '../../utils/restfulAPI';
import { Button, Col, Container, Input, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';

export default function SignUp(props) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState(''); // Error state

    const [validSignUp, setValidSignUp] = useState(true); 

    function closeModalWithoutSigningUp() {
        setUsername('');
        setPassword('');
        setConfirmPassword('');
        props.toggleSignUp(); 
    }
/*
    function handleSignUp() {
        if (isValidSignUp(username, password, confirmPassword)) {
            props.processSignUp(username, password);
            closeModalWithoutSigningUp();
        } else {
            setValidSignUp(false); 
        }
    }
        */
    async function handleSignUp() {
        const serverUrl = getOriginalServerUrl();
        const requestBody = {
            requestType: 'createuser',
            username,
            password,
            confirmPassword,
        };

        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            if (response && response.userID >= 0) {
                setPassword(response.str);
                setValidSignUp(true);
                setErrorMessage(response.response);
                props.onLoginSuccess(response.userID); // Notify parent about successful login
            } else {
                setValidSignUp(false);
                setErrorMessage(response.response);
            }
        } catch (err) {
            console.error('Error sending login request:', err);
            setValidSignUp(false);
            setErrorMessage('Failed to connect to the server.');
        }
    }

    
    return (
        <Modal isOpen={props.showSignUp} toggle={props.toggleSignUp}>
            <SignUpHeader toggleOpen={props.toggleSignUp} />
            <SignUpBody
                username={username}
                setUsername={setUsername}
                password={password}
                setPassword={setPassword}
                confirmPassword={confirmPassword}
                setConfirmPassword={setConfirmPassword}
                validSignUp={validSignUp}
                errorMessage={errorMessage} // Pass error message
            />
            <SignUpFooter
                handleSignUp={handleSignUp}
                toggleSignUp={props.toggleSignUp} // Pass toggleSignUp prop
                openLoginModal={props.openLoginModal} // Pass openLoginModal prop
            />
        </Modal>
    );
    
}

function SignUpHeader(props) {
    return (
        <ModalHeader className="ml-2" toggle={props.toggleOpen}>
            Sign Up
        </ModalHeader>
    );
}

function SignUpBody(props) {
    return (
        <ModalBody>
            <Container>
                <LoginRow 
                    label="Username" 
                    value={
                        <Input
                            value={props.username || ''}
                            placeholder="Enter your username"
                            onChange={(e) => { props.setUsername(e.target.value) }}
                            invalid={!props.validSignUp && !props.username}
                        />
                    } 
                />
                <LoginRow 
                    label="Password" 
                    value={
                        <Input
                            type="password"
                            value={props.password || ''}
                            placeholder="Enter your password"
                            onChange={(e) => { props.setPassword(e.target.value) }}
                            invalid={!props.validSignUp && !props.password}
                        />
                    }
                />
                <LoginRow 
                    label="Confirm Password" 
                    value={
                        <Input
                            type="password"
                            value={props.confirmPassword || ''}
                            placeholder="Confirm your password"
                            onChange={(e) => { props.setConfirmPassword(e.target.value) }}
                            invalid={!props.validSignUp && !props.confirmPassword}
                        />
                    }
                />
                {props.errorMessage && (
                    <Row className="text-danger mt-2">
                        <Col>{props.errorMessage}</Col>
                    </Row>
                )}
            </Container>
        </ModalBody>
    );
}

function SignUpFooter(props) {
    return (
        <ModalFooter>
            <Button
                color="secondary"
                onClick={() => {
                    props.toggleSignUp(); // Close signup modal
                    props.openLoginModal(); // Open login modal
                }}
            >
                Back to Login
            </Button>
            <Button color="primary" onClick={props.handleSignUp}>
                Sign Up
            </Button>
        </ModalFooter>
    );
}


function LoginRow({ label, value }) {
    return (
        <Row className='my-2 vertical-center'>
            <Col xs={3}>
                {label}:
            </Col>
            <Col xs={9}>
                {value}
            </Col>
        </Row>
    );
}

function isValidSignUp(username, password, confirmPassword) {
    return username && password && confirmPassword && password === confirmPassword;
}
