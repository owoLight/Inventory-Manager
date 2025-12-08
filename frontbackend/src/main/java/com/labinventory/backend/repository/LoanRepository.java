package com.labinventory.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labinventory.backend.model.Item;
import com.labinventory.backend.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Latest *open* loan for a given item (no returnDate)
    Optional<Loan> findTopByItemAndReturnDateIsNullOrderByCheckoutDateDesc(Item item);
}
