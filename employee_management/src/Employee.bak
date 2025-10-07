import java.io.*;
import java.util.*;

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

class EmployeeManager {
    private ArrayList<Employee> employees = new ArrayList<>();

    // file store
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt"))) {
            for (Employee emp : employees) {
                bw.write(emp.getId() + "," + emp.getName() + "," + emp.getDepartment() + "," + emp.getSalary());
                bw.newLine(); // move to next line
            }
            System.out.println("💾 Data saved successfully to employees.txt");
        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String department = parts[2];
                    double salary = Double.parseDouble(parts[3]);
                    Employee emp = new Employee(id, name, department, salary);
                    employees.add(emp);
                }
            }
            System.out.println("💾 Data loaded successfully from employees.txt");
        } catch (FileNotFoundException e) {
            System.out.println("⚠ File not found, starting with an empty list.");
        } catch (IOException e) {
            System.out.println("❌ Error loading file: " + e.getMessage());
        }
    }

    // Add Employee
    public void addEmployee(Employee emp) {

        if (searchEmployee(emp.getId()) != null) {
            System.out.println("❌ Employee with ID " + emp.getId() + " already exists!");
            return;
        }
        if (emp.getSalary() < 0) {
            System.out.println("❌ Salary cannot be negative!");
            return;
        }
        if (emp.getName().trim().isEmpty() || emp.getDepartment().trim().isEmpty()) {
            System.out.println("❌ Name and Department cannot be empty!");
            return;
        }
        employees.add(emp);
        System.out.println("✅ Employee added successfully!");
    }

    // Display All Employees
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("⚠ No employees found.");
            return;
        }
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    // Search Employee by ID
    public Employee searchEmployee(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null; // Not found
    }

    // Delete Employee
    public boolean deleteEmployee(int id) {
        Employee emp = searchEmployee(id);
        if (emp != null) {
            employees.remove(emp);
            return true;
        }
        return false;
    }

    public Employee updateEmployee(int id, String name, String department, double salary) {
        Employee emp = searchEmployee(id);
        if (emp != null) {
            emp.setName(name);
            emp.setDepartment(department);
            emp.setSalary(salary);
            return emp;
        }
        return null;

    }
}

class EmployeeApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();
        manager.loadFromFile();
        int choice = 0;
        while (true) {
            System.out.println("\n===== Employee Management System =====");
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
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double sal = sc.nextDouble();

                    Employee emp = new Employee(id, name, dept, sal);
                    manager.addEmployee(emp);
                    manager.saveToFile();
                    break;

                case 2:
                    manager.displayEmployees();
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();
                    Employee found = manager.searchEmployee(searchId);
                    if (found != null)
                        System.out.println(found);
                    else
                        System.out.println("❌ Employee not found!");
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    int deleteId = sc.nextInt();
                    if (manager.deleteEmployee(deleteId))
                        System.out.println("✅ Employee deleted.");
                    else
                        System.out.println("❌ Employee not found.");
                    break;

                case 5:
                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine(); // consume newline

                    Employee e = manager.searchEmployee(updateId);
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
                        String newSalary = sc.nextLine();
                        if (!newSalary.isEmpty() && Double.parseDouble(newSalary) != 0) {
                            e.setSalary(Double.parseDouble(newSalary));
                        }
                        System.out.println(" Employee updated successfully!");
                        System.out.println(e);
                    } else {
                        System.out.println(" Employee not found.");
                    }
                    break;

                case 6:
                    manager.saveToFile();
                    System.out.println("💾 Data saved. Exiting...");

                    sc.close();
                    return;

                default:
                    System.out.println(" Invalid choice, try again.");
            }
        }
    }
}
