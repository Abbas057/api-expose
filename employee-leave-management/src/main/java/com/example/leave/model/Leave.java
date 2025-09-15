package com.example.leave.model;

import java.sql.Timestamp;

public class Leave {
    private int id;
    private String employeeId;
    private String status;
    private Timestamp appliedOn;

    // Constructors
    public Leave() {}

    public Leave(String employeeId, String status) {
        this.employeeId = employeeId;
        this.status = status;
    }

    public Leave(int id, String employeeId, String status, Timestamp appliedOn) {
        this.id = id;
        this.employeeId = employeeId;
        this.status = status;
        this.appliedOn = appliedOn;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getAppliedOn() { return appliedOn; }
    public void setAppliedOn(Timestamp appliedOn) { this.appliedOn = appliedOn; }
}
