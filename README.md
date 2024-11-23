# CSC311 Capstone Project: FSC Library Management System

## About This Project
This project is intended as a proof-of-concept for managing a library, with the ability to manage books and patrons. It keeps track of this through a MySQL database.

## Usage
The library management system is designed to enable the management of library operations, such as checkouts, returns, and book management. When the program is opened, it navigates to the login screen, then as the staff, you enter your username and password to log into the library management system. When the staff is logged in, they will have access to many functionalities such as checking out books, returning books, adding books, and managing patron information. The checkout process happens by entering both patron's and book's details. The system will update the database once confirmed. The return process will have the staff enter the patron's details and confirming it by clicking the return button to return the current borrowed book. The add book process is by clicking the add button and filling in the book details, and then once clicked add, it will save it to the database. Patrons could also access their account information and view the books they want to checkout or return. Patrons could also track their current borrowed books by pressing the current rental details. There are also error messages if an error occurs during checkout or return. Once, the staff or patron is done, then they can log out of the program.

##Database Structure
Login Screen: Username and Password, and can log in as staff or patron
Patron Self-Service View: Title, Author, ISBN, Edition, Checkout button, Current Rental Details button, Return button
Patron (Librarian View): ID, Name, Email, Renting?, Book ID, Title, Author, ISBN, Edition, Add Patron button, Edit Patron, Remove Patron, Checkout button, Return button
Books (Librarian View): ID, Title, Author, ISBN, Edition, Quantity, Reconnect To DB button, Add Book button, Edit Book button, Delete Book button

## Technical details
Frontend: JavaFx, SceneBuilder, CSS, Figma
Backend: IntelliJ (Java)
Database: Microsoft SQL Server (hosted on Azure)
Database Connectivity: Microsoft JDBC Driver

## Authors
Andrew Kehoe: Project Leader, Database Admin, and Backend Programmer<br>
  https://github.com/andrewkehoe<br><br>
 Jordan Biggs: Frontend Designer and Backend Programmer<br>
	https://github.com/Jordansggib<br><br>
 Samin Mahmood: Backend Programmer<br>
	https://github.com/MehmedBucket03<br><br>
 Bennett Thomas: Frontend Design<br>
	https://github.com/Ben2474 <br><br>
 Zuxin Chen: Backend Programmer <br>
  https://github.com/ZuxinChen<br><br>
