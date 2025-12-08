package com.labinventory.backend.model;

import java.time.LocalDate;

public class LoanForm {

    private String borrowerName;
    private String borrowerPuid;
    private LocalDate dueDate;

    public LoanForm() { }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public String getBorrowerPuid() { return borrowerPuid; }
    public void setBorrowerPuid(String borrowerPuid) { this.borrowerPuid = borrowerPuid; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
