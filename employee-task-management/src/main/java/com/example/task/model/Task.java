package com.example.task.model;

import java.sql.Timestamp;

public class Task {
    private int id;
    private int employeeId;
    private String title;
    private String description;
    private String status;
    private Timestamp createdOn;

    // Constructors, getters, setters

    public Task(){}

    // Constructor for creating new task (without id, created_on)
    public Task(int employeeId, String title, String description, String status ){
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    // Constructor to reading  from DB
    public Task(int id,int employeeId, String title, String description, String status, Timestamp createdOn ){
        this.id = id;
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdOn = createdOn;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedOn() { return createdOn; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }


}
