package data;

import model.Enrollment;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;

public class EnrollmentManager {
    private EnrollmentManager() {}

    private static final DataManager<Enrollment> s_Enrollments =
            new DataManager<>(
                    Enrollment::fromLine,
                    Enrollment::toFileString
            );

    public static void save(Path path) { s_Enrollments.save(path); }
    public static void load(Path path) { s_Enrollments.load(path); }

    public static boolean add(String studentUsername, String courseCode) {
        var c = CourseManager.get(courseCode);
        if (c == null) return false;
        if (c.quota() == studentCount(courseCode)) return false;
        return s_Enrollments.add(new Enrollment(studentUsername, courseCode));
    }
    public static int studentCount(String courseCode) {
        int count = 0;
        for (final var e : s_Enrollments.get()) {
            if (e.courseCode().equals(courseCode)) count++;
        }
        return count;
    }

    public static boolean isEnrolled(String studentUser, String courseCode) {
        for (final var e : s_Enrollments.get()) {
            if (e.courseCode().equals(courseCode) && e.studentUsername().equals(studentUser)) return true;
        }
        return false;
    }

    public static @NotNull ArrayList<String> getCoursesBy(String studentUser) {
        ArrayList<String> res = new ArrayList<>();
        for (final var e : s_Enrollments.get()) if (e.studentUsername().equals(studentUser)) res.add(e.courseCode());
        return res;
    }
    public static @NotNull ArrayList<String> getStudentsIn(String courseCode) {
        ArrayList<String> res = new ArrayList<>();
        for (final var e : s_Enrollments.get()) if (e.courseCode().equals(courseCode)) res.add(e.studentUsername());
        return res;
    }

    public static boolean removeEnrollment(String studentUser, String courseCode) {
        return s_Enrollments.get().removeIf(
                e -> (e.courseCode().equals(courseCode) && e.studentUsername().equals(studentUser)));
    }
    public static boolean removeCourse(String courseCode) {
        return s_Enrollments.get().removeIf(e -> e.courseCode().equals(courseCode));
    }
    public static boolean removeStudent(String studentUsername) {
        return s_Enrollments.get().removeIf(e -> e.studentUsername().equals(studentUsername));
    }
}
