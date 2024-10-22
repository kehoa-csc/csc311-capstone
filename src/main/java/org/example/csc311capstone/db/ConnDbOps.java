package org.example.csc311capstone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnDbOps {

        final String MYSQL_SERVER_URL = "jdbc:mysql://csc311kehoeserver.mysql.database.azure.com/";
        final String DB_URL = "jdbc:mysql://csc311kehoeserver.mysql.database.azure.com/capstone";
        final String USERNAME = "kehoa";
        final String PASSWORD = "m&tYCA*56LgX";

        public boolean connectToDatabase() {
            boolean hasRegistred = false;


            try {
                //First, connect to MYSQL server and create the database if not created
                Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS capstone");
                statement.close();
                conn.close();

                //create patrons and books tables if they not be created
                if (createPatronsTable() && createBooksTable()){
                    hasRegistred = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return hasRegistred;
        }

    /**
     * create patrons table in database
     * @author zuxin
     */
    public boolean createPatronsTable() {
        boolean hasCreatePatrons = false;

        try {
            //connect to the database and create the table "patrons" if cot created
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS patrons ("
                    + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "bookName VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL,UNIQUE"
                    + "borrowTime VARCHAR(200) NOT NULL,"
                    + "returnTime VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //check if we have patrons in the table patrons
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM patrons");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasCreatePatrons = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasCreatePatrons;
    }

    /**
     * create books table in database
     * @author zuxin
     */
    public boolean createBooksTable(){
        boolean hasCreateBooks = false;
        try {
            //connect to the database and create the table "books" if cot created
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS books ("
                    + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "ISBN INT(10) NOT NULL,"
                    + "bookName VARCHAR(200) NOT NULL,"
                    + "author VARCHAR(200) NOT NULL"
                    + "edition VARCHAR(200) NOT NULL,"
                    + "quantity INT(10) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //check if we have books in the table books
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM books");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasCreateBooks = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasCreateBooks;
    }

}
