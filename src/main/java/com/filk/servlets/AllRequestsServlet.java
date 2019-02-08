package com.filk.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

public class AllRequestsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String filePath = request.getPathInfo().substring(1).replace('/', File.separatorChar);
        File file = new File(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream servletOutputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            servletOutputStream.write(Arrays.copyOf(buffer, bytesRead));
        }
        servletOutputStream.flush();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
