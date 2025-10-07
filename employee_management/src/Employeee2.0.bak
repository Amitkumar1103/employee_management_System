
// import java.io.*;
import java.util.*;
import java.sql.*;

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

    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", salary=" + salary + "]";
    }
}

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
        }
        return conn;
    }
}

class EmployeeManager {
    // private ArrayList<Employee> employees = new ArrayList<>();

    // File Operations
    // public void saveToFile() {
    // try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt")))
    // {
    // for (Employee emp : employees) {
    // bw.write(emp.getId() + "," + emp.getName() + "," + emp.getDepartment() + ","
    // + emp.getSalary());
    // bw.newLine(); // move to next line
    // }
    // System.out.println(" Data saved successfully to employees.txt");
    // } catch (IOException e) {
    // System.out.println("Error saving file: " + e.getMessage());
    // }
    // }

    // public void loadFromFile() {
    // try (BufferedReader br = new BufferedReader(new FileReader("employees.txt")))
    // {
    // String line;
    // while ((line = br.readLine()) != null) {
    // String[] parts = line.split(",");
    // if (parts.length == 4) {
    // int id = Integer.parseInt(parts[0]);
    // String name = parts[1];
    // String department = parts[2];
    // double salary = Double.parseDouble(parts[3]);
    // Employee emp = new Employee(id, name, department, salary);
    // employees.add(emp);
    // }
    // }
    // System.out.println(" Data loaded successfully from employees.txt");
    // } catch (FileNotFoundException e) {
    // System.out.println(" File not found, starting with an empty list.");
    // } catch (IOException e) {
    // System.out.println(" Error loading file: " + e.getMessage());
    // }
    // }

    // Add Employee
    public void addemp(Employee emp) {
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
}

class EmployeeApp {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connected to MySQL successfully!");
        } else {
            System.out.println("Connection failed.");
        }
        Scanner sc = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();
        // manager.loadFromFile();
        int choice = 0;
        while (true) {
            System.out.println("\nEmployee Management System:-");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Update Employee");
            System.out.println("6. Exit");
            try {
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                if (choice < 1 || choice > 6) {
                    System.out.println("Invalid choice, please select between 1-6.");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id;
                    try {
                        id = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println(" Invalid ID, must be an integer.");
                        sc.nextLine();
                        break;
                    }
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double sal;
                    try {
                        sal = sc.nextDouble();
                        if (sal < 0) {
                            System.out.println(" Salary cannot be negative.");
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(" Invalid salary, must be a number.");
                        sc.nextLine();
                        break;
                    }
                    // manager.saveToFile();
                    manager.addemp(new Employee(id, name, dept, sal));
                    break;

                case 2:
                    manager.disEmp();
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    int searchId;
                    try {
                        searchId = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input, please enter a number.");
                        sc.nextLine();
                        continue;
                    }
                    Employee found = manager.searchEmp(searchId);
                    if (found != null)
                        System.out.println(found);
                    else
                        System.out.println("Employee not found!");
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println(" Invalid ID, must be an integer.");
                        sc.nextLine();
                        break;
                    }
                    if (manager.delEmp(deleteId))
                        System.out.println("Employee deleted.");
                    else
                        System.out.println("Employee not found.");
                    break;

                case 5:
                    System.out.print("Enter ID to update: ");
                    int updateId;
                    try {
                        updateId = sc.nextInt();
                        sc.nextLine(); // consume newline
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid ID, must be an integer.");
                        break;
                    }

                    Employee e = manager.searchEmp(updateId);
                    if (e != null) {
                        System.out.print("Enter new name (or press Enter to skip): ");
                        String newName = sc.nextLine();
                        if (!newName.trim().isEmpty()) {
                            e.setName(newName);
                        }

                        System.out.print("Enter new department (or press Enter to skip): ");
                        String newDept = sc.nextLine();
                        if (!newDept.trim().isEmpty()) {
                            e.setDepartment(newDept);
                        }
                        System.out.print("Enter new salary (or 0 to skip): ");
                        String salaryInput = sc.nextLine();
                        double newSalary = e.getSalary();
                        if (!salaryInput.trim().isEmpty()) {
                            try {

                                // Convert the string to a double
                                newSalary = Double.parseDouble(salaryInput);
                                if (newSalary < 0) {
                                    System.out.println("Salary cannot be negative.");
                                    break;
                                }
                                e.setSalary(newSalary);
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid salary, must be a number.");
                                break;
                            }
                        }

                        manager.updateEmp(e.getId(), e.getName(), e.getDepartment(), e.getSalary());
                        System.out.println("Employee updated successfully!");

                        System.out.println(e);
                    } else {
                        System.out.println("Employee not found.");
                    }

                    break;

                case 6:
                    System.out.println("Data saved. Exiting...");
                    // manager.saveToFile();
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
