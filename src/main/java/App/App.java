package App;

import data.DataStore;
import model.User;
import view.LoginPanel;
import view.admin.AdminPanel;
import view.instructor.InstructorPanel;
import view.student.StudentPanel;

import org.jetbrains.annotations.NotNull;
import javax.swing.*;

public class App extends JFrame {
    public App() {
        setTitle("Student Information System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                DataStore.saveAll();
            }
        });

        setLocationRelativeTo(null);
        setContentPane(new LoginPanel(this));
    }

    public void onLogin(@NotNull User user) {
        switch (user.role()) {
            case ADMIN -> setContentPane(new AdminPanel());
            case INSTRUCTOR -> setContentPane(new InstructorPanel(user));
            case STUDENT -> setContentPane(new StudentPanel(user));
        }
        revalidate();
        repaint();
    }

    static void main() {
        DataStore.loadAll();

        SwingUtilities.invokeLater(() ->
                new App().setVisible(true)
        );
    }
}