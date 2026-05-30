package com.sukatani.repository;

import com.sukatani.model.SensorData;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SensorRepository extends BaseRepository {

    public void create(SensorData data) throws SQLException {
        String sql = "INSERT INTO sensor_data (crop_id, soil_moisture, temperature, humidity, ph) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, data.getCropId());
            pstmt.setDouble(2, data.getSoilMoisture());
            pstmt.setDouble(3, data.getTemperature());
            pstmt.setDouble(4, data.getHumidity());
            pstmt.setDouble(5, data.getPh());
            
            pstmt.executeUpdate();
        }
    }

    public List<SensorData> getLatestByCropId(int cropId, int limit) throws SQLException {
        List<SensorData> results = new ArrayList<>();
        String sql = "SELECT * FROM sensor_data WHERE crop_id = ? ORDER BY recorded_at DESC LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cropId);
            pstmt.setInt(2, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToSensorData(rs));
                }
            }
        }
        return results;
    }

    private SensorData mapResultSetToSensorData(ResultSet rs) throws SQLException {
        SensorData data = new SensorData();
        data.setId(rs.getInt("id"));
        data.setCropId(rs.getInt("crop_id"));
        data.setSoilMoisture(rs.getDouble("soil_moisture"));
        data.setTemperature(rs.getDouble("temperature"));
        data.setHumidity(rs.getDouble("humidity"));
        data.setPh(rs.getDouble("ph"));
        // Handle SQLite datetime
        String ts = rs.getString("recorded_at");
        if (ts != null) {
            data.setRecordedAt(LocalDateTime.parse(ts.replace(" ", "T")));
        }
        return data;
    }
}
