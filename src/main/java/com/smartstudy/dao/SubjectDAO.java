package com.smartstudy.dao;

import com.smartstudy.model.Subject;
import com.smartstudy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // =========================
    // ADD SUBJECT
    // =========================
    public boolean addSubject(Subject subject) {

        String sql = "INSERT INTO subjects " +
                "(user_id, subject_name, description) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, subject.getUserId());
            statement.setString(2, subject.getSubjectName());
            statement.setString(3, subject.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // =========================
    // GET SUBJECTS FOR USER
    // =========================
    public List<Subject> getSubjectsByUser(int userId) {

        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT id, user_id, subject_name, description " +
                "FROM subjects " +
                "WHERE user_id = ? " +
                "ORDER BY id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Subject subject = new Subject();

                    subject.setId(result.getInt("id"));
                    subject.setUserId(result.getInt("user_id"));
                    subject.setSubjectName(
                            result.getString("subject_name"));
                    subject.setDescription(
                            result.getString("description"));

                    subjects.add(subject);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }


    // =========================
    // DELETE SUBJECT
    // =========================
    public boolean deleteSubject(int id, int userId) {

        String sql = "DELETE FROM subjects " +
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