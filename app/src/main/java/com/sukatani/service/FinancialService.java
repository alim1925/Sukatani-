package com.sukatani.service;

import com.sukatani.model.FinancialRecord;
import com.sukatani.repository.FinancialRepository;
import com.sukatani.util.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class FinancialService {
    private final FinancialRepository financialRepo = new FinancialRepository();

    public boolean addRecord(FinancialRecord record) {
        try {
            financialRepo.create(record);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FinancialRecord> getMyRecords() {
        try {
            int userId = SessionManager.getCurrentUser().getId();
            return financialRepo.getAllByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public double getTotalIncome() {
        return getMyRecords().stream()
                .filter(r -> "income".equalsIgnoreCase(r.getType()))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return getMyRecords().stream()
                .filter(r -> "expense".equalsIgnoreCase(r.getType()))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double getNetProfit() {
        return getTotalIncome() - getTotalExpense();
    }
}
