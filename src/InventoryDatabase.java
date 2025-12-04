import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryDatabase {
    private final Map<String, Item> items = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private final List<CheckoutSystem> records = new ArrayList<>();

    // Accessors for InventoryManager
    public Map<String, Item> getItems() { return items; }
    public Map<String, User> getUsers() { return users; }
    public List<CheckoutSystem> getRecords() { return records; }
}