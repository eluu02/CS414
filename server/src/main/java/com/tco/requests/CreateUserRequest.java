
package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(CreateUserRequest.class);
    private String username;
    private String password;
    private String confirmPassword;
    private Integer userID;
    private String response;

    //protected String requestType;

    public CreateUserRequest() {
        super.requestType = "createuser";
    }

    public CreateUserRequest(String username, String password, String confirmPassword) {
        super.requestType = "createuser";
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }

    public String getRequestType() {
        return requestType;
    }

    // Overrideable Methods
    @Override
    public void buildResponse() throws BadRequestException {
        // backend logic
        System.out.println("Creating Account");
        UserDatabase db = new UserDatabase();
        
        //commented out for now as my (Ethan L.) implementations are slightly different
        this.userID = db.validateCredentials(this.username, this.password, true);
        
        //boolean isValid = db.validateCredentials(this.username, this.password);
        if (this.userID >= 0){
            this.response = "Account Created";
        } else {
            this.response = "Unable To Create Account";
        }
        
        log.trace("buildResponse -> {}", this);
    }

}

