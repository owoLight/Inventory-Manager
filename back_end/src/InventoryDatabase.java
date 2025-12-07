import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

//================================================================
// This class acts as a database for InventoryManager. tracks items, users, and checkout records
//================================================================

public class InventoryDatabase {
    private final Map<String, Item> items = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private final List<CheckoutSystem> records = new ArrayList<>();

    private final TreeSet<Integer> availableIds = new TreeSet<>();
    private int nextId = 0;


    // Generate a unique user ID with appropriate prefix.
    public String generateUserID(User.Permissions perms) {
        int idNumber;
        if (!availableIds.isEmpty()) {
            idNumber = availableIds.pollFirst();
        } else { idNumber = nextId++; }
        return (perms == User.Permissions.admin ? "A-" : "U-") + String.format("%04d", idNumber);
    }

    // Release a user ID back to the pool of available IDs.
    public void releaseUserID(String fullIDString) {
        try {
            String[] parts = fullIDString.split("-");
            int idNumber = Integer.parseInt(parts[1]);
            availableIds.add(idNumber);
        } catch (NumberFormatException e) {
            System.out.println("Error releasing user ID: " + fullIDString);
        }
    }

    // search functions
    public List<Item> searchItemsByName(String keyword) {
        List<Item> results = new ArrayList<>();

        // simple case-insensitive search
        for (Item item : items.values()) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    public List<Item> searchItemsByType(Class<?> type) {
        List<Item> results = new ArrayList<>();
         for (Item item : items.values()) {
            if (type.isInstance(item)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<Item> searchItemsByAvailability(boolean available) {
        List<Item> results = new ArrayList<>();
        for (Item item : items.values()) {
            boolean isCheckedOut = false;
            for (CheckoutSystem record : records) {
                if (record.getItemId().equals(item.getId()) && !record.isReturned()) {
                    isCheckedOut = true;
                    break;
                }
            }
            if (available != isCheckedOut) {
                results.add(item);
            }
        }
        return results;
    }

    // Accessors for InventoryManager
    public Map<String, Item> getItems() { return items; }
    public Map<String, User> getUsers() { return users; }
    public List<CheckoutSystem> getRecords() { return records; }
}