package com.sukatani.service;

import com.sukatani.model.ScheduleTask;
import com.sukatani.repository.ScheduleRepository;
import com.sukatani.util.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class ScheduleService {
    private final ScheduleRepository scheduleRepo = new ScheduleRepository();

    public boolean addTask(ScheduleTask task) {
        try {
            scheduleRepo.create(task);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean completeTask(int taskId) {
        try {
            scheduleRepo.updateStatus(taskId, "completed");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ScheduleTask> getMyUpcomingTasks() {
        try {
            int userId = SessionManager.getCurrentUser().getId();
            return scheduleRepo.getAllByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
