import React from 'react';
import { Container, Button } from 'reactstrap';
import { CLIENT_TEAM_NAME } from '../../utils/constants';
import { useToggle } from '../../hooks/useToggle';
import Menu from './Menu';
import Settings from './Settings';
import Login from './Login';
import { IoMdClose } from 'react-icons/io'
import { BsSignIntersection } from 'react-icons/bs';
import SignUp from './SignUp';

export default function Header(props) {
    const [showSettings, toggleSettings] = useToggle(false);
    const [showLogin, toggleLogin] = useToggle(false);
    const [showSignUp, toggleSignUp] = useToggle(false);

    const toggles = {toggleSettings, toggleLogin, toggleSignUp}
    const shows = {showSettings, showLogin, showSignUp}

    return (
        <React.Fragment>
            <HeaderContents
                {...toggles}
                {...props}
            />
            <AppModals
                {...shows}
                {...toggles}
                {...props}
            />
        </React.Fragment>
    );
}

function HeaderContents(props) {
    return (
        <div className='full-width header vertical-center'>
            <Container>
            <div className='header-container'>
                <h1
                    className='tco-text-upper header-title'
                    data-testid='header-title'
                >
                    {CLIENT_TEAM_NAME}
                </h1>
                <HeaderButton {...props} />
            </div>
            </Container>
        </div>
    );
}

function HeaderButton(props) {
    return (
        <Menu {...props} />
    );
}

function AppModals(props) {
    return (
        <>
            <Settings
                {...props}
            />
            <Login
                showLogin={props.showLogin}
                toggleLogin={props.toggleLogin}
                {...props}
            />
            <SignUp
                showSignUp={props.showSignUp}
                toggleSignUp={props.showSignUp}
                {...props}
            />
        </>
    );
}