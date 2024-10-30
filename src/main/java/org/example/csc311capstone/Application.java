package org.example.csc311capstone;
//test edit for GH
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.csc311capstone.db.ConnDbOps;

import java.io.IOException;
import java.util.Scanner;

public class Application extends javafx.application.Application {

    public static ConnDbOps cdbop;

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
            while (opt != 'q') {
                System.out.println("Select an option:");
                    System.out.println("a: Add a new patron");
                    System.out.println("d: Display all patrons");
                    System.out.println("e: Edit a patron's information");
                    System.out.println("r: Remove a patron");
                    System.out.println("q: Query patron by name");
                    System.out.println("q: Quit application");
                opt = scnr.next().charAt(0);
            }
        } else if (opt == 'b') { //Book manager mode
            while (opt != 'q') {
                System.out.println("Select an option:");
                System.out.println("a: Add a new book");
                System.out.println("d: Display all books");
                System.out.println("e: Edit a book's information");
                System.out.println("r: Remove a book");
                System.out.println("q: Query book by name");
                System.out.println("q: Quit application");
                opt = scnr.next().charAt(0);
                //ToDo: Book options go here. There should probably be different stuff like ISBN, # of copies...
                //Likewise, patron should have a "currently borrowed" field with a book ID.
                //Oh, and have the books table made in the connectToDatabase method too.
            }
        }

    }


}