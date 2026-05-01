package data;

import model.Course;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private CourseManager() {}

    private static final DataManager<Course> s_Courses =
            new DataManager<>(
                    Course::fromLine,
                    Course::toFileString
            );

    public static void save(Path path) { s_Courses.save(path); }
    public static void load(Path path) { s_Courses.load(path); }

    public static List<Course> get() { return s_Courses.get(); }

    public static @Nullable Course get(String courseCode) {
        for(Course c : s_Courses.get()) if (c.courseCode().equals(courseCode)) return c;
        return null;
    }

    public static @NotNull List<Course> getByInstructor(String instructorName) {
        ArrayList<Course> instructor_courses = new ArrayList<>();
        for (Course c : s_Courses.get()) {
            if (c.instructorUsername().equals(instructorName)) instructor_courses.add(c);
        }
        return instructor_courses;
    }
}
