package com.sukatani.repository;

import com.sukatani.model.Crop;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CropRepository extends BaseRepository {
    
    public int create(Crop crop) throws SQLException {
        String sql = "INSERT INTO crops (user_id, name, planting_date, estimated_harvest_date, estimated_quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, crop.getUserId());
            pstmt.setString(2, crop.getName());
            pstmt.setString(3, crop.getPlantingDate().toString());
            pstmt.setString(4, crop.getEstimatedHarvestDate().toString());
            pstmt.setDouble(5, crop.getEstimatedQuantity());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public List<Crop> getAllByUserId(int userId) throws SQLException {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM crops WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    crops.add(mapResultSetToCrop(rs));
                }
            }
        }
        return crops;
    }

    public Crop getById(int id) throws SQLException {
        String sql = "SELECT * FROM crops WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCrop(rs);
                }
            }
        }
        return null;
    }

    private Crop mapResultSetToCrop(ResultSet rs) throws SQLException {
        Crop crop = new Crop();
        crop.setId(rs.getInt("id"));
        crop.setUserId(rs.getInt("user_id"));
        crop.setName(rs.getString("name"));
        crop.setPlantingDate(LocalDate.parse(rs.getString("planting_date")));
        crop.setEstimatedHarvestDate(LocalDate.parse(rs.getString("estimated_harvest_date")));
        crop.setEstimatedQuantity(rs.getDouble("estimated_quantity"));
        return crop;
    }
}
