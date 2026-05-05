package data;

import model.StudentProfile;

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
    public static void deleteCourse(String courseCode) {
        CourseManager.removeCourse(courseCode);
        EnrollmentManager.removeCourse(courseCode);
        GradeManager.removeCourse(courseCode);
    }

    public static void deleteStudent(StudentProfile st) {
        StudentManager.removeUser(st.studentID());
        UserManager.removeUser(st.username());
        EnrollmentManager.removeStudent(st.studentID());
        GradeManager.removeStudent(st.studentID());
    }
}
