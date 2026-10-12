
package com.smartstudy.servlet;

import com.smartstudy.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String college = request.getParameter("college");
        String branch = request.getParameter("branch");
        String yearValue = request.getParameter("year");

        // Validate input
        if (name == null || name.isBlank()
                || email == null || email.isBlank()
                || password == null || password.isBlank()
                || yearValue == null || yearValue.isBlank()) {

            request.setAttribute("error",
                    "Please fill in all required fields.");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        int year;

        try {
            year = Integer.parseInt(yearValue);

            if (year < 1 || year > 5) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Enter a valid year.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        // Hash the password
        String hashedPassword =
                BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users " +
                "(name, email, password, college, branch, year) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name.trim());
            statement.setString(2, email.trim());
            statement.setString(3, hashedPassword);
            statement.setString(4, college);
            statement.setString(5, branch);
            statement.setInt(6, year);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                response.sendRedirect(
                        request.getContextPath() + "/login.jsp");
                return;
            } else {
                request.setAttribute("error",
                        "Registration failed. Please try again.");

                request.getRequestDispatcher("register.jsp")
                        .forward(request, response);
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();

            if ("23000".equals(e.getSQLState())) {
                request.setAttribute("error",
                        "This email is already registered.");
            } else {
                request.setAttribute("error",
                        "Registration failed. Please try again later.");
            }

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error",
                    "Something went wrong during registration.");

            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath() + "/register.jsp");
    }
}