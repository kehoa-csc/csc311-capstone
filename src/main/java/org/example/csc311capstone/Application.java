package org.example.csc311capstone;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.csc311capstone.Module.Book;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.BooksTable;
import org.example.csc311capstone.db.ConnDbOps;
import org.example.csc311capstone.db.PatronsTable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * hold the column name which is columns from patron table
 * if change the columns from a database, please change it too
 * And mySQL is ignored case, these shows as a caption letter just mean there is constant value
 */
enum patronsColumns {
    ID, NAME, CURRBOOK, EMAIL, RETURNDATE, BORROWDATE, PASSWORD, BORROWDAYS
}

/**
 * hold the column name which is columns from book table
 */
enum booksColumns {
    ID,ISBN,NAME,AUTHOR,EDITION,QUANTITY,COPIESLEFT
}

public class Application extends javafx.application.Application {

    private static final ConnDbOps cdbop = new ConnDbOps();
    private static final PatronsTable patronsTable = new PatronsTable();
    private static final BooksTable booksTable = new BooksTable();

    @Override
    public void start(Stage stage) throws IOException {
        //Uncomment the frontend you are working on.
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("patron_self_service.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("librarian_view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("second_view.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 582, 400);
        stage.setTitle("Library Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //Testing database connection. ToDo: Will have login screen before this that passes in the credentials.
        //testDatabaseConnection();
        launch();
    }

    /**
     * Tests the database connection and provides an interactive menu for managing
     * patrons and books.
     */
    private static void testDatabaseConnection() {
        System.out.println("Connecting to database...");
        try {
            cdbop.connectToDatabase();
        } catch (Exception e) {
            System.out.println("Could not connect to database.\nEnsure your login details are correct and try again.");
            System.exit(1);
        }
        System.out.println("Connected successfully.");

        //Initial selection of managing patrons or adding books
        Scanner scnr = new Scanner(System.in);
        System.out.println("Press \"c\" to view patron options.");
        System.out.println("Press \"b\" to view or add books.");
        char opt = scnr.next().charAt(0);

        while (true) {
            if (opt == 'c') { //patron manager mode
                patronMode(opt, scnr);
            } else if (opt == 'b') { //Book manager mode
                bookMode(opt, scnr);
            } else if(opt == 'q') {
                System.out.println("Quit application");
                System.exit(0);
            }
            else {
                System.out.println("Invalid option");
            }
        }
    }

    /**
     * Provides an interactive mode for managing patrons in the library system.
     * Allows users to add, display, edit, remove, query patrons, and borrow a book.
     *
     * @param opt The initial option chosen by the user.
     * @param scnr The Scanner object used to read user input.
     */
    private static void patronMode(char opt,Scanner scnr){
        while (opt != 't') {
            System.out.println("Select an option:");
            System.out.println("a: Add a new patron");
            System.out.println("d: Display all patrons");
            System.out.println("e: Edit a patron's information");
            System.out.println("r: Remove a patron");
            System.out.println("q: Query patron by name");
            System.out.println("b: Borrowing of book status");
            System.out.println("t: Quit application");
            opt = scnr.next().charAt(0);
            switch (opt) {
                case 'a':
                    System.out.print("Please enter a name: ");
                    String name = scnr.next();
                    System.out.print("Please enter an email: ");
                    String email = scnr.next();
                    System.out.print("Please enter a password: ");
                    String password = scnr.next();

                    //freely edit the value in patron table
                    Map<String, Object> addPatronInfo = new HashMap<>();
                    addPatronInfo.put(patronsColumns.NAME.name(), name);
                    addPatronInfo.put(patronsColumns.EMAIL.name(), email);
                    addPatronInfo.put(patronsColumns.PASSWORD.name(), password);

                    patronsTable.addPatron(addPatronInfo);// calling from PatronsTable

                    //cdbop.addPatron(name, email);
                    break;
                case 'd':
                    patronsTable.listAllPatrons(); // display all patrons
                    break;
                case 'e':
                    System.out.print("Please enter ID of user to edit:");
                    int id = scnr.nextInt();
                    System.out.print("Please enter a name: ");
                    String newName = scnr.next();
                    System.out.print("Please enter an email: ");
                    String newEmail = scnr.next();
                    System.out.print("Please enter a password: ");
                    String newPassword = scnr.next();

                    //freely edit the value in patron table
                    Map<String, Object> updatePatronInfo = new HashMap<>();
                    updatePatronInfo.put(patronsColumns.NAME.name(), newName);
                    updatePatronInfo.put(patronsColumns.EMAIL.name(), newEmail);
                    updatePatronInfo.put(patronsColumns.PASSWORD.name(), newPassword);

                    patronsTable.editPatron(updatePatronInfo,id); // calling from PatronsTable
                    break;
                case 'r':
                    System.out.print("Please enter ID of user to delete:");
                    int ID = scnr.nextInt();
                    patronsTable.removePatron(ID);
                    break;
                case 'q':
                    System.out.print("Please enter a name your want to query: ");
                    String Name = scnr.next();

//                    Map<String, Object> searchPatronInfo = new HashMap<>();
//                    searchPatronInfo.put(patronsColumns.NAME.name(), Name);
//                    Patron patron = patronsTable.queryPatron(searchPatronInfo);
//                    System.out.println(patron);
                    ObservableList<Patron> patrons = patronsTable.fuzzyMatchPatronByName(Name);
                    patrons.forEach(System.out::println);
                    break;
                case 'b':
                    System.out.print("Please enter ID of patron who want to borrow book:");
                    int patronId = scnr.nextInt();
                    System.out.print("Please enter ID of book to be borrowed:");
                    int bookId = scnr.nextInt();

                    patronsTable.borrowBook(patronId,bookId);
                    break;
                case 't':
                    System.out.println("Quit application");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    /**
     * Launches an interactive menu for managing books in the library system.
     * Provides options for adding, displaying, editing, removing, and querying books.
     *
     * @param opt The initial option selected by the user.
     * @param scnr The Scanner object used to read user input.
     */
    private static void bookMode(char opt,Scanner scnr) {
        int id;
        int ISBN;
        String name;
        String author;
        String edition;

        while (opt != 't') {
            System.out.println("Select an option:");
            System.out.println("a: Add a new book");
            System.out.println("d: Display all books");
            System.out.println("e: Edit a book's information");
            System.out.println("r: Remove a book");
            System.out.println("q: Query book by name");
            System.out.println("t: Quit application");
            opt = scnr.next().charAt(0);
            //ToDo: Book options go here. There should probably be different stuff like ISBN, # of copies...
            //Likewise, patron should have a "currently borrowed" field with a book ID.
            //Oh, and have the books table made in the connectToDatabase method too.

            switch (opt){
                case 'a':

                    System.out.print("Please enter a ISBN: ");
                    ISBN = scnr.nextInt();
                    System.out.print("Please enter a name: ");
                    name = scnr.next();
                    System.out.print("Please enter an author: ");
                    author = scnr.next();
                    System.out.print("Please enter a edition: ");
                    edition = scnr.next();

                    Map<String, Object> addBookInfo = new HashMap<>();
                    addBookInfo.put(booksColumns.ISBN.name(), ISBN);
                    addBookInfo.put(booksColumns.NAME.name(), name);
                    addBookInfo.put(booksColumns.AUTHOR.name(), author);
                    addBookInfo.put(booksColumns.EDITION.name(), edition);
                    //remember to enter quantity and copesLeft here
                    addBookInfo.put(booksColumns.QUANTITY.name(), 1);
                    addBookInfo.put(booksColumns.COPIESLEFT.name(), 1);

                    booksTable.addBook(addBookInfo);
                    break;
                case 'd':
                    booksTable.listAllBooks();
                    break;
                case 'e':
                    System.out.print("Please enter ID of user to edit:");
                    id = scnr.nextInt();
                    System.out.print("Please enter a name: ");
                    name = scnr.next();
                    System.out.print("Please enter an author: ");
                    author = scnr.next();

                    //freely edit the value in book table
                    Map<String, Object> updateBookInfo = new HashMap<>();
                    updateBookInfo.put(booksColumns.NAME.name(), name);
                    updateBookInfo.put(booksColumns.AUTHOR.name(), author);

                    booksTable.editBook(updateBookInfo,id);
                    break;
                case 'r':
                    System.out.print("Please enter ID of book to remove:");
                    id= scnr.nextInt();
                    booksTable.removeBook(id);
                    break;
                case 'q':
                    System.out.print("Please enter a name your want to query: ");
                    String Name = scnr.next();

//                    Map<String, Object> searchBookInfo = new HashMap<>();
//                    searchBookInfo.put(patronsColumns.NAME.name(), Name);
//                    Book book = booksTable.queryBook(searchBookInfo);
//                    System.out.println(book);

                    ObservableList<Book> bookList = booksTable.fuzzyMatchBookByName(Name);
                    bookList.forEach(System.out::println);
                    break;
                case 't':
                    System.out.println("Quit application");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");

            }
        }
    }
}