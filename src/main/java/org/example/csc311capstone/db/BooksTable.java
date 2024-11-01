package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Book;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
/*

    NOTE-THIS IS CURRENTLY OUT OF DATE.
    CHANGES HAVE BEEN MADE TO THE DATABASE SINCE THIS WAS WRITTEN, AND IT NEEDS TO BE UPDATED LATER.
    DO NOT USE FOR THE TIME BEING.

 */
/**
 * control the book table in a database, which extends from ConnDbOps
 * @Author zuxin chen
 */
public class BooksTable extends ConnDbOps{
    private final static String TABLE_NAME = "books";

    /**
     * Adds a book to the database. If the book already exists, increases the quantity and copies left.
     *
     * @param addBookInfo A map containing the book's details, with column names as keys and their corresponding values.
     */
    public void addBook(Map<String, Object> addBookInfo) {

        if (addBookInfo == null || addBookInfo.isEmpty()) {
            System.out.println("The book information is empty or null. Unable to add book.");
            return;
        }


        //set SQL with insert new data
        StringBuilder sql = new StringBuilder("INSERT INTO " + TABLE_NAME +" ");
        StringJoiner columnsJoiner = new StringJoiner(", ");
        StringJoiner valuesJoiner = new StringJoiner(", ");

        sql.append("(");
        for (String key : addBookInfo.keySet()) {
            columnsJoiner.add(key);
            valuesJoiner.add("?");
        }

        sql.append(columnsJoiner); // join "," between each column, not in the end
        sql.append(") VALUES (");
        sql.append(valuesJoiner); // join "," between each value, not in the end
        sql.append(")");
        //increase quantity and copiesLeft when a book is existed
        sql.append(" ON DUPLICATE KEY UPDATE quantity = quantity + 1, copiesLeft = copiesLeft + 1");

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())){

            int index = 1;
            for (Object value : addBookInfo.values()) {
                preparedStatement.setObject(index++, value);
            }

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new books was inserted successfully.");
            }else {
                System.out.println("Inserting the new book failed, no rows affected.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while accessing the database.");
        }
    }

    /**
     * Removes a book from the database based on its unique identifier.
     *
     * @param id The unique identifier of the book to be removed. If the id is 0 or if the book does not
     *           exist, the method will print an error message and return. Depending on the book's
     *           quantity and copies left, it will either delete the book or update its stock.
     */
    public void removeBook(int id){

        if (id == 0) {
            System.out.println("No book id is 0, unable to delete book.");
            return;
        }
        /*
            if CopiesLeft == 1 && Quantity == 1, able to delete a book from table
            if CopiesLeft == 0 && Quantity == 1, a book has been browsed, it needs to return
            if CopiesLeft < 0 && Quantity < 1, error number of the book
            if CopiesLeft > 1 && Quantity > 1, a book should not be deleted, just decrease the CopiesLeft and Quantity
         */
        Map<String, Object> oldBookInfo = new HashMap<>();
        oldBookInfo.put("id",id);
        Book book = searchBook(oldBookInfo);
        if (book == null) {
            System.out.println("This book does not exists, unable to delete book.");
            return;
        }
        if (book.getCopiesLeft() == 0 &&  book.getQuantity() == 1) {
            System.out.println("A book has been checked out and needs to be returned");
            return;
        }
        if(book.getCopiesLeft() < 0 &&  book.getQuantity() < 1){
            System.out.println("Error!!! The number of this book are invalid, please check the number of this book");
            return;
        }
        if (book.getCopiesLeft() > 1 &&  book.getQuantity() > 1) {
            // Book have much, updating its quantity and copiesLeft
            oldBookInfo.put("quantity", book.getQuantity()-1);
            oldBookInfo.put("copiesLeft", book.getCopiesLeft()-1);
            editBook(oldBookInfo, id);
        }else {
            deleteBookById(id);
        }

    }

    private void deleteBookById(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A book was deleted successfully.");
            } else {
                System.out.println("Deleting the book failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits the details of an existing book in the database.
     *
     * @param updateBookInfo A map containing the column names as keys and the updated values as values.
     * @param id The unique identifier of the book to be edited.
     */
    public void editBook(Map<String, Object> updateBookInfo, int id) {

        if (updateBookInfo == null || updateBookInfo.isEmpty() || id == 0) {
            System.out.println("The book information is empty or null, and no book id is 0. Unable to edit book.");
            return;
        }

        StringBuilder sql = new StringBuilder("UPDATE "+ TABLE_NAME +" Set ");
        StringJoiner joiner = new StringJoiner(", ");

        for (String key : updateBookInfo.keySet()) {
            joiner.add(key + " = ?");
        }
        sql.append(joiner); // join "," between each, not in the end
        sql.append(" WHERE id = ? ");

        //try-with-resources, these resources are automatically shut down
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())){

            int index = 1;
            for (Object object: updateBookInfo.values()) {
                preparedStatement.setObject(index++, object);
            }

            preparedStatement.setObject(index, id); // set id last one

            int row = preparedStatement.executeUpdate();
            if (row == 0) {
                System.out.println("Updating book failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Searches for a book in the database using the specified search criteria.
     *
     * @param searchBookInfo A map containing the search criteria, where keys are column names and values are the corresponding search values.
     * @return The book that matches the search criteria or a new Book object if no match is found.
     */
    public Book searchBook(Map<String,Object> searchBookInfo){

        if (searchBookInfo == null || searchBookInfo.isEmpty()) {
            System.out.println("The book information is empty or null. Unable to search book.");
            return null;
        }

        // Construct SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" WHERE ");
        StringJoiner joiner = new StringJoiner(" AND ");

        for (String key : searchBookInfo.keySet()) {
            joiner.add(key + " = ?");
        }
        sql.append(joiner);

        Book book = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object object: searchBookInfo.values()) {
                preparedStatement.setObject(index++, object);
            }

            // close the ResultSet after use
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book();
                    book.setId(resultSet.getInt("id"));
                    book.setISBN(resultSet.getInt("ISBN"));
                    book.setName(resultSet.getString("name"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setEdition(resultSet.getString("edition"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setCopiesLeft(resultSet.getInt("copiesLeft"));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    /**
     * displace all books from table books
     */
    public void listAllBooks() {

        String sql = "SELECT * FROM "+ TABLE_NAME +" ";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setISBN(resultSet.getInt("ISBN"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setEdition(resultSet.getString("edition"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setCopiesLeft(resultSet.getInt("copiesLeft"));
                System.out.println(book);
            }
            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
