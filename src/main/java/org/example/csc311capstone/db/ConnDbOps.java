package org.example.csc311capstone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDbOps {


        final String MYSQL_SERVER_URL = "jdbc:mysql://csc311kehoeserver.mysql.database.azure.com/";
        final String DB_URL = "jdbc:mysql://csc311kehoeserver.mysql.database.azure.com/capstone";
        final String USERNAME = "kehoa";
        final String PASSWORD = "m&tYCA*56LgX";

        public boolean connectToDatabase() {
            boolean hasRegistredUsers = false;


            try {
                //First, connect to MYSQL server and create the database if not created
                Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS capstone");
                statement.close();
                conn.close();

                //Second, connect to the database and create the table "users" if cot created
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                statement = conn.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS patrons ("
                        + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                        + "name VARCHAR(200) NOT NULL,"
                        + "email VARCHAR(200) NOT NULL UNIQUE,"
                        + "phone VARCHAR(200),"
                        + "address VARCHAR(200)"
                        + ")";
                statement.executeUpdate(sql);

                //check if we have users in the table users
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM patrons");

                if (resultSet.next()) {
                    int numUsers = resultSet.getInt(1);
                    if (numUsers > 0) {
                        hasRegistredUsers = true;
                    }
                }

                statement.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return hasRegistredUsers;
        }

}
