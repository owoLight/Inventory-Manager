/*
Patch Notes:

Users are allowed to check out items if available

Methods in the manager check the database to see if there are existing users

Items cannot be checked out twice by the same user
Tracks if items are overdue with overdue flag

Added ways to search items by name and type in the database, return type: List<Item>
Only searches items added into the database.

Items returned late will incur penalty points to the user

To Do:
˚　　✦　　.　. ˚　.　　. ✦　　˚  . ★⋆. ࿐࿔ 
Different access levels for users (admin, normal)
Error Handling for invalid inputs
Steady database connection instead of in-memory
GUI interface
.  ˚　　*　✦　.　.✦　˚  ˚.˚　　✦　.　. 　˚.　　
*/

public class Main {

    // using main to do some basic testing
    public static void main(String[] args) {
        System.out.println("Inventory Manager System");

        Item item1 = new Item("001", "Laptop", "Dell XPS 13");
        Item item2 = new ECEItem("002", "Oscilloscope", "Tektronix TBS2000");
        Item item3 = new Item("003", "Projector", "Epson EX3260");
        Item item4 = new ECEItem("004", "Multimeter", "Fluke 87V");

        InventoryDatabase database = new InventoryDatabase();
        InventoryManager manager = new InventoryManager(database);
        manager.addItem(item1);
        manager.addItem(item2);
        manager.addItem(item3);
        manager.addItem(item4);

        database.searchItemsByName("scope").forEach(i -> 
            System.out.println("Found by name: " + i.getName() + " (" + i.getId() + ")")
        );

        database.searchItemsByType(ECEItem.class).forEach(i -> 
            System.out.println("Found by type: " + i.getName() + " (" + i.getId() + ")")
        );
        

        System.err.println("End of code testing.");

    }
}