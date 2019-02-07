package com.filk.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {


//        Map<String, Object> pageVariables = createPageVariablesMap(request);
//        pageVariables.put("message", "");

        //response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));

        String filePath = request.getPathInfo().substring(1).replace('/', File.separatorChar);
        File file = new File(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        //BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        PrintWriter printWriter = response.getWriter();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            printWriter.write(Arrays.copyOf(buffer, bytesRead).toString());
        }
        printWriter.flush();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
