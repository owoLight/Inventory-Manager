/*
Patch Notes:
Users are allowed to be created
Users with invalid email formats are rejected
Duplicate user IDs are not allowed
Invalid users are marked as null

Users are allowed to check out items if available
Users can be removed from the system

Items are added to the inventory system

To Do:
Make it so only admins can add and remove users and items from the database
Change a user to null once it is removed from the system
Additional things i havent thought of yet
*/

public class Main {

    // using main to do some basic testing
    public static void main(String[] args) {
        System.out.println("Inventory Manager System");
        Item item1 = new Item("001", "Laptop", "Dell XPS 13");
        Item item2 = new ECEItem("002", "Oscilloscope", "Tektronix TBS2000");
        Item item3 = new Item("003", "Projector", "Epson EX3260");

        InventoryDatabase database = new InventoryDatabase();
        InventoryManager manager = new InventoryManager(database);
        manager.addItem(item1);
        manager.addItem(item2);
        manager.addItem(item3); 
        User user1 = manager.createAndRegisterUser("001", "Alice", "alice@gmail.com", User.Permissions.normal);
        User user2 = manager.createAndRegisterUser("002", "Bob", "bob_email.com", User.Permissions.normal); // invalid email
        User user3 = manager.createAndRegisterUser("001", "Charlie", "charlie@yahoo.com", User.Permissions.normal); // duplicate ID
        User user4 = manager.createAndRegisterUser("001", "David", "david@yahoo.com", User.Permissions.admin); // valid admin user

        for (User user : new User[]{user1, user2, user3, user4}) {
            if(user != null) {
                System.out.println("User Created: " + User.getName(user) + ", Email: " + User.getEmail(user) + ", ID: " + User.getId(user));
            }
            else {
                System.out.println("User does not exist.");
            }
        }

        manager.removeUser("U-003"); // non-existent user
        manager.removeUser("U-001"); // existing user
        
        System.err.println("End of code testing.");

    }
}