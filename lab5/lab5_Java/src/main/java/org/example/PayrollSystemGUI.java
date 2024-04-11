package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Main class for launching the application with a graphical user interface
public class PayrollSystemGUI extends JFrame {
    private PayrollSystem payrollSystem = new PayrollSystem();
    private DefaultListModel<String> employeeListModel = new DefaultListModel<>();
    private JList<String> employeeList = new JList<>(employeeListModel);

    public PayrollSystemGUI() {
        setTitle("Payroll System");
        createGUI();
        loadEmployeesFromFile("employees.txt");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(e -> {
            EmployeeDialog dialog = new EmployeeDialog(this);
            dialog.setVisible(true);
            payrollSystem.saveToFile("employees.txt"); // Сохранение данных в файл после добавления сотрудника
        });

        JButton removeEmployeeButton = new JButton("Remove Employee");
        removeEmployeeButton.addActionListener(e -> {
            int selectedIndex = employeeList.getSelectedIndex();
            if (selectedIndex != -1) {
                Employee employee = payrollSystem.getEmployees().get(selectedIndex);
                if (employee != null) {
                    payrollSystem.removeEmployee(employee);
                    updateEmployeeList();
                    payrollSystem.saveToFile("employees.txt");
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton payrollButton = new JButton("Generate Payroll");
        payrollButton.addActionListener(e -> {
            ArrayList<String> payroll = payrollSystem.generatePayroll();
            String payrollString = String.join("\n", payroll);
            JOptionPane.showMessageDialog(this, "ВІДОМОСТІ НА ОПЛАТУ ПРАЦІВНИКІВ ЗА БЕРЕЗЕНЬ 2024\n\nNAME - Position |Work Hours|Hours Rate|Summary\n" + payrollString, "ВІДОМОСТІ НА ВИПЛАТУ", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(addEmployeeButton);
        controlPanel.add(removeEmployeeButton);
        controlPanel.add(payrollButton);

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(employeeList), BorderLayout.CENTER);
    }

    void addEmployee(Employee employee) {
        payrollSystem.addEmployee(employee);
        updateEmployeeList();
    }
    void updateEmployeeList() {
        employeeListModel.clear();
        for (Employee employee : payrollSystem.getEmployees()) {
            employeeListModel.addElement(employee.name + " - " + employee.position);
        }
    }

    private void loadEmployeesFromFile(String filename) {
        payrollSystem.loadFromFile(filename);
        updateEmployeeList();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayrollSystemGUI::new);
    }
}
