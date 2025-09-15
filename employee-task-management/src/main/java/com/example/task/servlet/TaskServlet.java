package com.example.task.servlet;

import com.example.task.dao.TaskDAO;
import com.example.task.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * TaskServlet handles REST-style API calls for tasks
 * Endpoints:
 *  GET    /api/tasks        → list all tasks
 *  POST   /api/tasks        → create new task
 *  PUT    /api/tasks/{id}   → update task
 *  DELETE /api/tasks/{id}   → delete task
 */
@WebServlet("/api/tasks/*")
public class TaskServlet extends HttpServlet {

    private TaskDAO taskDAO;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
        mapper = new ObjectMapper(); // JSON serializer/deserializer
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            String json = mapper.writeValueAsString(tasks);
            resp.getWriter().write(json);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            BufferedReader reader = req.getReader();
            Task task = mapper.readValue(reader, Task.class);

            taskDAO.addTask(task);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\":\"Task created successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String pathInfo = req.getPathInfo(); // e.g. /5
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Task ID required in URL\"}");
                return;
            }

            int taskId = Integer.parseInt(pathInfo.substring(1));

            BufferedReader reader = req.getReader();
            Task task = mapper.readValue(reader, Task.class);
            task.setId(taskId);

            taskDAO.updateTask(task);
            resp.getWriter().write("{\"message\":\"Task updated successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String pathInfo = req.getPathInfo(); // e.g. /5
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Task ID required in URL\"}");
                return;
            }

            int taskId = Integer.parseInt(pathInfo.substring(1));

            taskDAO.deleteTask(taskId);
            resp.getWriter().write("{\"message\":\"Task deleted successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
