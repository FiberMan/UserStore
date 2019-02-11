package com.filk.utils;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JdbcClient {
    private final String propertiesPath = "config\\database.txt";
    private Connection connection;
    private Statement statement;
    private static JdbcClient jdbcClient;

    public static JdbcClient instance() {
        if (jdbcClient == null) {
            jdbcClient = new JdbcClient();
        }
        return jdbcClient;
    }

    private JdbcClient() {
        try(FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            connection = DriverManager.getConnection(properties.getProperty("sqlite.db.url"));
        } catch (SQLException | IOException e) {
            System.out.println("Connection to database failed.");
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Query execution failed.");
            e.printStackTrace();
        }
        return resultSet;
    }

    public Integer executeUpdate(String query) {
        int updatedRows;
        try {
            statement = connection.createStatement();
            updatedRows = statement.executeUpdate(query);
            close();
        } catch (SQLException e) {
            System.out.println("Update execution failed:");
            System.out.println(query);
            e.printStackTrace();
            updatedRows = -1;
        }
        return updatedRows;
    }

    public void close() {
        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed to close Statement.");
            e.printStackTrace();
        }
    }
}
