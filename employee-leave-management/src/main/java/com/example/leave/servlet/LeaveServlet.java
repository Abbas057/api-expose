package com.example.leave.servlet;

import com.example.leave.dao.LeaveDAO;
import com.example.leave.model.Leave;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class LeaveServlet extends HttpServlet {

    private LeaveDAO leaveDAO;

    @Override
    public void init() {
        leaveDAO = new LeaveDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("GET request received");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Leave> leaves = leaveDAO.getAllLeaves();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < leaves.size(); i++) {
                Leave l = leaves.get(i);
                json.append("{")
                    .append("\"id\":\"").append(l.getId()).append("\",")
                    .append("\"employeeId\":\"").append(l.getEmployeeId()).append("\",")
                    .append("\"status\":\"").append(l.getStatus()).append("\",")
                    .append("\"appliedOn\":\"").append(l.getAppliedOn()).append("\"")
                    .append("}");
                if (i < leaves.size() - 1) json.append(",");
            }
            json.append("]");
            resp.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"error\":\"Failed to fetch leaves\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST request received");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String employeeId = req.getParameter("employeeId");
        String status = req.getParameter("status");

        System.out.println("Parameters: employeeId=" + employeeId + ", status=" + status);

        if (employeeId == null || employeeId.isEmpty()) {
            resp.getWriter().write("{\"error\":\"employeeId is required\"}");
            return;
        }

        Leave leave = new Leave(employeeId, (status != null) ? status : "PENDING");

        try {
            leaveDAO.addLeave(leave);
            resp.getWriter().write("{\"message\":\"Leave applied successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"error\":\"Failed to apply leave\"}");
        }
    }
}
