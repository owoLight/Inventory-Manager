// =======================================================
// this class manages the interaction service for the inventory.
// this includes adding items, registering users, deleting users, checking out items, and returning items.
// =======================================================


public class InventoryManager {
    private final InventoryDatabase database;

    public InventoryManager(InventoryDatabase database) {
        this.database = database;
    }

    // add an item to the inventory
    public void addItem(Item item) {
        database.getItems().put(item.getId(item), item);
    }

    // registers a user to the system
    public User createAndRegisterUser(String id, String name, String email, User.Permissions perms) {
        
        // check if email is valid
        if (!User.isValidEmail(email)) {
            System.out.println("Invalid email: " + email);
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
        User user = new User(prefixedId, name, email, perms);
        database.getUsers().put(User.getId(user), user);

        System.out.println("User created and registered: " + User.getName(user) + " (" + User.getId(user) + ")");
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

    // checkout an item for a user
    public boolean checkoutItem(String itemID, String userID) {
        Item item = database.getItems().get(itemID);

        // item is unable to be checked out
        if (item == null || !item.isAvailable) return false;

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

        for (CheckoutSystem r : database.getRecords()) {
            if (r.getItemId().equals(itemID) && !r.isReturned() && r.getUserId().equals(userID)) {
                r.setReturnDate(java.time.LocalDate.now());
                found = true;
                break;
            }
        }
        if (!found) return false; // item user pair is not found
        else {
            item.setAvailability(true); // item user pair found
            return true;
        }
        
    }
}