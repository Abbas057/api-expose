package com.example.leave.dao;

import com.example.leave.model.Leave;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LeaveDAO {

    private String url;
    private String username;
    private String password;

    public LeaveDAO() {
        try {
            // Load Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Load DB properties
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input != null) {
                props.load(input);
                url = props.getProperty("db.url");
                username = props.getProperty("db.username");
                password = props.getProperty("db.password");
            } else {
                throw new RuntimeException("db.properties not found in classpath");
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    // Insert new leave
    public void addLeave(Leave leave) throws SQLException {
        String sql = "INSERT INTO employee_leaves (employee_id, status) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(true); // ensure commit happens automatically
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, leave.getEmployeeId());
                stmt.setString(2, leave.getStatus());
                stmt.executeUpdate();
                System.out.println("Inserting leave: " + leave.getEmployeeId() + ", " + leave.getStatus());
            }
        }

        
    }

    // Get all leaves
    public List<Leave> getAllLeaves() throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        String sql = "SELECT id, employee_id, status, applied_on FROM employee_leaves ORDER BY id";

        try (Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Leave leave = new Leave(
                        rs.getInt("id"),
                        rs.getString("employee_id"),
                        rs.getString("status"),
                        rs.getTimestamp("applied_on"));
                leaves.add(leave);
            }
        }
        return leaves;
    }
}
