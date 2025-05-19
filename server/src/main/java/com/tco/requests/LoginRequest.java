package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(LoginRequest.class);
    private String username;
    private String password;
    private Integer userID;
    private String response;

    //protected String requestType;

    public LoginRequest() {
        super.requestType = "login";
    }

    public LoginRequest(String username, String password) {
        super.requestType = "login";
        this.username = username;
        this.password = password;
    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        UserDatabase db = new UserDatabase();
        System.out.println("Logging In");
        
        //commented out for now as my (Ethan L.) implementations are slightly different
        this.userID = db.validateCredentials(this.username, this.password, false);
        System.out.println(this.userID);
        //boolean isValid = db.validateCredentials(this.username, this.password);
        if (this.userID >= 0){
            this.response = "Logged In";
        } else {
            this.response = "Incorrect Username or Password";
        }
        
        log.trace("buildResponse -> {}", this);
    }

}
