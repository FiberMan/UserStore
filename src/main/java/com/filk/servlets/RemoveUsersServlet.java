package com.filk.servlets;

import com.filk.utils.JdbcClient;
import com.filk.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RemoveUsersServlet extends HttpServlet {
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String userId = request.getParameter("user_id");

        int updatedRows = JdbcClient.instance().executeUpdate("DELETE FROM users WHERE id = " + userId + ";");

        String updateResolution = (updatedRows == -1 ? "Failed to delete user ID: " : "User ID has been deleted: ") + userId;
        String title = updatedRows == -1 ? "Failed to delete user" : "User deleted";

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("title", title);
        pageVariables.put("update_resolution", updateResolution);

        response.getWriter().println(PageGenerator.instance().getPage("user_updated.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
