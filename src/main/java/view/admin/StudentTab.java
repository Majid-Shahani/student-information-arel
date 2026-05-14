package view.admin;

import data.StudentManager;
import model.StudentProfile;
import view.Refreshable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentTab extends JPanel implements Refreshable {
    private final DefaultTableModel model;

    public StudentTab() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(
                new String[]{
                        "Student ID",
                        "Full Name",
                        "Department",
                        "Year",
                        "Username"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh();
    }

    @Override
    public void refresh() {
        model.setRowCount(0);

        for (StudentProfile s : StudentManager.get()) {
            model.addRow(new Object[]{
                    s.studentID(),
                    s.fullName(),
                    s.department(),
                    s.year(),
                    s.username()
            });
        }
    }
}