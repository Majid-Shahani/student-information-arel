package view;

import App.App;
import data.UserManager;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {

    private final JFrame parent;

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPanel(JFrame parent) {
        this.parent = parent;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);

        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(userLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        User user = UserManager.authenticate(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            return;
        }

        usernameField.setText("");
        passwordField.setText("");
        ((App) parent).onLogin(user);
    }
}