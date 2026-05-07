package view.admin;

import view.Refreshable;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    private final JTabbedPane tabs;

    public AdminPanel() {
        setLayout(new BorderLayout());

        tabs = new JTabbedPane();

        tabs.addTab("Courses", new CourseTab());
        tabs.addTab("Users", new UserTab());
        tabs.addTab("Students", new StudentTab());

        tabs.addChangeListener(_ -> {
            Component selected = tabs.getSelectedComponent();
            if (selected instanceof Refreshable ref) ref.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }
}