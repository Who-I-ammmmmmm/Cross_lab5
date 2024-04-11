import java.io.File

// Class representing an employee position
open class Employee(
    val name: String,
    val position: String,
    val hoursWorked: Int
) {
    // Hourly rate for all employees
    val hourlyRate = when (position) {
        "Developer" -> 45.0
        "Manager" -> 25.0
        else -> 35.0
    }

    // Method to calculate salary
    open fun calculateSalary(): Double {
        return hoursWorked * hourlyRate
    }
}

// Class representing the position of a developer
class Developer(
    name: String,
    hoursWorked: Int
) : Employee(name, "Developer", hoursWorked) {
    // Overriding the method to calculate salary for a developer
    override fun calculateSalary(): Double {
        return hoursWorked * hourlyRate
    }
}

// Class representing the position of a manager
class Manager(
    name: String,
    hoursWorked: Int
) : Employee(name, "Manager", hoursWorked) {
    // Overriding the method to calculate salary for a manager
    override fun calculateSalary(): Double {
        return hoursWorked * hourlyRate
    }
}

// Class representing the payroll system
class PayrollSystem {
    private val employees = mutableListOf<Employee>()

    // Method to add a new employee
    fun addEmployee(employee: Employee) {
        employees.add(employee)
    }

    // Method to remove an employee
    fun removeEmployee(employee: Employee) {
        employees.remove(employee)
    }

    // Method to get all employees
    fun getEmployees(): List<Employee> {
        return employees.toList()
    }

    // Method to generate a payroll report
    fun generatePayroll(): List<String> {
        val payrollList = mutableListOf<String>()
        for (employee in employees) {
            val salary = employee.calculateSalary()
            val info = "${employee.name} - ${employee.position} | ${employee.hoursWorked} hours  | ${employee.hourlyRate}/hour  | $salary$"
            payrollList.add(info)
        }
        return payrollList
    }

    // Method to save data to a file
    fun saveToFile(filename: String) {
        val file = File(filename)
        file.bufferedWriter().use { out ->
            for (employee in employees) {
                out.write("${employee.name},${employee.position},${employee.hoursWorked}\n")
            }
        }
    }

    // Method to load data from a file
    fun loadFromFile(filename: String) {
        val file = File(filename)
        if (file.exists()) {
            try {
                file.bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        val (name, position, hoursWorked) = line.split(",")
                        when (position) {
                            "Developer" -> addEmployee(Developer(name, hoursWorked.toInt()))
                            "Manager" -> addEmployee(Manager(name, hoursWorked.toInt()))
                            else -> addEmployee(Employee(name, position, hoursWorked.toInt()))
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error loading data from file: ${e.message}")
            }
        }
    }
}
