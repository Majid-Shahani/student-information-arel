package data;

import java.nio.file.Path;

public class DataStore {
    public static void loadAll() {
        Path base = Path.of(System.getProperty("user.dir")).resolve("resources");

        CourseManager.load(base.resolve("courses.txt"));
        EnrollmentManager.load(base.resolve("enrollments.txt"));
        GradeManager.load(base.resolve("grades.txt"));
        StudentManager.load(base.resolve("students.txt"));
        UserManager.load(base.resolve("users.txt"));
    }
    public static void saveAll() {
        Path base = Path.of(System.getProperty("user.dir")).resolve("resources");

        CourseManager.save(base.resolve("courses.txt"));
        EnrollmentManager.save(base.resolve("enrollments.txt"));
        GradeManager.save(base.resolve("grades.txt"));
        StudentManager.save(base.resolve("students.txt"));
        UserManager.save(base.resolve("users.txt"));
    }
}
