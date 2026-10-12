package com.smartstudy.servlet;

import com.smartstudy.dao.SubjectDAO;
import com.smartstudy.model.Subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/subjects")
public class SubjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        SubjectDAO subjectDAO = new SubjectDAO();

        List<Subject> subjects =
                subjectDAO.getSubjectsByUser(userId);

        request.setAttribute("subjects", subjects);

        request.getRequestDispatcher("/subjects.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String action = request.getParameter("action");

        SubjectDAO subjectDAO = new SubjectDAO();

        try {

            // =========================
            // ADD SUBJECT
            // =========================
            if ("add".equals(action)) {

                String subjectName =
                        request.getParameter("subjectName");

                String description =
                        request.getParameter("description");

                if (subjectName == null ||
                        subjectName.trim().isEmpty()) {

                    request.setAttribute(
                            "error",
                            "Subject name is required.");

                } else {

                    Subject subject = new Subject();

                    subject.setUserId(userId);
                    subject.setSubjectName(subjectName.trim());
                    subject.setDescription(
                            description == null
                                    ? ""
                                    : description.trim());

                    boolean added =
                            subjectDAO.addSubject(subject);

                    if (added) {

                        response.sendRedirect(
                                request.getContextPath()
                                        + "/subjects");

                        return;

                    } else {

                        request.setAttribute(
                                "error",
                                "Unable to add subject.");
                    }
                }
            }


            // =========================
            // DELETE SUBJECT
            // =========================
            else if ("delete".equals(action)) {

                String idValue =
                        request.getParameter("id");

                if (idValue != null &&
                        !idValue.trim().isEmpty()) {

                    int id = Integer.parseInt(idValue);

                    subjectDAO.deleteSubject(id, userId);
                }

                response.sendRedirect(
                        request.getContextPath()
                                + "/subjects");

                return;
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Something went wrong. Please try again.");
        }

        // Reload subjects
        List<Subject> subjects =
                subjectDAO.getSubjectsByUser(userId);

        request.setAttribute("subjects", subjects);

        request.getRequestDispatcher("/subjects.jsp")
                .forward(request, response);
    }
}