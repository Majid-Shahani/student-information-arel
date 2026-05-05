package view.admin;

import App.App;
import model.User;

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
        //tabs.addTab("Students", new StudentTab());
        //tabs.addTab("Users", new UserTab());

        add(tabs, BorderLayout.CENTER);
    }
}