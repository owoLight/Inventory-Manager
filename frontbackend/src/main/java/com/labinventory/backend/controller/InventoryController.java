package com.labinventory.backend.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.labinventory.backend.model.Item;
import com.labinventory.backend.model.Loan;
import com.labinventory.backend.model.LoanForm;
import com.labinventory.backend.service.InventoryService;

@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String listItems(Model model) {
        model.addAttribute("items", inventoryService.getAllItems());
        return "items";
    }

    @GetMapping("/items/new")
    public String showNewItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "item-form";
    }

    @PostMapping("/items")
    public String createItem(@RequestParam String code,
                             @RequestParam String name,
                             @RequestParam String description) {
        Item item = new Item(code, name, description);
        inventoryService.addItem(item);
        return "redirect:/items";
    }

    // ----- Borrow -----

    @GetMapping("/items/{id}/borrow")
    public String showBorrowForm(@PathVariable Long id, Model model) {
        Item item = inventoryService.getItem(id);

        LoanForm form = new LoanForm();
        form.setDueDate(LocalDate.now().plusDays(7));

        model.addAttribute("item", item);
        model.addAttribute("loanForm", form);
        return "borrow";
    }

    @PostMapping("/items/{id}/borrow")
    public String borrowItem(@PathVariable Long id,
                             @ModelAttribute("loanForm") LoanForm form) {

        inventoryService.borrowItem(
                id,
                form.getBorrowerName(),
                form.getBorrowerPuid(),
                form.getDueDate()
        );

        return "redirect:/items";
    }

    // ----- Return -----

    @PostMapping("/items/{id}/return")
    public String returnItem(@PathVariable Long id) {
        inventoryService.returnItem(id);
        return "redirect:/items";
    }

    // ----- Item details (clickable ID) -----

    @GetMapping("/items/{id}")
    public String itemDetails(@PathVariable Long id, Model model) {
        Item item = inventoryService.getItem(id);
        Loan loan = inventoryService.getActiveLoanForItem(id);

        model.addAttribute("item", item);
        model.addAttribute("loan", loan);

        return "item-details";
    }
}
