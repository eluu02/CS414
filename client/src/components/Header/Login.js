import React, { useState } from 'react';
import { sendAPIRequest, getOriginalServerUrl } from '../../utils/restfulAPI';
import { Button, Col, Container, Input, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';

export default function Login(props) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [validLogin, setValidLogin] = useState(true); // Validation state
    const [errorMessage, setErrorMessage] = useState(''); // Error state

    function closeModalWithoutLoggingIn() {
        setUsername('');
        setPassword('');
        setValidLogin(true);
        setErrorMessage('');
        props.toggleLogin(); // Toggle login modal visibility
    }

    async function handleLogin() {
        const serverUrl = getOriginalServerUrl();
        const requestBody = {
            requestType: 'login',
            username,
            password,
        };

        try {
            const response = await sendAPIRequest(requestBody, serverUrl);
            if (response && response.userID >= 0) {
                setPassword(response.str);
                setValidLogin(true);
                setErrorMessage(response.response);
                props.onLoginSuccess(response.userID); // Notify parent about successful login
            } else {
                setValidLogin(false);
                setErrorMessage(response.response);
            }
        } catch (err) {
            console.error('Error sending login request:', err);
            setValidLogin(false);
            setErrorMessage('Failed to connect to the server.');
        }
    }

    return (
        <Modal isOpen={props.showLogin} toggle={closeModalWithoutLoggingIn}>
            <LoginHeader toggleOpen={closeModalWithoutLoggingIn} />
            <LoginBody
                username={username}
                setUsername={setUsername}
                password={password}
                setPassword={setPassword}
                validLogin={validLogin}
                errorMessage={errorMessage}
            />
            <LoginFooter
                handleLogin={handleLogin}
                openSignUpModal={props.openSignUpModal} // New prop for opening the signup modal
            />
        </Modal>
    );
}


function LoginHeader(props) {
    return (
        <ModalHeader className="ml-2" toggle={props.toggleOpen}>
            Login
        </ModalHeader>
    );
}

function LoginBody(props) {
    return (
        <ModalBody>
            <Container>
                <LoginRow
                    label="Username"
                    value={
                        <Input
                            value={props.username || ''}
                            placeholder="Enter your username"
                            onChange={(e) => props.setUsername(e.target.value)}
                            invalid={!props.validLogin && !props.username}
                        />
                    }
                />
                <LoginRow
                    label="Password"
                    value={
                        <Input
                            value={props.password || ''}
                            placeholder="Enter your password"
                            onChange={(e) => props.setPassword(e.target.value)}
                            invalid={!props.validLogin && !props.password}
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

function LoginRow({ label, value }) {
    return (
        <Row className="my-2 vertical-center">
            <Col xs={3}>{label}:</Col>
            <Col xs={9}>{value}</Col>
        </Row>
    );
}

function LoginFooter(props) {
    return (
        <ModalFooter>
            <Button 
                color="secondary" 
                onClick={props.openSignUpModal} // Trigger signup modal
            >
                Create an Account
            </Button>
            <Button color="primary" onClick={props.handleLogin}>
                Login
            </Button>
        </ModalFooter>
    );
}


/*
import React, { useState } from 'react';
import { sendAPIRequest, getOriginalServerUrl } from '@utils/restfulAPI';
import { Button, Col, Container, Input, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';

export default function Login(props) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [validLogin, setValidLogin] = useState(true); // For validation (assumed)

    function closeModalWithoutLoggingIn() {
        setUsername('');
        setPassword('');
        props.toggleLogin(); // Toggle login modal visibility
    }

    function handleLogin() {
        if (isValidLogin(username, password)) {
            props.processLogin(username, password);
            closeModalWithoutLoggingIn();
        } else {
            setValidLogin(false); // Indicate invalid login attempt
        }
    }

    return (
        <Modal isOpen={props.showLogin} toggle={closeModalWithoutLoggingIn}>
            <LoginHeader toggleOpen={closeModalWithoutLoggingIn} />
            <LoginBody
                username={username}
                setUsername={setUsername}
                password={password}
                setPassword={setPassword}
                validLogin={validLogin}
            />
            <LoginFooter
                handleLogin={handleLogin}
                closeModalWithoutLoggingIn={closeModalWithoutLoggingIn}
                validLogin={validLogin}
            />
        </Modal>
    );
}

function LoginHeader(props) {
    return (
        <ModalHeader className="ml-2" toggle={props.toggleOpen}>
            Login
        </ModalHeader>
    );
}

function LoginBody(props) {
    return (
        <ModalBody>
            <Container>
                <LoginRow 
                    label="Username" 
                    value={
                        <Input
                            value={props.username}
                            placeholder="Enter your username"
                            onChange={(e) => { props.setUsername(e.target.value) }}
                            invalid={!props.validLogin && !props.username}
                        />
                    } 
                />
                <LoginRow 
                    label="Password" 
                    value={
                        <Input
                            type="password"
                            value={props.password}
                            placeholder="Enter your password"
                            onChange={(e) => { props.setPassword(e.target.value) }}
                            invalid={!props.validLogin && !props.password}
                        />
                    }
                />
            </Container>
        </ModalBody>
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

function LoginFooter(props) {
    return (
        <ModalFooter>
            <Button color="secondary" onClick={props.closeModalWithoutLoggingIn}>Cancel</Button>
            <Button color="primary" onClick={props.handleLogin}>
                Login
            </Button>
        </ModalFooter>
    );
}

function isValidLogin(username, password) {
    // Implement your login validation logic here (e.g., non-empty fields)
    return username && password; // Example: both fields must be non-empty
}
*/