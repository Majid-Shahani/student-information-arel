package data;

import model.Role;
import model.StudentProfile;
import model.User;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;

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

    public static void removeUser(String username) {
        User user = (Objects.requireNonNull(UserManager.get(username)));
        if (user.role() == Role.STUDENT) deleteStudent(Objects.requireNonNull(StudentManager.get(user.username())));
        else if (user.role() == Role.INSTRUCTOR) deleteInstructor(user);
        else deleteUser(user); // admin
    }
    public static void deleteUser(@NotNull User user) {
        UserManager.removeUser(user);
    }
    public static void deleteInstructor(@NotNull User user) {
        var courses = CourseManager.getByInstructor(user.username());
        for (var course : courses) {
            EnrollmentManager.removeCourse(course.courseCode());
            GradeManager.removeCourse(course.courseCode());
        }
        CourseManager.removeCourses(courses);
        UserManager.removeUser(user);
    }

    public static void deleteStudent(@NotNull StudentProfile st) {
        StudentManager.removeUser(st.username());
        UserManager.removeUser(st.username());
        EnrollmentManager.removeStudent(st.username());
        GradeManager.removeStudent(st.username());
    }
}
