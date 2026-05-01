package data;

import model.StudentProfile;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

public class StudentManager {
    private static final DataManager<StudentProfile> s_Students =
            new DataManager<>(
                    StudentProfile::fromLine,
                    StudentProfile::toFileString
            );

    private StudentManager() {
    }

    public static void load(Path path) {
        s_Students.load(path);
    }
    public static void save(Path path) {
        s_Students.save(path);
    }

    public static @Nullable StudentProfile get(String username) {
        for (var sp : s_Students.get()) if (sp.username().equals(username)) return sp;
        return null;
    }
    public static List<StudentProfile> get() {
        return s_Students.get();
    }

}
