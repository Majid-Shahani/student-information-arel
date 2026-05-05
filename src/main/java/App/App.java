package App;

import data.*;
import view.*;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private model.User currUser;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public App() {
        setTitle("Student Information System");
        setSize(600,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new Login(this), "Login");
        add(mainPanel);

        show("Login");
    }

    public void show(String panelName) {

        cardLayout.show(mainPanel, panelName);
    }

    public void onLogin(model.User user) {
        currUser = user;
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