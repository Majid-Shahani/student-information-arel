package App;

import data.*;
import model.User;
import view.*;
import view.admin.AdminPanel;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private User currUser;

    public App() {
        setTitle("Student Information System");
        setSize(600,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new AdminPanel(this, currUser), "ADMIN");
        mainPanel.add(new LoginPanel(this), "LOGIN");
        add(mainPanel);

        show("LOGIN");
    }

    public void show(String panelName) {

        cardLayout.show(mainPanel, panelName);
    }

    public void onLogin(model.User user) {
        switch(user.role()) {
            case ADMIN -> show("ADMIN");
            case INSTRUCTOR -> show("INSTRUCTOR");
            case STUDENT -> show("STUDENT");
        }
    }

    static void main(String[] args) {
        DataStore.loadAll();

        SwingUtilities.invokeLater(() -> { new App().setVisible(true); });
    }
}