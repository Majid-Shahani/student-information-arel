package view.student;

import App.App;
import model.User;
import view.Refreshable;

import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JPanel {
    public StudentPanel(App app, User user) {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Available Courses", new AvailableCoursesTab(user));
        tabs.addTab("My Courses", new MyCoursesTab(user));
        tabs.addTab("Transcript", new TranscriptTab(user));

        tabs.addChangeListener(e -> {
            Component selected = tabs.getSelectedComponent();
            if (selected instanceof Refreshable ref) ref.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }
}