package com.sukatani.service;

import com.sukatani.model.PreOrder;
import com.sukatani.repository.PreOrderRepository;
import com.sukatani.util.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class PreOrderService {
    private final PreOrderRepository preOrderRepo = new PreOrderRepository();

    public boolean placeOrder(PreOrder preOrder) {
        try {
            preOrderRepo.create(preOrder);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PreOrder> getMyPreOrders() {
        try {
            int userId = SessionManager.getCurrentUser().getId();
            return preOrderRepo.getAllByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
