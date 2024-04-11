import javax.swing.*
import java.awt.*

// Class for creating a dialog window to add an employee
class EmployeeDialog(owner: JFrame) : JDialog(owner, "Add Employee", true) {
    private val nameField = JTextField(20)
    private val positionComboBox = JComboBox(arrayOf("Employee", "Developer", "Manager"))
    private val hoursWorkedField = JTextField(10)

    init {
        layout = GridLayout(0, 2)

        add(JLabel("Name:"))
        add(nameField)
        add(JLabel("Position:"))
        add(positionComboBox)
        add(JLabel("Hours Worked:"))
        add(hoursWorkedField)

        val addButton = JButton("Add")
        addButton.addActionListener {
            val name = nameField.text
            val position = positionComboBox.selectedItem.toString()
            val hoursWorked = hoursWorkedField.text.toIntOrNull() ?: 0
            if (name.isNotBlank() && position.isNotBlank()) {
                val payrollSystemGUI = owner as PayrollSystemGUI
                when (position) {
                    "Developer" -> payrollSystemGUI.payrollSystem.addEmployee(Developer(name, hoursWorked))
                    "Manager" -> payrollSystemGUI.payrollSystem.addEmployee(Manager(name, hoursWorked))
                    else -> payrollSystemGUI.payrollSystem.addEmployee(Employee(name, position, hoursWorked))
                }
                payrollSystemGUI.updateEmployeeList()
                payrollSystemGUI.payrollSystem.saveToFile("employees.txt")
                dispose()
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }

        val cancelButton = JButton("Cancel")
        cancelButton.addActionListener {
            dispose()
        }

        add(addButton)
        add(cancelButton)

        pack()
        setLocationRelativeTo(owner)
    }
}

// Main class to run the application with a graphical user interface
class PayrollSystemGUI : JFrame("Payroll System") {
    val payrollSystem = PayrollSystem()
    val employeeListModel = DefaultListModel<String>()
    val employeeList = JList<String>(employeeListModel)

    init {
        createGUI()
        loadEmployeesFromFile("employees.txt")
        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

    private fun createGUI() {
        layout = BorderLayout()

        val addEmployeeButton = JButton("Add Employee")
        addEmployeeButton.addActionListener {
            val dialog = EmployeeDialog(this)
            dialog.isVisible = true
        }

        val removeEmployeeButton = JButton("Remove Employee")
        removeEmployeeButton.addActionListener {
            val selectedIndex = employeeList.selectedIndex
            if (selectedIndex != -1) {
                val employee = payrollSystem.getEmployees()[selectedIndex]
                if (employee != null) {
                    payrollSystem.removeEmployee(employee)
                    updateEmployeeList()
                    payrollSystem.saveToFile("employees.txt")
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE)
                }
            }
        }

        val payrollButton = JButton("Generate Payroll")
        payrollButton.addActionListener {
            val payroll = payrollSystem.generatePayroll()
            val payrollString = payroll.joinToString("\n")
            JOptionPane.showMessageDialog(this, "PAYROLL REPORT FOR MARCH 2024\n\nNAME - Position  |Work Hours|Hourly Rate|Salary\n$payrollString", "PAYROLL REPORT", JOptionPane.INFORMATION_MESSAGE)
        }

        val controlPanel = JPanel()
        controlPanel.add(addEmployeeButton)
        controlPanel.add(removeEmployeeButton)
        controlPanel.add(payrollButton)

        add(controlPanel, BorderLayout.NORTH)
        add(JScrollPane(employeeList), BorderLayout.CENTER)
    }

    fun updateEmployeeList() {
        employeeListModel.clear()
        for (employee in payrollSystem.getEmployees()) {
            employeeListModel.addElement("${employee.name} - ${employee.position}")
        }
    }

    private fun loadEmployeesFromFile(filename: String) {
        payrollSystem.loadFromFile(filename)
        updateEmployeeList()
    }
}
