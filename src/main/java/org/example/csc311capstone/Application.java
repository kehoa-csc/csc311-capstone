package org.example.csc311capstone;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    ID, NAME, CURRBOOK, EMAIL, RETURNDATE, BORROWDATE;
}

/**
 * hold the column name which is columns from book table
 */
enum booksColumns {
    ID,ISBN,NAME,AUTHOR,EDITION,QUANTITY,COPIESLEFT;
}

public class Application extends javafx.application.Application {

    private static final ConnDbOps cdbop = new ConnDbOps();;
    private static final PatronsTable patronsTable = new PatronsTable();
    private static final BooksTable booksTable = new BooksTable();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();

        //Testing database connection. ToDo: Will have login screen before this that passes in the credentials.
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


        if (opt == 'c') { //patron manager mode
            patronMode(opt,scnr);
        } else if (opt == 'b') { //Book manager mode
            bookMode(opt,scnr);
        }else {
            System.out.println("Invalid option");
        }

    }

    /**
     * Provides an interactive mode for managing patrons in the library system.
     * Allows users to add, display, edit, remove, and query patrons.
     *
     * @param opt The initial option chosen by the user.
     * @param scnr The Scanner object used to read user input.
     */
    public static void patronMode(char opt,Scanner scnr){
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

                    //freely edit the value in patron table
                    Map<String, Object> addPatronInfo = new HashMap<>();
                    addPatronInfo.put(patronsColumns.NAME.name(), name);
                    addPatronInfo.put(patronsColumns.EMAIL.name(), email);

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

                    //freely edit the value in patron table
                    Map<String, Object> updatePatronInfo = new HashMap<>();
                    updatePatronInfo.put(patronsColumns.NAME.name(), newName);
                    updatePatronInfo.put(patronsColumns.EMAIL.name(), newEmail);

                    patronsTable.editPatron(updatePatronInfo,id); // calling from PatronsTable

                    // cdbop.editPatron(newName,newEmail); //Not ready yet, we need Display first
                    break;
                case 'r':
                    System.out.print("Please enter ID of user to delete:");
                    int ID = scnr.nextInt();
                    patronsTable.deletePatron(ID);
                    break;
                case 'q':
                    System.out.print("Please enter a name your want to query: ");
                    String Name = scnr.next();
                    patronsTable.searchPatronByName(Name);
                    break;
                case 'b':
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
     * @param opt The initial option selected by the user. Controls the menu flow.
     * @param scnr The Scanner object used to read user input from the console.
     */
    public static void bookMode(char opt,Scanner scnr) {
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
                    System.out.print("Please enter a name: ");
                    String name = scnr.next();
                    System.out.print("Please enter an author: ");
                    String author = scnr.next();

                    Map<String, Object> addBookInfo = new HashMap<>();
                    addBookInfo.put(booksColumns.NAME.name(), name);
                    addBookInfo.put(booksColumns.AUTHOR.name(), author);

                    booksTable.addBook(addBookInfo);
                    break;

            }
        }
    }
}