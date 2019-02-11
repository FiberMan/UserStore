package com.filk.main;

import com.filk.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        AddUsersServlet addUsersServlet = new AddUsersServlet();
        EditUsersServlet editUsersServlet = new EditUsersServlet();
        RemoveUsersServlet removeUsersServlet = new RemoveUsersServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(addUsersServlet), "/users/add");
        context.addServlet(new ServletHolder(editUsersServlet), "/users/edit");
        context.addServlet(new ServletHolder(removeUsersServlet), "/users/remove");
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}

