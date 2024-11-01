package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Patron;

import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;
/*

    NOTE-THIS IS CURRENTLY OUT OF DATE.
    CHANGES HAVE BEEN MADE TO THE DATABASE SINCE THIS WAS WRITTEN, AND IT NEEDS TO BE UPDATED LATER.
    DO NOT USE FOR THE TIME BEING.

 */


/**
 * control patrons table in a database, which extends from ConnDbOps
 * @author zuxin
 */
public class PatronsTable extends ConnDbOps{
    private final static String TABLE_NAME = "patrons";

    /**
     * Adds a new patron to the database.
     * The details of the patron to be added are passed in as a map where the keys
     * are column names and the values are the respective data.
     *
     * @param addPatronInfo A map containing the column names and values for the new patron.
     */
    public void addPatron(Map<String, Object> addPatronInfo) {

        if (addPatronInfo == null || addPatronInfo.isEmpty()) {
            System.out.println("The patron information is empty or null. Unable to add patron.");
            return;
        }

        StringBuilder sql = new StringBuilder("INSERT INTO " + TABLE_NAME +" ");
        StringJoiner columnsJoiner = new StringJoiner(", ");
        StringJoiner valuesJoiner = new StringJoiner(", ");

        sql.append("(");
        for (String key : addPatronInfo.keySet()) {
            columnsJoiner.add(key);
            valuesJoiner.add("?");
        }

        sql.append(columnsJoiner); // join "," between each column, not in the end
        sql.append(") VALUES (");
        sql.append(valuesJoiner); // join "," between each value, not in the end
        sql.append(")");

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())){

            int index = 1;
            for (Object value : addPatronInfo.values()) {
                preparedStatement.setObject(index++, value);
            }

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new patron was inserted successfully.");
            }else {
                System.out.println("Inserting the new patron failed, no rows affected.");
            }

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

        String sql = "DELETE FROM "+ TABLE_NAME +" WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            int row = preparedStatement.executeUpdate();

            if (row == 0) {
                System.out.println("A patron was deleted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates the information of a patron in the database.
     * The details of the patron to be updates are passed in as a map where the keys
     * are column names and the values are the new data.
     *
     * @param updatePatronInfo A map of column names and their respective new values to update.
     * @param id The unique id of the patron
     */
    public void editPatron(Map<String, Object> updatePatronInfo, int id) {

        StringBuilder sql = new StringBuilder("UPDATE "+ TABLE_NAME +" Set ");
        StringJoiner joiner = new StringJoiner(", ");

        for (String key : updatePatronInfo.keySet()) {
            joiner.add(key + " = ?");
        }
        sql.append(joiner); // join "," between each, not in the end
        sql.append(" WHERE id = ? ");

        //try-with-resources, these resources are automatically shut down
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())){

            int index = 1;
            for (Object object: updatePatronInfo.values()) {
                preparedStatement.setObject(index++, object);
            }

            preparedStatement.setObject(index, id); // set id last one

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                System.out.println("Updating patron failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * search a patron by patron's name
     *
     * @author zuxin
     * @param name name of patron needs to find
     */
    public void searchPatronByName(String name){

        String sql = "SELECT * FROM "+ TABLE_NAME +" WHERE name = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patron patron = new Patron();
                patron.setID(resultSet.getInt("id"));
                patron.setName(name);
                patron.setCurrBook(resultSet.getInt("currBook"));
                patron.setEmail(resultSet.getString("email"));
                patron.setReturnDate(resultSet.getString("returnDate"));
                patron.setBorrowDate(resultSet.getString("borrowDate"));

                System.out.println(patron);
            }
            resultSet.close();

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

        String sql = "SELECT * FROM "+ TABLE_NAME +" ";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patron patron = new Patron();
                patron.setID(resultSet.getInt("id"));
                patron.setName(resultSet.getString("name"));
                patron.setCurrBook(resultSet.getInt("currBook"));
                patron.setEmail(resultSet.getString("email"));
                patron.setReturnDate(resultSet.getString("returnDate"));
                patron.setBorrowDate(resultSet.getString("borrowDate"));
                System.out.println(patron);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(int id){

        if (id == 0) {
            System.out.println("No book id is 0, unable to borrow a book.");
            return;
        }

        String sql = "UPDATE books " +
                "SET quantity = quantity - 1, " +
                "copiesLeft = copiesLeft - 1 " +
                "WHERE id = ? AND quantity > 0 AND copiesLeft > 0";

        try  (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.out.println("Cannot borrow book, either the book does not exist or there are no copies left.");
            } else {
                System.out.println("Book borrowed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while borrowing the book: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
