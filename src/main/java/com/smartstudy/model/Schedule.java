
package com.smartstudy.model;

import java.sql.Date;
import java.sql.Time;

public class Schedule {

    private int id;
    private int userId;
    private int subjectId;
    private String topic;
    private Date studyDate;
    private Time startTime;
    private Time endTime;

    public Schedule() {
    }

    public Schedule(int id, int userId, int subjectId,
                    String topic, Date studyDate,
                    Time startTime, Time endTime) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.topic = topic;
        this.studyDate = studyDate;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(Date studyDate) {
        this.studyDate = studyDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}