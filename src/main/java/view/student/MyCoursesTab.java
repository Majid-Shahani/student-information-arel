package view.student;

import data.CourseManager;
import data.EnrollmentManager;

import model.Course;
import model.User;
import view.Refreshable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyCoursesTab extends JPanel implements Refreshable {
    private final User student;
    private final DefaultTableModel model;

    public MyCoursesTab(User student) {
        this.student = student;
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{
                        "Code",
                        "Name",
                        "Credit",
                        "Instructor"
                }, 0
        );

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        for (String code : EnrollmentManager.getCoursesBy(student.username())) {
            Course c = CourseManager.get(code);
            if (c == null) continue; // ERROR
            model.addRow(new Object[]{
                    c.courseCode(),
                    c.courseName(),
                    c.credit(),
                    c.instructorUsername()
            });
        }
    }
}