import java.time.LocalDate;

public class CheckoutSystem {
    private String userId;
    private String itemId;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate = null;

    public CheckoutSystem(String itemId, String userId,
                          LocalDate checkoutDate, LocalDate dueDate) {
        this.itemId = itemId;
        this.userId = userId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate date) {
        this.returnDate = date;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

}
