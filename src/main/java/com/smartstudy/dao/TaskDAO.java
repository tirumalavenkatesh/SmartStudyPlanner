package com.smartstudy.dao;

import com.smartstudy.model.Task;
import com.smartstudy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // Add a task
    public void addTask(Task task) throws Exception {

        String sql = "INSERT INTO tasks " +
                "(user_id, subject_id, title, description, priority, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, task.getUserId());
            statement.setInt(2, task.getSubjectId());
            statement.setString(3, task.getTitle());
            statement.setString(4, task.getDescription());
            statement.setString(5, task.getPriority());
            statement.setDate(6, task.getDueDate());
            statement.setString(7, task.getStatus());

            statement.executeUpdate();
        }
    }

    // Get all tasks of a particular student
    public List<Task> getTasksByUserId(int userId) throws Exception {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks " +
                "WHERE user_id = ? " +
                "ORDER BY due_date";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Task task = new Task();

                task.setId(result.getInt("id"));
                task.setUserId(result.getInt("user_id"));
                task.setSubjectId(result.getInt("subject_id"));
                task.setTitle(result.getString("title"));
                task.setDescription(result.getString("description"));
                task.setPriority(result.getString("priority"));
                task.setDueDate(result.getDate("due_date"));
                task.setStatus(result.getString("status"));

                tasks.add(task);
            }
        }

        return tasks;
    }

    // Delete a task
    public void deleteTask(int taskId, int userId) throws Exception {

        String sql = "DELETE FROM tasks " +
                "WHERE id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, taskId);
            statement.setInt(2, userId);

            statement.executeUpdate();
        }
    }

    // Update task status
    public void updateTaskStatus(int taskId,
                                 int userId,
                                 String status) throws Exception {

        String sql = "UPDATE tasks SET status = ? " +
                "WHERE id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, taskId);
            statement.setInt(3, userId);

            statement.executeUpdate();
        }
    }
}