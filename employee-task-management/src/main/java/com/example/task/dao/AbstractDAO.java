package com.example.task.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDAO {
    protected Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) throw new RuntimeException("db.properties not found");
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.username");
        String pass = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, pass);
    }
}
