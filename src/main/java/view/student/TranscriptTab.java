package view.student;

import data.CourseManager;
import data.GradeManager;

import model.Course;
import model.GradeRecord;
import model.User;
import view.Refreshable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TranscriptTab extends JPanel implements Refreshable {
    private final User student;
    private final DefaultTableModel model;
    private final JLabel gpaLabel;

    public TranscriptTab(User student) {
        this.student = student;
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{
                        "Course",
                        "Midterm",
                        "Final",
                        "Average",
                        "Letter"
                }, 0
        );

        JTable table = new JTable(model);
        gpaLabel = new JLabel();

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(gpaLabel, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);

        for (GradeRecord g : GradeManager.get(student.username())) {
            Course c = CourseManager.get(g.courseCode());
            String courseName = (c == null) ? g.courseCode() : c.courseName();

            model.addRow(new Object[]{
                    courseName,
                    g.midterm(),
                    g.finalExam(),
                    g.calculateAverage(),
                    g.getLetterGrade()
            });
        }
        gpaLabel.setText(
                "GPA: " + String.format("%.2f", GradeManager.gpa(student.username()))
        );
    }
}