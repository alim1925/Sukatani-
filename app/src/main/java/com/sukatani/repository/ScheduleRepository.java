package com.sukatani.repository;

import com.sukatani.model.ScheduleTask;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository extends BaseRepository {

    public void create(ScheduleTask task) throws SQLException {
        String sql = "INSERT INTO schedule_tasks (crop_id, activity_type, scheduled_date, status, notes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, task.getCropId());
            pstmt.setString(2, task.getActivityType());
            pstmt.setString(3, task.getScheduledDate().toString());
            pstmt.setString(4, task.getStatus());
            pstmt.setString(5, task.getNotes());
            
            pstmt.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE schedule_tasks SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
        }
    }

    public List<ScheduleTask> getAllByUserId(int userId) throws SQLException {
        List<ScheduleTask> tasks = new ArrayList<>();
        String sql = "SELECT st.* FROM schedule_tasks st " +
                     "JOIN crops c ON st.crop_id = c.id " +
                     "WHERE c.user_id = ? " +
                     "ORDER BY st.scheduled_date ASC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapResultSetToTask(rs));
                }
            }
        }
        return tasks;
    }

    private ScheduleTask mapResultSetToTask(ResultSet rs) throws SQLException {
        ScheduleTask task = new ScheduleTask();
        task.setId(rs.getInt("id"));
        task.setCropId(rs.getInt("crop_id"));
        task.setActivityType(rs.getString("activity_type"));
        task.setScheduledDate(LocalDate.parse(rs.getString("scheduled_date")));
        task.setStatus(rs.getString("status"));
        task.setNotes(rs.getString("notes"));
        return task;
    }
}
