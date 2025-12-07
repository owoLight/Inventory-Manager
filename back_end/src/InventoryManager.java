
// =======================================================
// this class manages the interaction service for the inventory.
// this includes adding items, registering users, deleting users, checking out items, and returning items.
// =======================================================

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class InventoryManager {
    private final InventoryDatabase database;
    private final LoginManager logins;

    public InventoryManager(InventoryDatabase database, LoginManager logins) {
        this.database = database;
        this.logins = logins;
    }

//================
// USER METHODS
//================

    // registers a user to the system
    public User createAndRegisterUser(String id, String name, String email, String password, User.Permissions perms) {
        
        // check if email is valid
        if (!User.isValidEmail(email)) {
            System.out.println("Invalid email: " + email);
            return null;
        }

        if(!User.isValidPassword(password)){
            System.out.println("Invalid password: " + password);
            return null;
        }

        // create prefixed ID
        String prefixedId = (perms == User.Permissions.admin ? "A-" : "U-") + id;

        // Check uniqueness in the database
        if (database.getUsers().containsKey(prefixedId)) {
            System.out.println("ID already exists: " + prefixedId);
            return null;
        }

        // Pass prefixed ID to User constructor
        User user = new User(prefixedId, name, email, password, perms);
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

//================
// ITEM METHODS
//================

    // add an item to the inventory
    public void addItem(Item item) {
        database.getItems().put(item.getId(), item);
    }

    // checkout an item for a user
    public boolean checkoutItem(String itemID, String userID) {
        Item item = database.getItems().get(itemID);

        // item is unable to be checked out
        if (item == null || !item.isAvailable) {
            System.out.println("The item is already checked out or does not exist.");
            return false;
        }

        // check if user exists and is logged in
        if (!database.getUsers().containsKey(userID) || !logins.isLoggedIn(userID)) {
            System.out.println("User with ID " + userID + " does not exist.");
            return false;
        }

        // check if user already has this item checked out
        for (CheckoutSystem r : database.getRecords()) {
            if (r.getItemId().equals(itemID) &&
                r.getUserId().equals(userID) &&
                !r.isReturned()) 
            {
                System.out.println("User already has this item checked out.");
                return false;
            }
        }

        // check out item and make it unavailable
        item.setAvailability(false);
        CheckoutSystem record = new CheckoutSystem(
                itemID, userID,
                java.time.LocalDate.now(),
                java.time.LocalDate.now().plusDays(7)
        );
        // add it to records
        database.getRecords().add(record);
        return true;
    }

    public boolean returnItem(String itemID, String userID) {
        Item item = database.getItems().get(itemID);
        if (item == null) return false;

        boolean found = false; // keep track of if the item user pair exists
        CheckoutSystem foundRecord = null;
        
        // check if user exists
        if (!database.getUsers().containsKey(userID)) {
            System.out.println("User cannot return item because User with ID: " + userID + " does not exist.");
            return false;
        }
        
        // check if the item user pair exists in records
        for (CheckoutSystem r : database.getRecords()) {
            if (r.getItemId().equals(itemID) && !r.isReturned() && r.getUserId().equals(userID)) {
                r.setReturnDate(java.time.LocalDate.now());
                foundRecord = r;
                found = true;
                break;
            }
        }

        if (!found || foundRecord == null) return false; // item user pair is not found
        
        item.setAvailability(true); // item user pair found
        
        // Check if item was overdue and incur penalty points
        LocalDate today = LocalDate.now();
        LocalDate due = foundRecord.getDueDate();

        if (today.isAfter(due)) {
            int penalty = calculatePenaltyPoints(today, due);

            User user = database.getUsers().get(userID);
            user.addPenaltyPoints(penalty);

            System.out.println("Item was returned late! Penalty: " + penalty + " points added.");
        }

        System.out.println("Item returned successfully.");
        return true;
        
    }

    // get all overdue items
    public List<CheckoutSystem> getOverdueItems() {
        return database.getRecords().stream()
                .filter(r -> r.isOverdue())
                .toList();
    }

    private int calculatePenaltyPoints(LocalDate returnDate, LocalDate dueDate) {
    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

    // 1 penalty for every 7 days late — first day counts as late
    // Example: 1–7 days late = 1 point, 8–14 = 2 points, etc.
    return (int) Math.ceil(daysLate / 7.0);
}


}