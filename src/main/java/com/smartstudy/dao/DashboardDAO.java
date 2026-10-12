
package com.smartstudy.dao;

import com.smartstudy.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    // Count the student's subjects
    public int getTotalSubjects(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM subjects WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    // Count all tasks
    public int getTotalTasks(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tasks WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    // Count pending tasks
    public int getPendingTasks(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tasks " +
                "WHERE user_id = ? AND status = 'Pending'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    // Count completed tasks
    public int getCompletedTasks(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tasks " +
                "WHERE user_id = ? AND status = 'Completed'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
}
