package com.example.task.dao;

import com.example.task.model.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends AbstractDAO implements TaskOperations {

    @Override
    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (employee_id, title, description, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, task.getEmployeeId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getStatus());
            stmt.executeUpdate();
            conn.commit();

        }
    }

    @Override
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks ORDER BY id";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            conn.setAutoCommit(false);
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getInt("employee_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_on"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET title=?, description=?, status=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getId());
            stmt.executeUpdate();
            conn.commit();
        }
    }

    @Override
    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
            conn.commit();
        }
    }
}
