package data;

import model.StudentProfile;

public class StudentManager {
    private final DataManager<StudentProfile> m_Students =
            new DataManager<>(
                    StudentProfile::fromLine,
                    StudentProfile::toFileString
            );

}
