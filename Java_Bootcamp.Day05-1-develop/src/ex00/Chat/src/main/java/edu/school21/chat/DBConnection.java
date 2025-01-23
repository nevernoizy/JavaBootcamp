package edu.school21.chat;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.core.parameters.P;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection(){
        Connection connection = null;
        String dbURl = null;
        String dbUser = null;
        String dbPassword = null;
        FileInputStream fis;
        Properties properties = new Properties();

        try{
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
            dbURl = properties.getProperty("db.host");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
            fis.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            connection = DriverManager.getConnection(dbURl,dbUser,dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
