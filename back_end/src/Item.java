// =======================================================
// this class represents a generic item in the inventory.
// =======================================================

public class Item {
    protected String id;
    protected String name;
    protected String description;
    public boolean isAvailable = true;

    // constructor
    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public boolean checkAvailability() {
        return isAvailable;
    }

    // setter
    public void setAvailability(boolean bool) {
        this.isAvailable = bool;
    }
}