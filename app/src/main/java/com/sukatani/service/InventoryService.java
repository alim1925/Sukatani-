package com.sukatani.service;

import com.sukatani.model.Crop;
import com.sukatani.model.Inventory;
import com.sukatani.repository.CropRepository;
import com.sukatani.repository.InventoryRepository;
import com.sukatani.util.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class InventoryService {
    private final CropRepository cropRepo = new CropRepository();
    private final InventoryRepository inventoryRepo = new InventoryRepository();

    public boolean addCrop(Crop crop, String unit) {
        try {
            int cropId = cropRepo.create(crop);
            if (cropId != -1) {
                // Initialize inventory for this crop
                Inventory inv = new Inventory(cropId, 0, unit, "available");
                inventoryRepo.create(inv);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Crop> getMyCrops() {
        try {
            int userId = SessionManager.getCurrentUser().getId();
            return cropRepo.getAllByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Inventory getInventoryByCropId(int cropId) {
        try {
            return inventoryRepo.getByCropId(cropId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateStock(int cropId, double newQuantity) {
        try {
            Inventory inv = inventoryRepo.getByCropId(cropId);
            if (inv != null) {
                inv.setQuantity(newQuantity);
                inventoryRepo.update(inv);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
