public class User {
    private String id;
    private String name;
    private String email;
    private Permissions perms = Permissions.normal;

    // constructor
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // getters
    public String getId(User user) {
        return user.id;
    }

    // methods
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    private enum Permissions {
        admin, normal
    }

}
