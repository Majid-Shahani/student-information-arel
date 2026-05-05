package data;

import model.GradeRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;

public class GradeManager {
    private GradeManager() {}
    private static final DataManager<GradeRecord> s_Grades = new DataManager<>(
            GradeRecord::fromLine,
            GradeRecord::toFileString
    );

    public static void save(Path path) { s_Grades.save(path); }
    public static void load(Path path) { s_Grades.load(path); }

    public static boolean add(String studentUsername, String courseCode, double midterm, double finalExam) {
        return s_Grades.add(new GradeRecord(studentUsername, courseCode, midterm, finalExam));
    }
    public static boolean removeCourse(String courseCode) {
        return s_Grades.get().removeIf(g -> g.courseCode().equals(courseCode));
    }
    public static boolean removeStudent(String studentUser) {
        return s_Grades.get().removeIf(g -> g.studentUsername().equals(studentUser));
    }

    public static @Nullable GradeRecord get(String studentUser, String courseCode) {
        for (var g : s_Grades.get()) if (g.studentUsername().equals(studentUser) && g.courseCode().equals(courseCode))
            return g;
        return null;
    }
    public static @NotNull ArrayList<GradeRecord> get(String studentUser) {
        ArrayList<GradeRecord> grades = new ArrayList<>();
        for (var g : s_Grades.get()) if (g.studentUsername().equals(studentUser)) grades.add(g);
        return grades;
    }

    public static boolean updateGrade(String sUser, String cc, double midterms, double finals) {
        var it = s_Grades.get().listIterator();
        while (it.hasNext()) {
            var g = it.next();
            if (g.studentUsername().equals(sUser) && g.courseCode().equals(cc)) {
                it.set(new GradeRecord(sUser, cc, midterms, finals));
                return true;
            }
        }
        return false;
    }
    public static double gpa(String sUser) {
        double sum = 0;
        int count = 0;
        for (var g : s_Grades.get()) if(g.studentUsername().equals(sUser)) {
            sum += g.calculateAverage();
            count++;
        }
        if (count != 0) return sum / count;
        else return 0;
    }
}
