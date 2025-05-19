CREATE TABLE IF NOT EXISTS Users (
    userID          INT             NOT NULL,
    userName        VARCHAR(50)     NOT NULL,
    userPassword    VARCHAR(50)     NOT NULL,
    PRIMARY KEY (userID),
    UNIQUE KEY (userName)
);

CREATE TABLE IF NOT EXISTS Matches ( 
    matchID         INT             NOT NULL,
    ownerID         INT             NOT NULL,
    playerID        INT,
    playerTurn      INT,
    gameBoard       VARCHAR(8000)    NOT NULL,
    prevMove        VARCHAR(10),
    gameFinished    BOOLEAN         NOT NULL,
    matchScore      VARCHAR(50),
    endCondition    VARCHAR(50),
    startDateTime   DATETIME        NOT NULL,
    endDateTime     DATETIME,
    PRIMARY KEY(matchID),
);

CREATE TABLE IF NOT EXISTS Invitations (
    inviteID        INT     NOT NULL,
    ownerID         INT     NOT NULL,
    playerIDs       VARCHAR(8000),
    PRIMARY KEY(inviteID),
    FOREIGN KEY (ownerID) REFERENCES Users(userID)
);