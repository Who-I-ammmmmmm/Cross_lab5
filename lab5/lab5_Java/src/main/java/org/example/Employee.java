package org.example;

// Class representing an employee position
class Employee {
    String name;
    String position;
    int hoursWorked;

    // Hourly rate for all employees
    double hourlyRate;

    public Employee(String name, String position, int hoursWorked) {
        this.name = name;
        this.position = position;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = calculateHourlyRate();
    }

    // Method to calculate hourly rate
    double calculateHourlyRate() {
        switch (position) {
            case "Developer":
                return 45.0;
            case "Manager":
                return 25.0;
            default:
                return 35.0;
        }
    }

    // Method to calculate salary
    double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
