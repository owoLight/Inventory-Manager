// ========================================================
// this class represents a user in the inventory management system.
// ========================================================

public class User {
    private final String id;
    private String name;
    private String email;
    private Permissions perms = Permissions.normal;

    public enum Permissions {
        admin, normal
    }

    // constructor
    public User(String id, String name, String email, Permissions perms) {
        
        // assign name, id, and email
        this.name = name;
        this.id = id;
        this.email = email;

        // assign permissions
        this.perms = perms;
    }

    // getters
    public static String getId(User user) {
        return user.id;
    }
    public static String getName(User user) {
        return user.name;
    }
    public static String getEmail(User user) {
        return user.email;
    }
    public Permissions getPermissions() {
        return perms;
    }

    // methods
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    public static boolean isUniqueId(String id) {
        // In a real system, this method would check against a database or data structure
        // to ensure the ID is unique. Here, we'll assume all IDs passed are unique for simplicity.
        return true;
    }

    public static void changeEmail(User user, String newEmail) {
        if (isValidEmail(newEmail)) {
            user.email = newEmail;
        } else {
            System.out.println("Invalid email format. Email not changed.");
        }
    }

    public static void changeName(User user, String newName) {
        user.name = newName;
    }

}
