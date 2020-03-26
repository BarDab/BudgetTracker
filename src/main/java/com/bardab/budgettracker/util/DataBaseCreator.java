package com.bardab.budgettracker.util;

import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseCreator {

    public static void createMySQLDataBase(String username, String password){
        String url = "jdbc:mysql://localhost:3306/?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String sqlStatement = "CREATE DATABASE IF NOT EXISTS transactions_db";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sqlStatement)){

            statement.execute();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
