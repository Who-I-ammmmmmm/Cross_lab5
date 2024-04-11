package org.example;

import java.io.*;
import java.util.ArrayList;

// Class representing the payroll system
public class PayrollSystem {
    private ArrayList<Employee> employees = new ArrayList<>();

    // Method to add a new employee
    void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Method to remove an employee
    void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    // Method to get all employees
    ArrayList<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    // Method to generate a payroll report
    ArrayList<String> generatePayroll() {
        ArrayList<String> payrollList = new ArrayList<>();
        for (Employee employee : employees) {
            double salary = employee.calculateSalary();
            String info = employee.name + " - " + employee.position + " | " + employee.hoursWorked + " hours | " + employee.hourlyRate + "/hour | " + salary;
            payrollList.add(info);
        }
        return payrollList;
    }

    // Method to save data to a file
    void saveToFile(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (Employee employee : employees) {
                writer.write(employee.name + "," + employee.position + "," + employee.hoursWorked + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    void loadFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String position = parts[1];
                int hoursWorked = Integer.parseInt(parts[2]);
                switch (position) {
                    case "Developer":
                        addEmployee(new Developer(name, hoursWorked));
                        break;
                    case "Manager":
                        addEmployee(new Manager(name, hoursWorked));
                        break;
                    default:
                        addEmployee(new Employee(name, position, hoursWorked));
                        break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }
}
