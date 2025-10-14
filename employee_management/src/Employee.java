
// import java.io.*;
import java.util.*;
import java.sql.*;

// Database Connection Class
class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ems_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.exit(1);
        }
        return conn;
    }
}

// login & sign
class User {
    private int id;
    private String username;
    private String password;
    private String role;

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

// Employee Class
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", Salary: " + salary;
    }

}

class EmployeeManager {

    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"));
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public User signup(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return null;
        }
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("Signup successful! You can now login.");
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry"))
                System.out.println("Username already exists!");
            else
                System.out.println("Signup error: " + e.getMessage());
        }
        return null;
    }

    // Add Employee
    public void addemp(Employee emp) {
        if (emp.getId() <= 0 || emp.getName() == null || emp.getName().trim().isEmpty() ||
                emp.getDepartment() == null || emp.getDepartment().trim().isEmpty()) {
            System.out.println("Employee ID, name, and department cannot be empty.");
            return;
        }
        String query = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, emp.getId());
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getDepartment());
            ps.setDouble(4, emp.getSalary());
            ps.executeUpdate();
            System.out.println("Employee added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    // Display All Employees
    public void disEmp() {
        String query = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Department: " + rs.getString("department") +
                        ", Salary: " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error displaying employees: " + e.getMessage());
        }
    }

    // Search Employee by ID
    public Employee searchEmp(int id) {
        String query = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching employee: " + e.getMessage());
        }
        return null;
    }

    // Delete Employee
    public boolean delEmp(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }

    public void updateEmp(int id, String name, String department, double newSalary) {
        String query = "UPDATE employees SET name=?, department=?, salary=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            if (name == null || name.trim().isEmpty() ||
                    department == null || department.trim().isEmpty()) {
                System.out.println("Name and department cannot be empty.");
                return;
            }
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, newSalary);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Employee updated successfully.");
            else
                System.out.println("Employee not found.");
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    public void makeAdminById(int userId) {
        String query = "UPDATE users SET role='admin' WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("User with ID " + userId + " promoted to admin!");
            else
                System.out.println("User ID not found.");

        } catch (SQLException e) {
            System.out.println("Error promoting user: " + e.getMessage());
        }
    }

    public void viewAllUsers() {
        String query = "SELECT id, username, password, role FROM users";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n---- Registered Users ----");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Username: " + rs.getString("username") +
                        ", Password: " + rs.getString("password") +
                        ", Role: " + rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }
}

class EmployeeApp {

    public static int getIntInput(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number.");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static String getString(Scanner sc, String prompt) {
        String string;
        while (true) {
            System.out.print(prompt);
            try {
                string = sc.nextLine();
                return string;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid string.");
                sc.nextLine();
            }
            // } catch (NullPointerException e) {
            // System.out.println("Input cannot be null, please enter a valid string.");
            // sc.nextLine();
            // }
        }
    }

    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connected to MySQL successfully!");
        } else {
            System.out.println("Connection failed.");
        }
        Scanner sc = new Scanner(System.in);
        EmployeeManager empManager = new EmployeeManager();
        User currentUser = null;
        while (true) {
            System.out.println("\n===== Welcome to EMS =====");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
            int choice = getIntInput(sc, "Enter choice: ");
            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice, please select between 1-3.");
                continue;
            }

            switch (choice) {
                case 1:
                    String username = getString(sc, "Enter username: ");
                    String password = getString(sc, "Enter password: ");
                    currentUser = empManager.login(username, password);
                    if (currentUser != null) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Login failed, try again.");
                        continue;
                    }
                    break;
                case 2:
                    String username1 = getString(sc, "Enter username: ");
                    String password1 = getString(sc, "Enter password: ");
                    currentUser = empManager.signup(username1, password1);
                    if (currentUser != null) {
                        System.out.println("Signup successful!. You can now login with your credentials");
                    } else {
                        System.out.println("Signup failed, try again.");
                        continue;
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
            System.out.println("\nLogged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");

            if (currentUser.getRole().equalsIgnoreCase("admin")) {
                adminMenu(sc, empManager);
            } else {
                userMenu(sc, empManager);
            }
        }
    }

    public static void adminMenu(Scanner sc, EmployeeManager empManager) {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. View All Users");
            System.out.println("7. Make User Admin (by ID)");
            System.out.println("8. Logout");
            int choice = getIntInput(sc, "Enter choice: ");

            switch (choice) {
                case 1:
                    int id = getIntInput(sc, "Enter ID: ");
                    String name = getString(sc, "Enter Name: ");
                    String dept = getString(sc, "Enter Department: ");
                    double sal = getIntInput(sc, "Enter Salary: ");

                    Employee emp = new Employee(id, name, dept, sal);
                    empManager.addemp(emp);
                    break;

                case 2:
                    empManager.disEmp();
                    break;

                case 3:

                    int searchId = getIntInput(sc, "Enter ID to search: ");
                    Employee found = empManager.searchEmp(searchId);
                    if (found != null) {
                        System.out.println("Employee Found: " + found);
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 4:
                    int updateId = getIntInput(sc, "Enter ID to update: ");
                    String newName = getString(sc, "Enter new name: ");
                    String newDept = getString(sc, "Enter new department: ");
                    double newSalary = getIntInput(sc, "Enter new salary: ");
                    empManager.updateEmp(updateId, newName, newDept, newSalary);
                    break;

                case 5:
                    int deleteId = getIntInput(sc, "Enter ID to delete: ");
                    boolean deleted = empManager.delEmp(deleteId);
                    if (deleted)
                        System.out.println("Employee deleted successfully.");
                    else
                        System.out.println("Employee not found or error occurred.");
                    break;

                case 6:
                    empManager.viewAllUsers();
                    break;

                case 7:
                    empManager.viewAllUsers();
                    int promoteId = getIntInput(sc, "Enter user ID to promote: ");
                    empManager.makeAdminById(promoteId);
                    break;

                case 8:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice, please select between 1-8.");
            }
        }
    }

    public static void userMenu(Scanner sc, EmployeeManager empManager) {
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. View Employees");
            System.out.println("2. Search Employee");
            System.out.println("3. Logout");
            int choice = getIntInput(sc, "Enter choice (1-3): ");

            switch (choice) {
                case 1:
                    String query = "SELECT id, name, department FROM employees";
                    try (Connection conn = DBConnection.getConnection();
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery(query)) {

                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id") +
                                    ", Name: " + rs.getString("name") +
                                    ", Department: " + rs.getString("department"));
                        }
                    } catch (SQLException e) {
                        System.out.println("Error displaying employees: " + e.getMessage());
                    }
                    break;

                case 2:
                    int id = getIntInput(sc, "Enter ID to search: ");
                    Employee e = empManager.searchEmp(id);
                    if (e != null) {
                        System.out.println("ID: " + e.getId() +
                                ", Name: " + e.getName() +
                                ", Department: " + e.getDepartment());
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 3:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

}
