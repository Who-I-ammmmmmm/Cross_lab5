package org.example;

import javax.swing.*;
import java.awt.*;

// Class for creating a dialog window to add an employee
class EmployeeDialog extends JDialog {
    private JTextField nameField = new JTextField(20);
    private JComboBox<String> positionComboBox = new JComboBox<>(new String[]{"Employee", "Developer", "Manager"});
    private JTextField hoursWorkedField = new JTextField(10);

    public EmployeeDialog(JFrame owner) {
        super(owner, "Add Employee", true);
        setLayout(new GridLayout(0, 2));

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Position:"));
        add(positionComboBox);
        add(new JLabel("Hours Worked:"));
        add(hoursWorkedField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            // Get values from fields
            String name = nameField.getText();
            String position = (String) positionComboBox.getSelectedItem();
            int hoursWorked = Integer.parseInt(hoursWorkedField.getText());

            // Check that fields are not empty
            if (!name.isEmpty() && !position.isEmpty()) {
                PayrollSystemGUI payrollSystemGUI = (PayrollSystemGUI) owner;
                payrollSystemGUI.addEmployee(new Employee(name, position, hoursWorked));
                dispose(); // Close the dialog window
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        add(addButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(owner);
    }
}
