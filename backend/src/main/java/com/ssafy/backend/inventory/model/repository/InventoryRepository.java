package com.ssafy.backend.inventory.model.repository;

import com.ssafy.backend.inventory.model.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

}
