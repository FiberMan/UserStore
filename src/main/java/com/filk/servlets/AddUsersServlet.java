package com.filk.servlets;

import com.filk.utils.JdbcClient;
import com.filk.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddUsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("title", "Add new user");
        pageVariables.put("action", "add");
        pageVariables.put("button_value", "Add");
        pageVariables.put("first_name", "");
        pageVariables.put("last_name", "");
        pageVariables.put("salary", "");

        response.getWriter().println(PageGenerator.instance().getPage("user_form.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String userFirstName = request.getParameter("first_name");
        String userLastName = request.getParameter("last_name");
        String userSalary = request.getParameter("salary");

        JdbcClient jdbcClient = new JdbcClient();
        int updatedRows = jdbcClient.executeUpdate("INSERT INTO users(first_name, last_name, salary) values ('" + userFirstName + "', '" + userLastName + "', " + userSalary + ");");

        String updateResolution = (updatedRows == -1 ? "Failed to add new user: " : "New user has been added: ") + userFirstName + " " + userLastName;
        String title = updatedRows == -1 ? "Failed to add user" : "User added";

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
