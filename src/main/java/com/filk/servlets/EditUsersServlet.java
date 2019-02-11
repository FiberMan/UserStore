package com.filk.servlets;

import com.filk.utils.JdbcClient;
import com.filk.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EditUsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String userId = request.getParameter("user_id");

        try {
            ResultSet resultSet = JdbcClient.instance().executeQuery("select first_name, last_name, salary from users where id = " + userId + ";");


            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("title", "Edit user");
            pageVariables.put("action", "edit");
            pageVariables.put("button_value", "Update");
            pageVariables.put("user_id", userId);
            pageVariables.put("first_name", resultSet.getString("first_name"));
            pageVariables.put("last_name", resultSet.getString("last_name"));
            pageVariables.put("salary", resultSet.getString("salary"));

            response.getWriter().println(PageGenerator.instance().getPage("user_form.html", pageVariables));

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            System.out.println("No such user with ID: [" + userId + "]");
            e.printStackTrace();
            response.sendRedirect("/users/add");
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String userId = request.getParameter("user_id");
        String userFirstName = request.getParameter("first_name");
        String userLastName = request.getParameter("last_name");
        String userSalary = request.getParameter("salary");

        int updatedRows = JdbcClient.instance().executeUpdate("UPDATE users SET first_name = '" + userFirstName + "', last_name = '" + userLastName + "', salary = " + userSalary + " WHERE id = " + userId + ";");

        String updateResolution = (updatedRows == -1 ? "Failed to update user: " : "User has been updated: ") + userFirstName + " " + userLastName;
        String title = updatedRows == -1 ? "Failed to update user" : "User updated";

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("title", title);
        pageVariables.put("first_name", userFirstName);
        pageVariables.put("last_name", userLastName);
        pageVariables.put("update_resolution", updateResolution);

        response.getWriter().println(PageGenerator.instance().getPage("user_updated.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
