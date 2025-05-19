import java.util.ArrayList;

public class User {
    private int userID;
    private String userPassword;
    private String userEmail;
    //Line below commented out until Match class implemented
    //private ArrayList<Match> matchHistory;

    public User(int userID, String userPassword, String userEmail) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        //this.matchHistory = new ArrayList<>();
    }

    public boolean loginValidation() {
        return false;
    }

    public int getUserID() {
        return this.userID;
    }
}
