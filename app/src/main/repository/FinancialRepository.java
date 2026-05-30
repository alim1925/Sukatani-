package com.sukatani.repository;

import com.sukatani.model.FinancialRecord;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRepository extends BaseRepository {

    public void create(FinancialRecord record) throws SQLException {
        String sql = "INSERT INTO financial_records (user_id, type, description, amount, record_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, record.getUserId());
            pstmt.setString(2, record.getType());
            pstmt.setString(3, record.getDescription());
            pstmt.setDouble(4, record.getAmount());
            pstmt.setString(5, record.getRecordDate().toString());
            
            pstmt.executeUpdate();
        }
    }

    public List<FinancialRecord> getAllByUserId(int userId) throws SQLException {
        List<FinancialRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM financial_records WHERE user_id = ? ORDER BY record_date DESC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    records.add(mapResultSetToRecord(rs));
                }
            }
        }
        return records;
    }

    private FinancialRecord mapResultSetToRecord(ResultSet rs) throws SQLException {
        FinancialRecord record = new FinancialRecord();
        record.setId(rs.getInt("id"));
        record.setUserId(rs.getInt("user_id"));
        record.setType(rs.getString("type"));
        record.setDescription(rs.getString("description"));
        record.setAmount(rs.getDouble("amount"));
        record.setRecordDate(LocalDate.parse(rs.getString("record_date")));
        return record;
    }
}
