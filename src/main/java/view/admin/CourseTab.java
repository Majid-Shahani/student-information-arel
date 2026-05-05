package view.admin;

import data.CourseManager;
import data.UserManager;
import model.Course;
import model.Role;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CourseTab extends JPanel {

    private final DefaultTableModel model;
    JTable table;

    public CourseTab() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Code", "Name", "Credit", "Quota", "Instructor"}, 0);

        table = new JTable(model);

        JButton addBtn = new JButton("Add Course");
        addBtn.addActionListener(e -> openAddDialog());

        JButton deleteBtn = new JButton("Delete Course");
        deleteBtn.addActionListener(e -> deleteSelected());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(addBtn, BorderLayout.SOUTH);
        add(deleteBtn, BorderLayout.NORTH);

        loadData();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }

        String code = model.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete course " + code + "? This will remove enrollments and grades.",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            data.DataStore.deleteCourse(code);
            loadData();
        }
    }

    private void loadData() {
        model.setRowCount(0);

        for (Course c : CourseManager.get()) {
            model.addRow(new Object[]{
                    c.courseCode(),
                    c.courseName(),
                    c.credit(),
                    c.quota(),
                    c.instructorUsername()
            });
        }
    }

    private void openAddDialog() {
        JTextField code = new JTextField();
        JTextField name = new JTextField();
        JTextField credit = new JTextField();
        JTextField quota = new JTextField();
        JComboBox<User> instructor = new JComboBox<>();

        for (var u : UserManager.get()) {
            if (u.role() == Role.INSTRUCTOR || u.role() == Role.ADMIN) {
                instructor.addItem(u);
            }
        }
        if (instructor.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No Instructors Available");
            return;
        }
        instructor.setRenderer((list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus)
                -> new JLabel(value.fullName()));

        Object[] fields = {
                "Code:", code,
                "Name:", name,
                "Credit:", credit,
                "Quota:", quota,
                "Instructor Username:", instructor
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add Course",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean ok = CourseManager.add(
                        code.getText(),
                        name.getText(),
                        Integer.parseInt(credit.getText()),
                        Integer.parseInt(quota.getText()),
                        ((User) Objects.requireNonNull(instructor.getSelectedItem())).username()
                );

                if (!ok || instructor.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Failed to add course");
                    return;
                }

                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        }
    }
}