package com.labinventory.backend.service;

import java.util.Collection;

import com.labinventory.backend.model.CheckoutSystem;
import com.labinventory.backend.model.InventoryDatabase;
import com.labinventory.backend.model.Item;
import com.labinventory.backend.model.User;

public class InventoryManager {

    private final InventoryDatabase database;

    public InventoryManager(InventoryDatabase database) {
        this.database = database;
    }

    // add an item to the inventory
    public void addItem(Item item) {
        database.getItems().put(item.getId(), item);
    }

    // registers a user to the system
    public User createAndRegisterUser(String id, String name, String email, User.Permissions perms) {
        if (!User.isValidEmail(email)) {
            System.out.println("Invalid email: " + email);
            return null;
        }

        String prefixedId = (perms == User.Permissions.admin ? "A-" : "U-") + id;

        if (database.getUsers().containsKey(prefixedId)) {
            System.out.println("ID already exists: " + prefixedId);
            return null;
        }

        User user = new User(prefixedId, name, email, perms);
        database.getUsers().put(user.getId(), user);

        System.out.println("User created and registered: " + user.getName() + " (" + user.getId() + ")");
        return user;
    }

    public boolean removeUser(String userID) {
        if (!database.getUsers().containsKey(userID)) {
            System.out.println("User with ID " + userID + " does not exist.");
            return false;
        }
        database.getUsers().remove(userID);
        System.out.println("User with ID " + userID + " removed successfully.");
        return true;
    }

    public boolean checkoutItem(String itemID, String userID) {
        Item item = database.getItems().get(itemID);
        if (item == null || !item.isAvailable()) return false;

        item.setAvailability(false);
        CheckoutSystem record = new CheckoutSystem(
                itemID, userID,
                java.time.LocalDate.now(),
                java.time.LocalDate.now().plusDays(7)
        );
        database.getRecords().add(record);
        return true;
    }

    public boolean returnItem(String itemID, String userID) {
        Item item = database.getItems().get(itemID);
        if (item == null) return false;

        boolean found = false;

        for (CheckoutSystem r : database.getRecords()) {
            if (r.getItemId().equals(itemID) && !r.isReturned() && r.getUserId().equals(userID)) {
                r.setReturnDate(java.time.LocalDate.now());
                found = true;
                break;
            }
        }
        if (!found) return false;

        item.setAvailability(true);
        return true;
    }

    public Collection<Item> getAllItems() {
        return database.getItems().values();
    }
}
