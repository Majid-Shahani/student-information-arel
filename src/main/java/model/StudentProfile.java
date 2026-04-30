package model;

public record StudentProfile(String studentID, String fullName, String department, int year, String username) {
    String toFileString() {
        return String.join(",",
                studentID,
                fullName,
                department,
                String.valueOf(year),
                username
        );
    }
}
