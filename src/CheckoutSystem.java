import java.time.LocalDate;
// =======================================================
// this class assigns checked out items to users and tracks due dates.
// =======================================================

public class CheckoutSystem {
    private final String userId;
    private final String itemId;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnDate = null;
    
    // constructor
    public CheckoutSystem(String itemId, String userId, LocalDate checkoutDate, LocalDate dueDate) {
        this.itemId = itemId;
        this.userId = userId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    // getters
    public String getItemId() {
        return itemId;
    }
    public String getUserId() {
        return userId;
    }
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }

    // setters
    public void setReturnDate(LocalDate date) {
        this.returnDate = date;
    }

    // methods
    public boolean isReturned() {
        return returnDate != null;
    }

}
