// ========================================================
// this class represents a user in the inventory management system.
// ========================================================

public class User {
    private final String id;
    private String name;
    private String email;
    private Permissions perms = Permissions.normal;
    private String password;
    private int penaltyPoints = 0;

    public enum Permissions {
        admin, normal
    }

    // constructor
    public User(String id, String name, String email, String password, Permissions perms) {
        
        // assign attributes
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.perms = perms;
    }

    // getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() {return password; }
    public Permissions getPermissions() { return perms; }
    public int getPenaltyPoints() { return penaltyPoints; }

    // methods
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    public static boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit, one uppercase letter, and one lowercase letter
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(regex);
    }

    public boolean isUniqueId(String id) {
        // In a real system, this method would check against a database or data structure
        // to ensure the ID is unique. Here, we'll assume all IDs passed are unique for simplicity.
        return true;
    }

    public void changeEmail(String newEmail) {
        if (isValidEmail(newEmail)) {
            email = newEmail;
        } else {
            System.out.println("Invalid email format. Email not changed.");
        }
    }

    public void changePassword(String newPassword) {
        if (isValidPassword(newPassword)) {
            password = newPassword;
        } else {
            System.out.println("Invalid password format. Password not changed.");
        }
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void changeName(String newName) {
        name = newName;
    }

    public void addPenaltyPoints(int points) {
        penaltyPoints += points;
    }

}
