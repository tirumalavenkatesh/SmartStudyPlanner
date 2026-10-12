
package com.smartstudy.servlet;

import com.smartstudy.dao.TaskDAO;
import com.smartstudy.dao.SubjectDAO;
import com.smartstudy.model.Task;
import com.smartstudy.model.Subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private final TaskDAO taskDAO = new TaskDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    // Display tasks when the user opens /tasks
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            List<Subject> subjects =
                    subjectDAO.getSubjectsByUser(userId);

            List<Task> tasks =
                    taskDAO.getTasksByUserId(userId);

            request.setAttribute("subjects", subjects);
            request.setAttribute("tasks", tasks);

            request.getRequestDispatcher("/tasks.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load tasks.");
        }
    }

    // Add, delete, or update tasks
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {

                int subjectId = Integer.parseInt(
                        request.getParameter("subjectId"));

                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String priority = request.getParameter("priority");

                Date dueDate = Date.valueOf(
                        request.getParameter("dueDate"));

                Task task = new Task();
                task.setUserId(userId);
                task.setSubjectId(subjectId);
                task.setTitle(title.trim());
                task.setDescription(description);
                task.setPriority(priority);
                task.setDueDate(dueDate);
                task.setStatus("Pending");

                taskDAO.addTask(task);

            } else if ("delete".equals(action)) {

                int taskId = Integer.parseInt(
                        request.getParameter("taskId"));

                taskDAO.deleteTask(taskId, userId);

            } else if ("updateStatus".equals(action)) {

                int taskId = Integer.parseInt(
                        request.getParameter("taskId"));

                String status = request.getParameter("status");

                if (!"Pending".equals(status)
                        && !"Completed".equals(status)) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid task status.");
                    return;
                }

                taskDAO.updateTaskStatus(taskId, userId, status);

            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid task action.");
                return;
            }

            response.sendRedirect(
                    request.getContextPath() + "/tasks");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Task operation failed.");
        }
    }
}
