package view.instructor;

import model.User;
import view.Refreshable;

import javax.swing.*;
import java.awt.*;

public class InstructorPanel extends JPanel {
    public InstructorPanel(User user) {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("My Courses", new CoursesTab(user));
        tabs.addTab("Grades", new GradeTab(user));
        tabs.addChangeListener(_ -> {
            Component selected = tabs.getSelectedComponent();
            if (selected instanceof Refreshable ref) ref.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }
}