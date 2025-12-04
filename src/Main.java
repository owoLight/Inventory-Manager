/*
Patch Notes:
Users are allowed to be created
Users with invalid email formats are rejected
Duplicate user IDs are not allowed
Invalid users are set as null

Users are allowed to check out items if available
Users can be removed from the system
Deleted users are no longer in the database

Methods in the manager check the database to see if there are existing users

Items are added to the inventory system

To Do:
Make it so only admins can add and remove users and items from the database
Change a user to null once it is removed from the system

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

        if(user3 != null) {
            manager.removeUser(user3.getId()); // figure out how to remove non-existing user
        } else {
            System.out.println("Cannot remove user3; user does not exist.");
        }
        if(user1 != null) {
            manager.removeUser(user1.getId()); // existing user
        } else {
            System.out.println("Cannot remove user1; user does not exist.");
        }
        
        // Existing users: A-001 (David)
        boolean checkout1 = manager.checkoutItem(item1.getId(), user1.getId()); // should fail, user removed
        boolean checkout2 = manager.checkoutItem(item2.getId(), user4.getId()); // should succeed

        System.out.println("Checkout attempt 1 (should be false): " + checkout1);
        System.out.println("Checkout attempt 2 (should be true): " + checkout2);
        
        System.err.println("End of code testing.");

    }
}