
package com.smartstudy.servlet;

import com.smartstudy.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {

            request.setAttribute("error",
                    "Email and password are required.");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);
            return;
        }

        String sql = "SELECT id, name, email, password FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email.trim());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String hashedPassword = result.getString("password");

                    if (hashedPassword != null
                            && BCrypt.checkpw(password, hashedPassword)) {

                        HttpSession oldSession = request.getSession(false);

                        if (oldSession != null) {
                            oldSession.invalidate();
                        }

                        HttpSession session = request.getSession(true);

                        session.setAttribute("userId",
                                result.getInt("id"));

                        session.setAttribute("userName",
                                result.getString("name"));

                        session.setAttribute("userEmail",
                                result.getString("email"));

                        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                        return;
                    }
                }

                request.setAttribute("error",
                        "Invalid email or password.");

                request.getRequestDispatcher("login.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("error",
                    "Unable to log in right now. Please try again later.");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("login.jsp");
    }
}