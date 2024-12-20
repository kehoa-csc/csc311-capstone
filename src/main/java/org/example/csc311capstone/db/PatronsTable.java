package org.example.csc311capstone.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.csc311capstone.Module.Patron;

import java.sql.*;
import java.time.LocalDate;
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

        if (!checkMapInfo(addPatronInfo)) {
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
        
        if (!checkMapInfo(updatePatronInfo) || id == 0) {
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

        if (!checkMapInfo(queryPatronInfo)) {
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
                    patron.setPassword(resultSet.getString("password"));
                    patron.setBorrowDays(resultSet.getInt("borrowDays"));
                }

            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patron;
    }

    /**
     * Searches for patrons in the database using the name.
     *
     * @param name the search value for the patron name
     * @return The list of patrons that match the search criteria
     */
    public ObservableList<Patron> fuzzyMatchPatronByName(String name) {
        ObservableList<Patron> patronList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + name + "%"); // Use % for fuzzy matching

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Patron patron = new Patron();
                    patron.setID(resultSet.getInt("id"));
                    patron.setName(resultSet.getString("name"));
                    patron.setCurrBook(resultSet.getInt("currBook"));
                    patron.setEmail(resultSet.getString("email"));
                    patron.setReturnDate(resultSet.getString("returnDate"));
                    patron.setBorrowDate(resultSet.getString("borrowDate"));
                    patron.setPassword(resultSet.getString("password"));
                    patron.setBorrowDays(resultSet.getInt("borrowDays"));
                    patronList.add(patron);
                }
            }

            if (patronList.isEmpty()) {
                System.out.println("Not search patrons");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patronList;
    }

    /**
     * display all patrons from table patrons
     * if you need to display on a window, not screen, than you can return a patron object out
     *
     * @return
     * @author zuxin
     */
    public ObservableList<Patron> listAllPatrons() {

        ObservableList<Patron> patrons = FXCollections.observableArrayList();
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
                patron.setPassword(resultSet.getString("password"));
                patron.setBorrowDays(resultSet.getInt("borrowDays"));
                System.out.println(patron);
                patrons.add(patron);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patrons;
    }

    /**
     * Allows a patron to borrow a book from the library.
     * The method updates the book and the patron information
     * when a book exists and haves copies left, also and a patron exists
     *
     * @param patronId The ID of the patron borrowing the book.
     * @param bookId The ID of the book to be borrowed.
     */
    public void borrowBook(int patronId, int bookId, int borrowDays) {
        if (patronId == 0 || bookId == 0) {
            System.out.println("No patron or book id is 0, unable to borrow a book.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            processBookBorrowing(patronId, bookId, borrowDays, conn);

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
    private static void processBookBorrowing(int patronId, int bookId, int borrowDays, Connection conn) throws SQLException {
        conn.setAutoCommit(false); // start the transaction for two operations should both works

        String sqlBook = "UPDATE books SET copiesLeft = copiesLeft - 1 WHERE id = ? AND copiesLeft > 0";
        String sqlPatron = "UPDATE patrons SET currBook = ?, borrowDate = ?, borrowDays = ? WHERE id = ?";

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
                patronUpdateStatement.setString(2, LocalDate.now().toString()); //YYYY-MM-DD
                patronUpdateStatement.setInt(3, borrowDays);
                patronUpdateStatement.setInt(4, patronId);
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



    //Method to retrieve id from database where it is auto-incremented.
    public int retrieveId(Patron p) {
        connectToDatabase();
        int id;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT id FROM "+TABLE_NAME+" WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
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


    //NOTE: This doesn't work yet, but it might be worth finishing later?
    //-Andrew
    public Patron findPatronById(int id) {
        connectToDatabase();
        Patron patron = new Patron();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ""+id);

        //todo: check if count of select is greater than 0

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    patron.setID(resultSet.getInt("id"));
                    patron.setName(resultSet.getString("name"));
                    patron.setCurrBook(resultSet.getInt("currBook"));
                    patron.setEmail(resultSet.getString("email"));
                    patron.setReturnDate(resultSet.getString("returnDate"));
                    patron.setBorrowDate(resultSet.getString("borrowDate"));
                    patron.setPassword(resultSet.getString("password"));
                    patron.setBorrowDays(resultSet.getInt("borrowDays"));
                }
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return patron;
    }


    /**
     Updates the patron's name and email in the database.
     @param id    The ID of the patron to update.
     @param name  The new name for the patron.
     @param email The new email for the patron.
     @author samin
     */
    public void updatePatron(int id, String name, String email) {
        String sql = "UPDATE " + TABLE_NAME + " SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Patron updated successfully.");
            } else {
                System.out.println("No patron found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
