package view.admin;

import data.DataStore;
import data.StudentManager;
import data.UserManager;
import model.Role;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserTab extends JPanel {

    private final DefaultTableModel model;
    private final JTable table;

    public UserTab() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Username", "Full Name", "Role", "ID"}, 0
        );

        table = new JTable(model);

        JButton addBtn = new JButton("Add User");
        JButton deleteBtn = new JButton("Delete User");

        addBtn.addActionListener(e -> chooseRoleAndOpenDialog());
        deleteBtn.addActionListener(e -> deleteSelected());

        JPanel top = new JPanel();
        top.add(addBtn);
        top.add(deleteBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);

        for (User u : UserManager.get()) {
            model.addRow(new Object[]{
                    u.username(),
                    u.fullName(),
                    u.role(),
                    u.id()
            });
        }
    }

    private void chooseRoleAndOpenDialog() {
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());

        int result = JOptionPane.showConfirmDialog(
                this,
                roleBox,
                "Select Role",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return;

        Role role = (Role) roleBox.getSelectedItem();

        switch (role) {
            case ADMIN, INSTRUCTOR -> openBasicUserDialog(role);
            case STUDENT -> openStudentDialog();
        }
    }

    private void openBasicUserDialog(Role role) {
        JTextField username = new JTextField();
        JTextField password = new JTextField();
        JTextField fullName = new JTextField();
        JTextField id = new JTextField();

        Object[] fields = {
                "Username:", username,
                "Password:", password,
                "Full Name:", fullName,
                "ID:", id
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add " + role,
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return;

        if (username.getText().isEmpty() ||
                password.getText().isEmpty() ||
                fullName.getText().isEmpty() ||
                id.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields required");
            return;
        }

        boolean ok = UserManager.add(
                username.getText(),
                password.getText(),
                role,
                fullName.getText(),
                id.getText()
        );

        if (!ok) {
            JOptionPane.showMessageDialog(this, "User already exists");
            return;
        }

        loadData();
    }

    private void openStudentDialog() {
        JTextField username = new JTextField();
        JTextField password = new JTextField();
        JTextField fullName = new JTextField();
        JTextField id = new JTextField();
        JTextField department = new JTextField();
        JTextField year = new JTextField();

        Object[] fields = {
                "Username:", username,
                "Password:", password,
                "Full Name:", fullName,
                "Student ID:", id,
                "Department:", department,
                "Year:", year
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add Student",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return;

        try {
            int y = Integer.parseInt(year.getText());

            if (username.getText().isEmpty() ||
                    password.getText().isEmpty() ||
                    fullName.getText().isEmpty() ||
                    id.getText().isEmpty() ||
                    department.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "All fields required");
                return;
            }

            boolean okUser = UserManager.add(
                    username.getText(),
                    password.getText(),
                    Role.STUDENT,
                    fullName.getText(),
                    id.getText()
            );

            if (!okUser) {
                JOptionPane.showMessageDialog(this, "User already exists");
                return;
            }

            boolean okStudent = StudentManager.add(
                    id.getText(),
                    fullName.getText(),
                    department.getText(),
                    y,
                    username.getText()
            );

            if (!okStudent) {
                UserManager.removeUser(username.getText());
                JOptionPane.showMessageDialog(this, "Student profile failed");
                return;
            }

            loadData();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid year");
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first");
            return;
        }

        String username = model.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete user " + username + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        User user = UserManager.get(username);

        if (user.role() == Role.STUDENT) {
            var student = StudentManager.get(username);
            DataStore.deleteStudent(student);
        } else {
            UserManager.removeUser(username);
        }

        loadData();
    }
}