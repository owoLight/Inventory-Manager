package com.labinventory.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labinventory.backend.model.Item;
import com.labinventory.backend.model.Loan;
import com.labinventory.backend.repository.ItemRepository;
import com.labinventory.backend.repository.LoanRepository;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final LoanRepository loanRepository;

    public InventoryService(ItemRepository itemRepository,
                            LoanRepository loanRepository) {
        this.itemRepository = itemRepository;
        this.loanRepository = loanRepository;
    }

    // -------- Items --------

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
    }

    @Transactional
    public void addItem(Item item) {
        item.setAvailable(true);
        itemRepository.save(item);
    }

    // -------- Borrow / Return --------

    @Transactional
    public Loan borrowItem(Long itemId,
                           String borrowerName,
                           String borrowerPuid,
                           LocalDate dueDate) {

        Item item = getItem(itemId);

        if (!item.isAvailable()) {
            throw new IllegalStateException("Item already borrowed");
        }

        item.setAvailable(false);
        itemRepository.save(item);

        Loan loan = new Loan();
        loan.setItem(item);
        loan.setBorrowerName(borrowerName);
        loan.setBorrowerPuid(borrowerPuid);
        loan.setCheckoutDate(LocalDate.now());
        loan.setDueDate(dueDate);

        return loanRepository.save(loan);
    }

    @Transactional
    public void returnItem(Long itemId) {
        Item item = getItem(itemId);

        if (item.isAvailable()) {
            return; // nothing to do
        }

        Loan loan = loanRepository
                .findTopByItemAndReturnDateIsNullOrderByCheckoutDateDesc(item)
                .orElse(null);

        if (loan != null) {
            loan.setReturnDate(LocalDate.now());
            loanRepository.save(loan);
        }

        item.setAvailable(true);
        itemRepository.save(item);
    }

    public Loan getActiveLoanForItem(Long itemId) {
        Item item = getItem(itemId);
        return loanRepository
                .findTopByItemAndReturnDateIsNullOrderByCheckoutDateDesc(item)
                .orElse(null);
    }
}
