package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Patron;

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
            String sql = "INSERT INTO patrons (name,currBook, email, returnDate,borrowDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, patron.getName());
            preparedStatement.setInt(2, patron.getCurrBook());
            preparedStatement.setString(3, patron.getEmail());
            preparedStatement.setString(4, patron.getReturnDate());
            preparedStatement.setString(5, patron.getBorrowDate());

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
     * @param id get id of a patron should be deleted
     */
    public void deletePatron(int id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM patrons WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

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
            String sql = "UPDATE patrons Set name = ?, currBook = ?, email = ?, returnDate = ?, borrowDate = ? WHERE id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, patron.getName());
            preparedStatement.setInt(2, patron.getCurrBook());
            preparedStatement.setString(3, patron.getEmail());
            preparedStatement.setString(4, patron.getReturnDate());
            preparedStatement.setString(5, patron.getBorrowDate());
            // id is necessary condition to update data in Database, so it should be an integer
            preparedStatement.setInt(6, patron.getID());


            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                System.out.println("Updating patron failed, no rows affected");
            }

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
            String sql = "SELECT * FROM patrons WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patronName = resultSet.getString("name");
                String currBook = resultSet.getString("currBook");
                String email = resultSet.getString("email");
                String returnDate = resultSet.getString("returnDate");
                String borrowDate = resultSet.getString("borrowDate");
                System.out.println("ID: " + id
                        + ", Name: " + patronName
                        + ", currBook: " + currBook
                        + ", email: " + email
                        + ", returnDate: " + returnDate
                        + ", borrowDate: " + borrowDate);
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
                String returnDate = resultSet.getString("returnDate");
                String borrowDate = resultSet.getString("borrowDate");
                System.out.println("ID: " + id
                        + ", Name: " + patronName
                        + ", currBook: " + currBook
                        + ", email: " + email
                        + ", returnDate: " + returnDate
                        + ", borrowDate: " + borrowDate);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
