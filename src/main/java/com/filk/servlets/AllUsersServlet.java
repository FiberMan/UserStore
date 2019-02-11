package com.filk.servlets;

import com.filk.utils.JdbcClient;
import com.filk.utils.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AllUsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        ResultSet resultSet = JdbcClient.instance().executeQuery("select id, first_name, last_name, salary from users;");
        String usersTableBody = getUsersTableHtml(resultSet);
        JdbcClient.instance().close();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("title", "List of users");
        pageVariables.put("users_table_body", usersTableBody);

        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    private static String getUsersTableHtml(ResultSet resultSet) {
        StringBuilder response = new StringBuilder("<table>\n" +
                "    <tr>\n" +
                "        <th>ID</td>\n" +
                "        <th>First name</td>\n" +
                "        <th>Last name</td>\n" +
                "        <th>Salary</td>\n" +
                "        <th colspan=\"2\">Actions</td>\n" +
                "    </tr>\n");
        String userId;
        try {
            while (resultSet.next()) {
                userId = resultSet.getString("id");

                response.append("  <tr><td>");
                response.append(userId);
                response.append("</td><td>");
                response.append(resultSet.getString("first_name"));
                response.append("</td><td>");
                response.append(resultSet.getString("last_name"));
                response.append("</td><td>");
                response.append(resultSet.getString("salary"));
                response.append("</td><td>");
                response.append("<form method=\"get\" action=\"/users/edit\"><input type=\"submit\" value=\"Edit\">");
                response.append("<input name=\"user_id\" type=\"hidden\" value=\"").append(userId).append("\"></form></td><td>");
                response.append("<form method=\"post\" action=\"/users/remove\"><input type=\"submit\" value=\"Remove\">");
                response.append("<input name=\"user_id\" type=\"hidden\" value=\"").append(userId).append("\"></form>");
                response.append("</td></tr>\n");
            }
        } catch (SQLException e) {
            System.out.println("Can't get user list from database.");
            e.printStackTrace();
        }
        return response.append("</table>\n").toString();
    }
}

