package com.smartstudy.model;

public class Subject {

    private int id;
    private int userId;
    private String subjectName;
    private String description;

    public Subject() {
    }

    public Subject(int id, int userId, String subjectName, String description) {
        this.id = id;
        this.userId = userId;
        this.subjectName = subjectName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}