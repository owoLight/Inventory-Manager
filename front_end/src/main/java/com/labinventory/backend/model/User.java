package com.labinventory.backend.model;

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

    public User(String id, String name, String email, Permissions perms) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.perms = perms;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Permissions getPermissions() {
        return perms;
    }

    public boolean isAdmin() {
        return perms == Permissions.admin;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
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
