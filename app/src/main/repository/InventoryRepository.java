package com.sukatani.repository;

import com.sukatani.model.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository extends BaseRepository {

    public void create(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO inventory (crop_id, quantity, unit, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, inventory.getCropId());
            pstmt.setDouble(2, inventory.getQuantity());
            pstmt.setString(3, inventory.getUnit());
            pstmt.setString(4, inventory.getStatus());
            
            pstmt.executeUpdate();
        }
    }

    public void update(Inventory inventory) throws SQLException {
        String sql = "UPDATE inventory SET quantity = ?, unit = ?, status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, inventory.getQuantity());
            pstmt.setString(2, inventory.getUnit());
            pstmt.setString(3, inventory.getStatus());
            pstmt.setInt(4, inventory.getId());
            
            pstmt.executeUpdate();
        }
    }

    public Inventory getByCropId(int cropId) throws SQLException {
        String sql = "SELECT * FROM inventory WHERE crop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cropId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInventory(rs);
                }
            }
        }
        return null;
    }

    private Inventory mapResultSetToInventory(ResultSet rs) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setId(rs.getInt("id"));
        inventory.setCropId(rs.getInt("crop_id"));
        inventory.setQuantity(rs.getDouble("quantity"));
        inventory.setUnit(rs.getString("unit"));
        inventory.setStatus(rs.getString("status"));
        return inventory;
    }
}
