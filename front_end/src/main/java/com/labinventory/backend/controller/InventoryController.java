package com.labinventory.backend.controller;

import com.labinventory.backend.model.ECEItem;
import com.labinventory.backend.model.InventoryDatabase;
import com.labinventory.backend.model.Item;
import com.labinventory.backend.service.InventoryManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InventoryController {

    private final InventoryManager inventoryManager;

    public InventoryController() {
        InventoryDatabase db = new InventoryDatabase();
        this.inventoryManager = new InventoryManager(db);

        inventoryManager.addItem(new Item("001", "Laptop", "Dell XPS 13"));
        inventoryManager.addItem(new ECEItem("002", "Oscilloscope", "Tektronix TBS2000"));
        inventoryManager.addItem(new Item("003", "Projector", "Epson EX3260"));
    }

    @GetMapping("/")
    public String listItems(Model model) {
        model.addAttribute("items", inventoryManager.getAllItems());
        return "items";
    }

    @GetMapping("/items/new")
    public String newItemForm() {
        return "add-item";
    }

    @PostMapping("/items")
    public String createItem(@RequestParam String id,
                             @RequestParam String name,
                             @RequestParam String description) {
        inventoryManager.addItem(new Item(id, name, description));
        return "redirect:/";
    }

    @PostMapping("/items/borrow")
    public String borrowItem(@RequestParam String itemId) {
        inventoryManager.checkoutItem(itemId, "U-001"); // fake user for now
        return "redirect:/";
    }

    @PostMapping("/items/return")
    public String returnItem(@RequestParam String itemId) {
        inventoryManager.returnItem(itemId, "U-001");
        return "redirect:/";
    }
}

