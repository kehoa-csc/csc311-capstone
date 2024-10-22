package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Patron;

import java.sql.*;

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
            String sql = "INSERT INTO patrons (name,bookName, email, borrowTime,returnTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, patron.getName());
            preparedStatement.setString(2, patron.getBookName());
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
    public  void editPatron(Patron patron) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE patron Set name = ?, bookName = ?, email = ?, borrowTime = ? returnTime = ? WHERE id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, patron.getName());
            preparedStatement.setString(2, patron.getBookName());
            preparedStatement.setString(3, patron.getEmail());
            preparedStatement.setString(4, patron.getBorrowTime());
            preparedStatement.setString(5, patron.getReturnTime());
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
                String bookName = resultSet.getString("bookName");
                String email = resultSet.getString("email");
                String borrowTime = resultSet.getString("borrowTime");
                String returnTime = resultSet.getString("returnTime");
                System.out.println("ID: " + id
                        + ", Name: " + patronName
                        + ", bookName: " + bookName
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
     * displace all patron from table patrons
     *
     * @author zuxin
     */
    public void listAllPatrons() {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM books ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patronName = resultSet.getString("name");
                String bookName = resultSet.getString("bookName");
                String email = resultSet.getString("email");
                String borrowTime = resultSet.getString("borrowTime");
                String returnTime = resultSet.getString("returnTime");
                System.out.println("ID: " + id
                        + ", Name: " + patronName
                        + ", bookName: " + bookName
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
