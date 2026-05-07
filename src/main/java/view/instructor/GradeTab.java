package view.instructor;

import data.EnrollmentManager;
import data.GradeManager;
import data.StudentManager;
import data.CourseManager;

import model.Course;
import model.GradeRecord;
import model.StudentProfile;
import model.User;
import org.jetbrains.annotations.NotNull;
import view.Refreshable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GradeTab extends JPanel implements Refreshable {
    private final JComboBox<Course> courseBox;

    private final DefaultTableModel model;
    private final JTable table;

    public GradeTab(@NotNull User instructor) {
        setLayout(new BorderLayout());

        courseBox = new JComboBox<>();
        for (Course c : CourseManager.getByInstructor(instructor.username())) {
            courseBox.addItem(c);
        }
        courseBox.setRenderer((_,
                               value,
                               _,
                               _,
                               _)
                -> new JLabel(value.courseCode() + " - " + value.courseName()));
        courseBox.addActionListener(_ -> refresh());

        model = new DefaultTableModel(
                new String[]{
                        "Username",
                        "Full Name",
                        "Midterm",
                        "Final",
                        "Average",
                        "Letter"
                }, 0
        );

        table = new JTable(model);
        JButton gradeBtn = new JButton("Enter / Update Grade");
        gradeBtn.addActionListener(_ -> openGradeDialog());

        add(courseBox, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(gradeBtn, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        Course selectedCourse = (Course) courseBox.getSelectedItem();
        if (selectedCourse == null) return;

        for (String username :EnrollmentManager.getStudentsIn(selectedCourse.courseCode())) {
            StudentProfile student = StudentManager.get(username);
            if (student == null) continue;

            GradeRecord grade = GradeManager.get(username, selectedCourse.courseCode());
            if (grade == null) {
                model.addRow(new Object[]{
                        username,
                        student.fullName(),
                        "-",
                        "-",
                        "-",
                        "-"
                });
            } else {
                model.addRow(new Object[]{
                        username,
                        student.fullName(),
                        grade.midterm(),
                        grade.finalExam(),
                        grade.calculateAverage(),
                        grade.getLetterGrade()
                });
            }
        }
    }

    private void openGradeDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a student first");
            return;
        }

        Course selectedCourse = (Course) courseBox.getSelectedItem();
        if (selectedCourse == null) return;

        String username = model.getValueAt(row, 0).toString();

        JTextField midtermField = new JTextField();
        JTextField finalField = new JTextField();

        GradeRecord existing = GradeManager.get(username, selectedCourse.courseCode());

        if (existing != null) {
            midtermField.setText(String.valueOf(existing.midterm()));
            finalField.setText(String.valueOf(existing.finalExam()));
        }

        Object[] fields = {
                "Midterm:", midtermField,
                "Final:", finalField
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Enter Grades",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (result != JOptionPane.OK_OPTION) return;

        try {
            double midterm = Double.parseDouble(midtermField.getText());
            double finals =Double.parseDouble(finalField.getText());

            if (midterm < 0 || midterm > 100 || finals < 0 || finals > 100) {
                JOptionPane.showMessageDialog(this,"Grades must be between 0 and 100");
                return;
            }

            if (existing == null) {
                GradeManager.add(
                        username,
                        selectedCourse.courseCode(),
                        midterm,
                        finals
                );
            } else {
                GradeManager.updateGrade(
                        username,
                        selectedCourse.courseCode(),
                        midterm,
                        finals
                );
            }

            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Invalid grade input");
        }
    }
}