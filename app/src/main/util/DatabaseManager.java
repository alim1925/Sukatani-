package com.sukatani.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:database/sukatani.db";
    private static Connection connection;

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Ensure the database directory exists
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get("database"));
                connection = DriverManager.getConnection(DB_URL);
            } catch (Exception e) {
                throw new SQLException("Error connecting to database", e);
            }
        }
        return connection;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Check if tables already exist by checking for 'users' table
            var resultSet = conn.getMetaData().getTables(null, null, "users", null);
            if (!resultSet.next()) {
                InputStream is = DatabaseManager.class.getResourceAsStream("/db/init.sql");
                if (is == null) {
                    throw new RuntimeException("Could not find init.sql");
                }
                
                String sql = new BufferedReader(new InputStreamReader(is))
                        .lines().collect(Collectors.joining("\n"));
                
                // SQLite JDBC driver might not support executing multiple statements in one executeUpdate
                // for some versions or configurations. Splitting by semicolon is a common workaround.
                String[] statements = sql.split(";");
                for (String s : statements) {
                    if (!s.trim().isEmpty()) {
                        stmt.execute(s);
                    }
                }
                System.out.println("Database initialized successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }
}
