package view.instructor;

import data.CourseManager;
import model.Course;
import model.User;
import view.Refreshable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CoursesTab extends JPanel implements Refreshable {

    private final User instructor;

    private final DefaultTableModel model;

    public CoursesTab(User instructor) {
        this.instructor = instructor;
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Code", "Name", "Credit", "Quota"}, 0
        );
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        for (Course c : CourseManager.getByInstructor(instructor.username())) {
            model.addRow(new Object[]{
                    c.courseCode(),
                    c.courseName(),
                    c.credit(),
                    c.quota()
            });
        }
    }
}