-----

# 👨‍💼 Employee Management System

This is a **Java-based Employee Management System** that provides a command-line interface for performing CRUD (Create, Read, Update, Delete) operations on employee data. The application connects to a MySQL database to store and manage employee records.

-----

### ✨ Features

  * **Add Employee**: Add a new employee with their ID, name, department, and salary.
  * **Display All Employees**: View a list of all employees stored in the database.
  * **Search Employee**: Find an employee's details by their ID.
  * **Delete Employee**: Remove an employee's record from the database using their ID.
  * **Update Employee**: Modify an existing employee's name, department, and salary.
  * **Database Integration**: Utilizes MySQL to ensure data persistence.

-----

### 📋 Prerequisites

Before you can run this application, you need to have the following installed on your system:

  * **Java Development Kit (JDK)**: Version 8 or higher.
  * **MySQL Server**: The application connects to a MySQL database.
  * **MySQL Connector/J JAR file**: This JDBC driver is required to connect Java to MySQL. You can download it from the official MySQL website.

-----

### 🚀 Setup and Usage

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/Amitkumar1103/employee_management_System.git
    ```
2.  **Navigate to the project directory**:
    ```bash
    cd employee_management_System
    ```
3.  **Set up the database**:
      * Create a new database in MySQL. The code references a database named `ems_db`.
      * The `DBConnection.java` class contains the database connection details. You may need to update the `URL`, `USER`, and `PASSWORD` to match your local MySQL configuration.
      * The application will create the `employees` table automatically on the first run.
4.  **Add the MySQL Connector JAR file**:
      * Place the downloaded `mysql-connector-j-9.4.0.jar` file in the `lib` directory of your project.
      * Make sure this JAR is included in your project's build path in your IDE (e.g., in VS Code, you can do this by dragging the file to the `lib` folder in the file explorer and adding it to the classpath).
5.  **Run the application**:
      * Compile and run the `Main.java` file.
      * The application will display a menu of options, and you can interact with it through the terminal.
