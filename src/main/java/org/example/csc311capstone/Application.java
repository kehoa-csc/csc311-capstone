package org.example.csc311capstone;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.ConnDbOps;
import org.example.csc311capstone.db.PatronsTable;

import java.io.IOException;
import java.util.Scanner;

public class Application extends javafx.application.Application {

    public static ConnDbOps cdbop;
    public static PatronsTable pt = new PatronsTable();

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
        cdbop = new ConnDbOps();
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
        boolean validOpt = false;
        while (!validOpt) {
            if (opt == 'c') {
                validOpt = true;
            } else if (opt == 'b') {
                validOpt = true;
            } else {
                System.out.println("Invalid option");
                opt = scnr.next().charAt(0);
            }
        }

        //patron manager mode
        if (opt == 'c') {
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

                        Patron addAPatron = new Patron();
                        addAPatron.setName(name);
                        addAPatron.setEmail(email);
                        pt.addPatron(addAPatron);// calling from PatronsTable

                        //cdbop.addPatron(name, email);
                        break;
                    case 'd':
                        pt.listAllPatrons(); // display all patrons
                        break;
                    case 'e':
                        System.out.print("Please enter ID of user to edit:");
                        int id = scnr.nextInt();
                        System.out.print("Please enter a name: ");
                        String newName = scnr.next();
                        System.out.print("Please enter an email: ");
                        String newEmail = scnr.next();

                        Patron editAPatron = new Patron();
                        editAPatron.setID(id);
                        editAPatron.setName(newName);
                        editAPatron.setEmail(newEmail);
                        pt.editPatron(editAPatron); // calling from PatronsTable

                       // cdbop.editPatron(newName,newEmail); //Not ready yet, we need Display first
                        break;
                    case 'r':
                        System.out.print("Please enter ID of user to delete:");
                        int ID = scnr.nextInt();
                        pt.deletePatron(ID);
                        break;
                    case 'q':
                        System.out.print("Please enter a name your want to query: ");
                        String Name = scnr.next();
                        pt.searchPatronByName(Name);
                        break;
                    case 'b':
                        break;
                    case 't':
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            }
        } else if (opt == 'b') { //Book manager mode
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
            }
        }

    }


}