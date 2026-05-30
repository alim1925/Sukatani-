package com.sukatani.repository;

import com.sukatani.util.DatabaseManager;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseRepository {
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getConnection();
    }
}
