package view.student;

import data.CourseManager;
import data.EnrollmentManager;

import model.Course;
import model.User;
import view.Refreshable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AvailableCoursesTab extends JPanel implements Refreshable {
    private final User student;
    private final DefaultTableModel model;
    private final JTable table;

    public AvailableCoursesTab(User student) {
        this.student = student;
        setLayout(new BorderLayout());
        model = new DefaultTableModel(
                new String[]{
                        "Code",
                        "Name",
                        "Credit",
                        "Quota",
                        "Instructor"
                }, 0
        );

        table = new JTable(model);

        JButton enrollBtn = new JButton("Enroll");
        enrollBtn.addActionListener(_ -> enrollSelected());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(enrollBtn, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        for (Course c : CourseManager.get()) {
            if (EnrollmentManager.isEnrolled( student.username(), c.courseCode())) continue;

            model.addRow(new Object[]{
                    c.courseCode(),
                    c.courseName(),
                    c.credit(),
                    EnrollmentManager.studentCount(c.courseCode())
                            + "/" + c.quota(),
                    c.instructorUsername()
            });
        }
    }

    private void enrollSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }
        String code = model.getValueAt(row, 0).toString();

        boolean ok = EnrollmentManager.add(student.username(), code);
        if (!ok) {
            JOptionPane.showMessageDialog(this,"Enrollment failed");
            return;
        }

        refresh();
    }
}