package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Patron;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
/*

    NOTE-THIS IS CURRENTLY OUT OF DATE.
    CHANGES HAVE BEEN MADE TO THE DATABASE SINCE THIS WAS WRITTEN, AND IT NEEDS TO BE UPDATED LATER.
    DO NOT USE FOR THE TIME BEING.

 */

/**
 * control the books table in database , which extends from ConnDbOps
 * @author zuxin
 */
public class PatronsTable extends ConnDbOps{

    /**
     * add patron to table patrons
     *
     * @author zuxin
     * @param patron a patron items hold a patron's information
     */
    public void addPatron(Patron patron) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO patrons (name,currBook, email, borrowTime,returnTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, patron.getName());
            preparedStatement.setString(2, patron.getcurrBook());
            preparedStatement.setString(3, patron.getEmail());
            preparedStatement.setString(4, patron.getBorrowTime());
            preparedStatement.setString(5, patron.getReturnTime());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new patron was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a patron from table patrons
     *
     * @author zuxin
     * @param patron a patron should be deleted
     */
    public void deletePatron(Patron patron) {
        int ID = patron.getID();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM patrons WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ID);

            int row = preparedStatement.executeUpdate();

            if (row == 0) {
                System.out.println("A patron was deleted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * update the patron info in table patrons
     *
     * @author zuxin
     * @param patron new patron info
     */
    public void editPatron(Patron patron) {
        //ToDo: Make scanner and GUI take input for this, instead of .getters

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE patron Set name = ?, currBook = ?, email = ?, borrowTime = ? returnTime = ? WHERE id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            if(patron.getName() != null){  // if name has new value need to change
                preparedStatement.setString(1, patron.getName());
            }
            if(patron.getcurrBook()!= null){// if currBook has new value need to change
                preparedStatement.setString(2, patron.getcurrBook());
            }
            if(patron.getEmail() != null){// if Email has new value need to change
                preparedStatement.setString(3, patron.getEmail());
            }
            if(patron.getBorrowTime() != null){// if BorrowTime has new value need to change
                preparedStatement.setString(4, patron.getBorrowTime());
            }
            if(patron.getReturnTime() != null){// if ReturnTime has new value need to change
                preparedStatement.setString(5, patron.getReturnTime());
            }
            // id is necessary condition to update data in Database, so it shouldn't be null
            preparedStatement.setInt(6, patron.getID());



            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * search a patron by patron's name
     *
     * @author zuxin
     * @param name name of patron need to find
     */
    public void searchPatronByName(String name){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM patrons WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patronName = resultSet.getString("name");
                String currBook = resultSet.getString("currBook");
                String email = resultSet.getString("email");
                String borrowTime = resultSet.getString("borrowTime");
                String returnTime = resultSet.getString("returnTime");
                System.out.println("ID: " + id
                        + ", Name: " + patronName
                        + ", currBook: " + currBook
                        + ", email: " + email
                        + ", borrowTime: " + borrowTime
                        + ", returnTime: " + returnTime);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * display all patron from table patrons
     *
     * @author zuxin
     */
    public void listAllPatrons() {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM patrons ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patronName = resultSet.getString("name");
                String currBook = resultSet.getString("currBook");
                String email = resultSet.getString("email");
                String borrowTime = resultSet.getString("borrowTime");
                String returnTime = resultSet.getString("returnTime");
                System.out.println("ID: " + id
                        + ", Name: " + patronName
                        + ", currBook: " + currBook
                        + ", email: " + email
                        + ", borrowTime: " + borrowTime
                        + ", returnTime: " + returnTime);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
