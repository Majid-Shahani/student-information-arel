package view.admin;

import App.App;
import model.User;
import view.Refreshable;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private final App app;
    private final User user;

    private final JTabbedPane tabs;

    public AdminPanel(App app, User user) {
        this.app = app;
        this.user = user;

        setLayout(new BorderLayout());

        tabs = new JTabbedPane();

        tabs.addTab("Courses", new CourseTab());
        tabs.addTab("Users", new UserTab());
        tabs.addChangeListener(e -> {
            Component selected = tabs.getSelectedComponent();
            if (selected instanceof Refreshable ref) ref.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }
}