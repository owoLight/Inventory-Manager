package com.labinventory.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labinventory.backend.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
