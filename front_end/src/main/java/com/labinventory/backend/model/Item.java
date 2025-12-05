package com.labinventory.backend.model;

// =======================================================
// this class represents a generic item in the inventory.
// =======================================================
public class Item {
    protected String id;
    protected String name;
    protected String description;
    private boolean available = true;

    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean bool) {
        this.available = bool;
    }
}
