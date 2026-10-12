
package com.smartstudy.servlet;

import com.smartstudy.dao.ScheduleDAO;
import com.smartstudy.model.Schedule;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {

    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    // Display study schedules
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

        List<Schedule> schedules =
                scheduleDAO.getSchedulesByUser(userId);

        request.setAttribute("schedules", schedules);

        request.getRequestDispatcher("/schedule.jsp")
                .forward(request, response);
    }

    // Add or delete study schedules
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

                String subjectValue = request.getParameter("subjectId");
                String topic = request.getParameter("topic");
                String dateValue = request.getParameter("studyDate");
                String startValue = request.getParameter("startTime");
                String endValue = request.getParameter("endTime");

                if (subjectValue == null || subjectValue.isBlank()
                        || topic == null || topic.isBlank()
                        || dateValue == null || dateValue.isBlank()
                        || startValue == null || startValue.isBlank()
                        || endValue == null || endValue.isBlank()) {

                    request.setAttribute("error",
                            "Please fill in all fields.");

                } else {
                    int subjectId = Integer.parseInt(subjectValue);
                    Date studyDate = Date.valueOf(dateValue);
                    Time startTime = Time.valueOf(startValue + ":00");
                    Time endTime = Time.valueOf(endValue + ":00");

                    if (!endTime.after(startTime)) {
                        request.setAttribute("error",
                                "End time must be after start time.");
                    } else {
                        Schedule schedule = new Schedule();

                        schedule.setUserId(userId);
                        schedule.setSubjectId(subjectId);
                        schedule.setTopic(topic.trim());
                        schedule.setStudyDate(studyDate);
                        schedule.setStartTime(startTime);
                        schedule.setEndTime(endTime);

                        boolean added = scheduleDAO.addSchedule(schedule);

                        if (added) {
                            response.sendRedirect(
                                    request.getContextPath() + "/schedule");
                            return;
                        } else {
                            request.setAttribute("error",
                                    "Unable to add the study session.");
                        }
                    }
                }

            } else if ("delete".equals(action)) {

                String idValue = request.getParameter("id");

                if (idValue != null && !idValue.isBlank()) {
                    int id = Integer.parseInt(idValue);
                    scheduleDAO.deleteSchedule(id, userId);
                }

                response.sendRedirect(
                        request.getContextPath() + "/schedule");
                return;

            } else {
                request.setAttribute("error", "Invalid action.");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error",
                    "Please enter valid schedule details.");
        }

        List<Schedule> schedules =
                scheduleDAO.getSchedulesByUser(userId);

        request.setAttribute("schedules", schedules);

        request.getRequestDispatcher("/schedule.jsp")
                .forward(request, response);
    }
}