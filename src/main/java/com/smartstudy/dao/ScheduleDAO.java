package com.smartstudy.dao;

import com.smartstudy.model.Schedule;
import com.smartstudy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    // Add a new study schedule
    public boolean addSchedule(Schedule schedule) {

        String sql = "INSERT INTO study_sessions " +
                "(user_id, subject_id, topic, study_date, start_time, end_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, schedule.getUserId());
            statement.setInt(2, schedule.getSubjectId());
            statement.setString(3, schedule.getTopic());
            statement.setDate(4, schedule.getStudyDate());
            statement.setTime(5, schedule.getStartTime());
            statement.setTime(6, schedule.getEndTime());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get schedules for the logged-in user
    public List<Schedule> getSchedulesByUser(int userId) {

        List<Schedule> schedules = new ArrayList<>();

        String sql = "SELECT id, user_id, subject_id, topic, " +
                "study_date, start_time, end_time " +
                "FROM study_sessions " +
                "WHERE user_id = ? " +
                "ORDER BY study_date, start_time";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Schedule schedule = new Schedule();

                    schedule.setId(result.getInt("id"));
                    schedule.setUserId(result.getInt("user_id"));
                    schedule.setSubjectId(result.getInt("subject_id"));
                    schedule.setTopic(result.getString("topic"));
                    schedule.setStudyDate(result.getDate("study_date"));
                    schedule.setStartTime(result.getTime("start_time"));
                    schedule.setEndTime(result.getTime("end_time"));

                    schedules.add(schedule);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }


    // Delete a schedule
    public boolean deleteSchedule(int id, int userId) {

        String sql = "DELETE FROM study_sessions " +
                "WHERE id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}