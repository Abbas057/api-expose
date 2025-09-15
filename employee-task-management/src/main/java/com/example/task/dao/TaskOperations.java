package com.example.task.dao;

import com.example.task.model.Task;
import java.sql.SQLException;
import java.util.List;

public interface TaskOperations {
    void addTask(Task task) throws SQLException;
    List<Task> getAllTasks() throws SQLException;
    void updateTask(Task task) throws SQLException;
    void deleteTask(int taskId) throws SQLException;
}
