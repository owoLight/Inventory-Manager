package com.labinventory.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      // DB primary key

    @Column(unique = true, nullable = false)
    private String username;  // login name

    private String password;  // encoded password
    private String email;

    // NEW: PNW student id / PUID
    private String puid;

    @Enumerated(EnumType.STRING)
    private Permissions perms = Permissions.normal;

    public enum Permissions {
        admin, normal
    }

    public User() {}

    public User(String username, String password, String email, Permissions perms) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.perms = perms;
    }

    // ---- getters ----

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPuid() {
        return puid;
    }

    public Permissions getPerms() {
        return perms;
    }

    // ---- setters ----

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public void setPerms(Permissions perms) {
        this.perms = perms;
    }
}
