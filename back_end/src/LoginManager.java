import java.util.HashMap;
import java.util.Map;

public class LoginManager {

    private final InventoryDatabase database;  // reference to your user database
    private final Map<String, Boolean> loggedInUsers = new HashMap<>(); // session tracking

    public LoginManager(InventoryDatabase database) {
        this.database = database;
    }

    // Attempt to login a user with ID and password.
    // Returns true if authentication succeeds.

    public boolean login(String userID, String password) {
        User user = database.getUsers().get(userID);
        if (user == null) {
            System.out.println("User does not exist.");
            return false;
        }

        if (!user.checkPassword(password)) {
            System.out.println("Incorrect password for user " + userID);
            return false;
        }

        loggedInUsers.put(userID, true);  // mark as logged in
        System.out.println("User " + userID + " logged in successfully.");
        return true;
    }

    // Logout a user
    public void logout(String userID) {
        loggedInUsers.remove(userID);
        System.out.println("User " + userID + " logged out.");
    }

    // Check if a user is currently logged in.
    public boolean isLoggedIn(String userID) {
        return loggedInUsers.getOrDefault(userID, false);
    }

    
    // Check if a user exists and is authenticated.
    // Useful for restricting actions.
    public boolean authenticate(String userID) {
        if (!isLoggedIn(userID)) {
            System.out.println("User " + userID + " is not logged in.");
            return false;
        }
        return true;
    }


     // Optional: Check if a user has admin permissions.
    
    public boolean isAdmin(String userID) {
        User user = database.getUsers().get(userID);
        return user != null && user.getPermissions() == User.Permissions.admin;
    }
}
