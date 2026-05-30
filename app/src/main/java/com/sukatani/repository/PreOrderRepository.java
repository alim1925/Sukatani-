package com.sukatani.repository;

import com.sukatani.model.PreOrder;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PreOrderRepository extends BaseRepository {

    public void create(PreOrder preOrder) throws SQLException {
        String sql = "INSERT INTO pre_orders (crop_id, buyer_name, quantity, price, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, preOrder.getCropId());
            pstmt.setString(2, preOrder.getBuyerName());
            pstmt.setDouble(3, preOrder.getQuantity());
            pstmt.setDouble(4, preOrder.getPrice());
            pstmt.setString(5, preOrder.getStatus());
            
            pstmt.executeUpdate();
        }
    }

    public List<PreOrder> getAllByUserId(int userId) throws SQLException {
        List<PreOrder> preOrders = new ArrayList<>();
        // Join with crops to filter by user_id
        String sql = "SELECT po.* FROM pre_orders po " +
                     "JOIN crops c ON po.crop_id = c.id " +
                     "WHERE c.user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    preOrders.add(mapResultSetToPreOrder(rs));
                }
            }
        }
        return preOrders;
    }

    private PreOrder mapResultSetToPreOrder(ResultSet rs) throws SQLException {
        PreOrder po = new PreOrder();
        po.setId(rs.getInt("id"));
        po.setCropId(rs.getInt("crop_id"));
        po.setBuyerName(rs.getString("buyer_name"));
        po.setQuantity(rs.getDouble("quantity"));
        po.setPrice(rs.getDouble("price"));
        po.setOrderDate(LocalDate.parse(rs.getString("order_date").split(" ")[0])); // Handle potential DATETIME
        po.setStatus(rs.getString("status"));
        return po;
    }
}
