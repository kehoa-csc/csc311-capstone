package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Patron;
import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;

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

        if (checkMapInfo(addPatronInfo)) {
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
    public void removePatron(int id) {

        if (id == 0) {
            System.out.println("No patron id is 0, unable to delete patron.");
            return;
        }

        String sql = "DELETE FROM "+ TABLE_NAME +" WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
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
        
        if (checkMapInfo(updatePatronInfo) || id == 0) {
            System.out.println("The patron information to be updated is empty or null or the id is 0. Unable to update patron.");
            return;
        }

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
     * Queries the database for a patron based on the specified criteria provided in the map.
     * Returns the patron information if found.
     *
     * @param queryPatronInfo A map containing the search criteria for the patron, with keys as column names and values as the search values.
     * @return The patron information if found, otherwise null.
     */
    public Patron queryPatron(Map<String,Object> queryPatronInfo){

        if (checkMapInfo(queryPatronInfo)) {
            System.out.println("The patron information is empty or null. Unable to search book.");
            return null;
        }

        // Construct SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" WHERE ");
        StringJoiner joiner = new StringJoiner(" AND ");

        for (String key : queryPatronInfo.keySet()) {
            joiner.add(key + " = ?");
        }
        sql.append(joiner);

        Patron patron = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object object: queryPatronInfo.values()) {
                preparedStatement.setObject(index++, object);
            }

            // close the ResultSet after use
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    patron = new Patron();
                    patron.setID(resultSet.getInt("id"));
                    patron.setName(resultSet.getString("name"));
                    patron.setCurrBook(resultSet.getInt("currBook"));
                    patron.setEmail(resultSet.getString("email"));
                    patron.setReturnDate(resultSet.getString("returnDate"));
                    patron.setBorrowDate(resultSet.getString("borrowDate"));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patron;
    }

    /**
     * display all patrons from table patrons
     * if you need to display on a window, not screen, than you can return a patron object out
     * @author zuxin
     */
    public void listAllPatrons() {

        String sql = "SELECT * FROM "+ TABLE_NAME;
        Patron patron;

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()) {
                patron = new Patron();
                patron.setID(resultSet.getInt("id"));
                patron.setName(resultSet.getString("name"));
                patron.setCurrBook(resultSet.getInt("currBook"));
                patron.setEmail(resultSet.getString("email"));
                patron.setReturnDate(resultSet.getString("returnDate"));
                patron.setBorrowDate(resultSet.getString("borrowDate"));
                System.out.println(patron);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows a patron to borrow a book from the library.
     * The method updates the book and the patron information
     * when a book exists and haves copies left, also and a patron exists
     *
     * @param patronId The ID of the patron borrowing the book.
     * @param bookId The ID of the book to be borrowed.
     */
    public void borrowBook(int patronId, int bookId) {
        if (patronId == 0 || bookId == 0) {
            System.out.println("No patron or book id is 0, unable to borrow a book.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            processBookBorrowing(patronId, bookId, conn);

        } catch (SQLException e) {
            System.out.println("An error occurred while connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Processes a book borrowing transaction in the library.
     * Updates the book's available copies and the patron's current borrowed book information.
     * The method ensures both updates are committed or both are rolled back to maintain consistency.
     *
     * @param patronId The ID of the patron borrowing the book.
     * @param bookId The ID of the book to be borrowed.
     * @param conn The database connection to be used for the transaction.
     * @throws SQLException If a database access error occurs or the transaction fails.
     */
    private static void processBookBorrowing(int patronId, int bookId, Connection conn) throws SQLException {
        conn.setAutoCommit(false); // start the transaction for two operations should both works

        String sqlBook = "UPDATE books SET copiesLeft = copiesLeft - 1 WHERE id = ? AND copiesLeft > 0";
        String sqlPatron = "UPDATE patrons SET currBook = ? WHERE id = ?";

        try (PreparedStatement bookUpdateStatement = conn.prepareStatement(sqlBook);
             PreparedStatement patronUpdateStatement = conn.prepareStatement(sqlPatron)) {

            // Update book information
            bookUpdateStatement.setInt(1, bookId);
            int bookRows = bookUpdateStatement.executeUpdate();
            if (bookRows == 0) {
                conn.rollback(); // Roll back the transaction
                System.out.println("Cannot borrow book, either the book does not exist or there are no copies left.");
            }else if(bookRows > 0) {

                // Update patron information
                patronUpdateStatement.setInt(1, bookId);
                patronUpdateStatement.setInt(2, patronId);
                int patronRows = patronUpdateStatement.executeUpdate();
                if (patronRows > 0) {
                    conn.commit(); // Commit the transaction, all things are working
                    System.out.println("Patron has borrowed book successfully.");
                } else {
                    conn.rollback(); // Roll back the transaction, patron not exists
                    System.out.println("Unable to update patron information; patron may not exist.");
                }
            }
        } catch (SQLException e) {
            conn.rollback(); // Roll back a transaction when an exception is caught
            System.out.println("An error occurred while processing the transaction: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
        }
    }


    /**
     * Checks the validity of the provided map containing object information.
     *
     * @param objectInfo A map containing object information with keys as strings and values as objects.
     * @return true if the map is not null, not empty, and all values are non-null; false otherwise.
     */
    private boolean checkMapInfo(Map<String,Object> objectInfo) {
        if (objectInfo == null || objectInfo.isEmpty()) {
            return false;
        }
        for (Object value : objectInfo.values()) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }

}
