import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private Map<String, Item> items = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<CheckoutSystem> records = new ArrayList<>();

    public void addItem(Item item) {
        items.put(item.getId(item), item);
    }

    public void registerUser(User user) {
        users.put(user.getId(user), user);
    }

    public boolean checkoutItem(String itemID, String userID) {
        Item item = items.get(itemID);

        if (item == null || !item.isAvailable()) return false;

        item.setAvailability(false);
        CheckoutSystem record = new CheckoutSystem(
                itemID, userID,
                java.time.LocalDate.now(),
                java.time.LocalDate.now().plusDays(7)
        );

        records.add(record);
        return true;
    }

    public boolean returnItem(String itemID) {
        Item item = items.get(itemID);
        if (item == null) return false;

        for (CheckoutSystem r : records) {
            if (r.getItemId().equals(itemID) && !r.isReturned()) {
                r.setReturnDate(java.time.LocalDate.now());
                break;
            }
        }

        item.setAvailability(true);
        return true;
    }
}