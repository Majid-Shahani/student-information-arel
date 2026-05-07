package App;

import data.*;
import view.*;
import view.admin.AdminPanel;
import view.instructor.InstructorPanel;
import view.student.StudentPanel;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public App() {
        setTitle("Student Information System");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                DataStore.saveAll();
            }
        });
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "LOGIN");
        add(mainPanel);

        show("LOGIN");
    }

    public void show(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void onLogin(model.User user) {
        switch(user.role()) {
            case ADMIN -> {
                mainPanel.add(new AdminPanel(this, user), "ADMIN");
                show("ADMIN");
            }
            case INSTRUCTOR -> {
                mainPanel.add(new InstructorPanel(user), "INSTRUCTOR");
                show("INSTRUCTOR");
            }
            case STUDENT -> {
                mainPanel.add(new StudentPanel(this, user), "STUDENT");
                show("STUDENT");
            }
        }
    }

    static void main() {
        DataStore.loadAll();

        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}