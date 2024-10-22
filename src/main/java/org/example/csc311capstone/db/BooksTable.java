package org.example.csc311capstone.db;

import org.example.csc311capstone.Module.Book;

import java.sql.*;

/**
 * control the books table in database,which extends from ConnDbOps
 * @author zuxin
 */
public class BooksTable extends ConnDbOps{

    /**
     * add book to table books
     *
     * @author zuxin
     * @param book book items hold a book's information
     */
    public  void addBook(Book book) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO books (ISBN, bookName,author, edition, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, book.getISBN());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getEdition());
            preparedStatement.setInt(5, book.getQuantity());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new book was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a book from table books
     *
     * @author zuxin
     * @param book a book should be deleted
     */
    public void deleteBook(Book book) {
        int ID = book.getId();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ID);

            int row = preparedStatement.executeUpdate();

            if (row == 0) {
                System.out.println("A book was deleted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * update the book info in table books
     *
     * @author zuxin
     * @param book new book info
     */
    public  void editBook(Book book) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE books Set ISBN = ?, bookName = ?, author = ?, edition = ? quantity = ? WHERE id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, book.getISBN());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getEdition());
            preparedStatement.setInt(5, book.getQuantity());
            preparedStatement.setInt(6, book.getId());

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * search a book by book name
     *
     * @author zuxin
     * @param name name of book need to find
     */
    public void searchBookByName(String name){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM books WHERE bookName = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ISBN = resultSet.getInt("ISBN");
                String bookName = resultSet.getString("bookName");
                String author = resultSet.getString("author");
                String edition = resultSet.getString("edition");
                int quantity = resultSet.getInt("quantity");
                System.out.println("ID: " + id
                                + ", ISBN: " + ISBN
                                + ", bookName: " + bookName
                                + ", author: " + author
                                + ", edition: " + edition
                                + ", quantity: " + quantity);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * displace all book from table books
     *
     * @author zuxin
     */
    public  void listAllBooks() {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM books ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ISBN = resultSet.getInt("ISBN");
                String bookName = resultSet.getString("bookName");
                String author = resultSet.getString("author");
                String edition = resultSet.getString("edition");
                int quantity = resultSet.getInt("quantity");
                System.out.println("ID: " + id
                        + ", ISBN: " + ISBN
                        + ", bookName: " + bookName
                        + ", author: " + author
                        + ", edition: " + edition
                        + ", quantity: " + quantity);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
